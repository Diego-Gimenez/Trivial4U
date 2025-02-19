import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class enJuego extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "");

            // Consulta para obtener partidas activas con el nombre del creador
            String sql = "SELECT p.IdPartida, p.nombrePartida, j.nombre AS nombreCreador " +
                         "FROM partida p " +
                         "JOIN jugadores j ON p.idCreador = j.idJugador " +
                         "WHERE p.activa = 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // HTML de la respuesta
            out.println("<html><head><title>Partidas en Juego</title></head><body>");
            out.println("<h1>Partidas en Juego</h1>");
            out.println("<table border='1'><tr><th>ID</th><th>Nombre de Partida</th><th>Creador</th></tr>");

            // Iterar sobre los resultados
            while (rs.next()) {
                int idPartida = rs.getInt("IdPartida");
                String nombrePartida = rs.getString("nombrePartida");
                String nombreCreador = rs.getString("nombreCreador");

                out.println("<tr>");
                out.println("<td>" + idPartida + "</td>");
                out.println("<td>" + nombrePartida + "</td>");
                out.println("<td>" + nombreCreador + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</body></html>");

            // Cerrar conexiones
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            out.println("Error al obtener las partidas: " + e.getMessage());
        }
        out.close();
    }
}