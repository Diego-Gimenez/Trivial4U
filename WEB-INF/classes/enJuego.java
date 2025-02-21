import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class EnJuego extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        
        // Obtener el ID del jugador desde la sesión
        HttpSession session = req.getSession();
        Integer idJugador = (Integer) session.getAttribute("IdJugador");
        
        if (idJugador == null) {
            // Si no hay sesión activa, redirigir al login
            res.sendRedirect("login.html"); // Redirigir al login si no está logueado
            return;
        }

        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "");

            // Consulta para obtener las partidas activas donde el jugador es creador o contrincante
            String sql = "SELECT p.IdPartida, p.nombrePartida, j1.nombre AS creador, j2.nombre AS contrincante " +
                         "FROM partida p " +
                         "LEFT JOIN jugadores j1 ON p.idCreador = j1.IdJugador " +
                         "LEFT JOIN jugadores j2 ON p.contrincante = j2.IdJugador " +
                         "WHERE p.activa = 1 AND (p.idCreador = ? OR p.contrincante = ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idJugador);
            ps.setInt(2, idJugador);
            ResultSet rs = ps.executeQuery();

            // HTML de la respuesta
            out.println("<html><head><title>Partidas en Juego</title></head><body>");
            out.println("<h1>Partidas en Juego</h1>");
            out.println("<table border='1'><tr><th>ID</th><th>Nombre de Partida</th><th>Creador</th><th>Contrincante</th><th>Acción</th></tr>");

            // Iterar sobre los resultados
            while (rs.next()) {
                int idPartida = rs.getInt("IdPartida");
                String nombrePartida = rs.getString("nombrePartida");
                String creador = rs.getString("creador");
                String contrincante = rs.getString("contrincante");
                String botonAccion = "";

                // Si el contrincante es null, significa que la partida está esperando un contrincante y el jugador puede unirse
                if (contrincante == null) {
                    botonAccion = "<form action='unirsePartida' method='post'>" +
                                  "<input type='hidden' name='idPartida' value='" + idPartida + "'>" +
                                  "<input type='submit' value='Unirse'>" +
                                  "</form>";
                } else {
                    // Si ya hay contrincante, el botón será para continuar el juego
                    botonAccion = "<form action='continuarPartida' method='post'>" +
                                  "<input type='hidden' name='idPartida' value='" + idPartida + "'>" +
                                  "<input type='submit' value='Continuar con el juego'>" +
                                  "</form>";
                }

                out.println("<tr>");
                out.println("<td>" + idPartida + "</td>");
                out.println("<td>" + nombrePartida + "</td>");
                out.println("<td>" + creador + "</td>");
                out.println("<td>" + (contrincante != null ? contrincante : "Esperando contrincante") + "</td>");
                out.println("<td>" + botonAccion + "</td>");
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