import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class UnirsePartida extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Obtener el ID del usuario desde la sesión
        HttpSession session = req.getSession();
        Integer idUsuario = (Integer) session.getAttribute("IdJugador");

        // Si no hay sesión o el usuario no tiene un ID, redirigir al login
        if (idUsuario == null) {
            res.sendRedirect("login.html");
            return;
        }

        System.out.println("ID del usuario en sesión: " + idUsuario); // Depuración

        try {
            // Conectar con la base de datos
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "");

            // Consulta filtrando las partidas creadas por el usuario en sesión
            String sql = "SELECT p.IdPartida, p.nombrePartida, j.nombre AS nombreCreador " +
                         "FROM partida p " +
                         "JOIN jugadores j ON p.idCreador = j.IdJugador " +
                         "WHERE p.activa = 1 AND p.contrincante IS NULL AND p.idCreador <> ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario);  // Asignar el parámetro
            rs = ps.executeQuery();

            // Generar HTML
            out.println("<html><head><title>Unirse a una Partida</title></head><body>");
            out.println("<h1>Partidas Disponibles para Unirse</h1>");
            out.println("<table border='1'><tr><th>ID</th><th>Nombre de Partida</th><th>Creador</th><th>Unirse</th></tr>");

            boolean hayPartidas = false;
            while (rs.next()) {
                hayPartidas = true;
                int idPartida = rs.getInt("IdPartida");
                String nombrePartida = rs.getString("nombrePartida");
                String nombreCreador = rs.getString("nombreCreador");

                out.println("<tr>");
                out.println("<td>" + idPartida + "</td>");
                out.println("<td>" + nombrePartida + "</td>");
                out.println("<td>" + nombreCreador + "</td>");
                out.println("<td><form action='UnirsePartida' method='post'>");
                out.println("<input type='hidden' name='idPartida' value='" + idPartida + "'>");
                out.println("<input type='submit' value='Unirse'>");
                out.println("</form></td>");
                out.println("</tr>");
            }
            out.println("</table>");

            if (!hayPartidas) {
                out.println("<p>No hay partidas disponibles para unirse en este momento.</p>");
            }

            out.println("</body></html>");
        } catch (Exception e) {
            out.println("<p>Error al obtener las partidas: " + e.getMessage() + "</p>");
            e.printStackTrace(); // Imprime el error en la consola del servidor
        } finally {
            try { if (rs != null) rs.close(); if (ps != null) ps.close(); if (con != null) con.close(); } catch (SQLException ignored) {}
        }
        out.close();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");

        // Obtener el ID del usuario desde la sesión
        HttpSession session = req.getSession();
        Integer idUsuario = (Integer) session.getAttribute("IdJugador");

        // Si no hay sesión, redirigir al login
        if (idUsuario == null) {
            res.sendRedirect("login.html");
            return;
        }

        // Obtener el ID de la partida
        int idPartida;
        try {
            idPartida = Integer.parseInt(req.getParameter("idPartida"));
        } catch (NumberFormatException e) {
            res.getWriter().println("Error: ID de partida inválido.");
            return;
        }

        try {
            // Conexión a la base de datos
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "");

            // Actualizar la partida asignando al usuario como contrincante
            String sql = "UPDATE partida SET contrincante = ? WHERE IdPartida = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ps.setInt(2, idPartida);
            int filasActualizadas = ps.executeUpdate();

            ps.close();
            con.close();

            // Si la actualización fue exitosa, redirigir al tablero
            if (filasActualizadas > 0) {
                res.sendRedirect("Tablero?IdPartida=" + idPartida);
            } else {
                res.getWriter().println("Error al unirse a la partida.");
            }
        } catch (Exception e) {
            res.getWriter().println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
