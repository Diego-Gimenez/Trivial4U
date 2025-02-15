import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;


public class Estadisticas extends HttpServlet {

    private static final String URL = "jdbc:mysql://localhost:3306/proyecto";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try  {
            Class.forName("com.mysql.cj.jdbc.Driver");
            PrintWriter out = response.getWriter();
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(
                     "SELECT id_partida, id_jugador, id_categoria, acertadas, falladas, estrellas " +
                     "FROM estadisticas ORDER BY id_partida, id_categoria, id_jugador");
            ResultSet rs = stmt.executeQuery();
            // Renderizar la página de estadísticas
            renderStatsPage(out, rs);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try (PrintWriter out = response.getWriter()) {
                out.println("<p>Error al procesar las estadísticas: " + e.getMessage() + "</p>");
            }
        }
    }

    // Método para renderizar la página con las estadísticas
    private void renderStatsPage(PrintWriter out, ResultSet rs) throws SQLException {
        out.println("<html><head><title>Estadísticas</title>");
        out.println("<style>body { font-family: Arial, sans-serif; text-align: center; } ");
        out.println("table {width: 80%; margin: 20px auto; border-collapse: collapse;}");
        out.println("th, td {padding: 10px; border: 1px solid #ddd; text-align: center;}");
        out.println("th {background-color: #f2f2f2;}</style>");
        out.println("</head><body>");
        out.println("<h1>Estadísticas de Jugadores por Partida y Categoría</h1>");
        out.println("<table><tr><th>ID Partida</th><th>ID Jugador</th><th>ID Categoría</th><th>Acertadas</th><th>Falladas</th><th>Estrellas</th></tr>");

        // Imprimir las filas con los resultados de la consulta
        while (rs.next()) {
            out.println("<tr><td>" + rs.getInt("id_partida") + "</td><td>" + rs.getInt("id_jugador") + "</td><td>" 
                    + rs.getInt("id_categoria") + "</td><td>" + rs.getInt("acertadas") + "</td><td>" 
                    + rs.getInt("falladas") + "</td><td>" + rs.getInt("estrellas") + "</td></tr>");
        }

        out.println("</table>");
        out.println("<br><a href='index.html' style='text-decoration:none; color: white; background-color: #007bff; padding: 10px 20px; border-radius: 5px;'>Volver al Inicio</a>");
        out.println("</body></html>");
    }
}