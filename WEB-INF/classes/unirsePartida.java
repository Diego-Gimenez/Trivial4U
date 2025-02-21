import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class UnirsePartida extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "");

            // Consulta para obtener partidas activas con un solo jugador (contrincante NULL)
            String sql = "SELECT p.IdPartida, p.nombrePartida, j.nombre AS nombreCreador " +
                         "FROM partida p " +
                         "JOIN jugadores j ON p.idCreador = j.IdJugador " +
                         "WHERE p.activa = 1 AND p.contrincante IS NULL";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Generar la respuesta HTML en el servidor
            out.println("<html><head><title>Unirse a una Partida</title></head><body>");
            out.println("<h1>Partidas Disponibles para Unirse</h1>");
            out.println("<table border='1'><tr><th>ID</th><th>Nombre de Partida</th><th>Creador</th><th>Unirse</th></tr>");

            while (rs.next()) {
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
            out.println("</body></html>");

            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            out.println("Error al obtener las partidas: " + e.getMessage());
        }
        out.close();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");

        // Obtener el ID del usuario desde la sesión
        HttpSession session = req.getSession();
        int idUsuario = (session.getAttribute("idUsuario") != null) ? (int) session.getAttribute("idUsuario") : -1;

        // Obtener el ID de la partida
        int idPartida = Integer.parseInt(req.getParameter("idPartida"));

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

            // Si la actualización fue exitosa, generar la página del tablero directamente
            if (filasActualizadas > 0) {
                res.setContentType("text/html");
                PrintWriter out = res.getWriter();
                out.println("<html><head><title>Tablero de Juego</title></head><body>");
                out.println("<h1>Bienvenido al Tablero de la Partida " + idPartida + "</h1>");
                out.println("<p>Has sido unido correctamente a la partida. ¡Buena suerte!</p>");
                out.println("<a href='tablero?partida=" + idPartida + "'>Ir al Tablero</a>");
                out.println("</body></html>");
            } else {
                res.getWriter().println("Error al unirse a la partida.");
            }
        } catch (Exception e) {
            res.getWriter().println("Error: " + e.getMessage());
        }
    }
}