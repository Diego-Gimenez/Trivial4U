import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class EnJuego extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        // Obtener el ID del usuario desde la sesión
        HttpSession session = req.getSession();
        int idUsuario = (session.getAttribute("IdJugador") != null) ? (int) session.getAttribute("IdJugador") : -1;

        if (idUsuario == -1) {
            out.println("<html><body><h1>Error: No has iniciado sesión.</h1></body></html>");
            res.sendRedirect("login.html");
            return;
        }

        try {
            // Conectar a la base de datos
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "");

            // Consulta para obtener partidas activas donde el usuario es creador o contrincante
            String sql = "SELECT p.IdPartida, p.nombrePartida, c.nombre AS nombreCreador, " +
                         "COALESCE(o.nombre, 'Esperando') AS nombreContrincante " +
                         "FROM partida p " +
                         "JOIN jugadores c ON p.idCreador = c.IdJugador " +
                         "LEFT JOIN jugadores o ON p.contrincante = o.IdJugador " +
                         "WHERE p.activa = 1 AND (p.idCreador = ? OR p.contrincante = ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ps.setInt(2, idUsuario);
            ResultSet rs = ps.executeQuery();

            // Generar la respuesta HTML
            out.println("<html><head><title>Partidas en Juego</title></head><body>");
            out.println("<h1>Mis Partidas en Juego</h1>");
            out.println("<table border='1'><tr><th>ID</th><th>Nombre de Partida</th><th>Creador</th><th>Contrincante</th><th>Continuar</th></tr>");

            while (rs.next()) {
                int idPartida = rs.getInt("IdPartida");
                String nombrePartida = rs.getString("nombrePartida");
                String nombreCreador = rs.getString("nombreCreador");
                String nombreContrincante = rs.getString("nombreContrincante");

                out.println("<tr>");
                out.println("<td>" + idPartida + "</td>");
                out.println("<td>" + nombrePartida + "</td>");
                out.println("<td>" + nombreCreador + "</td>");
                out.println("<td>" + nombreContrincante + "</td>");
                out.println("<td><form action='Tablero' method='get'>");
                out.println("<input type='hidden' name='partida' value='" + idPartida + "'>");
                out.println("<input type='submit' value='Continuar'>");
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
}