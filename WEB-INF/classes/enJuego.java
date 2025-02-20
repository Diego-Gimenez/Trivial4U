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
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "");

            String sql = "SELECT p.IdPartida, p.nombrePartida, " +
                         "j1.nombre AS nombreCreador, " +
                         "j2.nombre AS nombreContrincante " +
                         "FROM partida p " +
                         "JOIN jugadores j1 ON p.idCreador = j1.idJugador " +
                         "LEFT JOIN jugadores j2 ON p.contrincante = j2.idJugador " +
                         "WHERE p.activa = 1";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idPartida = rs.getInt("IdPartida");
                String nombrePartida = rs.getString("nombrePartida");
                String nombreCreador = rs.getString("nombreCreador");
                String nombreContrincante = rs.getString("nombreContrincante");

                if (nombreContrincante == null) {
                    nombreContrincante = "Esperando...";
                }

                out.println("<tr>");
                out.println("<td>" + idPartida + "</td>");
                out.println("<td>" + nombrePartida + "</td>");
                out.println("<td>" + nombreCreador + "</td>");
                out.println("<td>" + nombreContrincante + "</td>");
                out.println("</tr>");
            }

            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            out.println("<tr><td colspan='4'>Error al obtener las partidas: " + e.getMessage() + "</td></tr>");
        }
        out.close();
    }
}
