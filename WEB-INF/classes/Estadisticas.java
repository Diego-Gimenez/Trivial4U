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
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = new PrintWriter(response.getOutputStream(), true, java.nio.charset.StandardCharsets.UTF_8);

        // Obtener el IdJugador desde la sesión
        HttpSession session = request.getSession(false);
        Integer IdJugador = (session != null) ? (Integer) session.getAttribute("IdJugador") : null;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Consulta SQL para obtener la suma de preguntas acertadas y falladas por categoría para el jugador
            String sql = "SELECT c.Nombre AS Categoria, SUM(e.Acertadas) AS Acertadas, SUM(e.Falladas) AS Falladas " +
                         "FROM estadisticas e " +
                         "JOIN categorias c ON e.IdCategoria = c.IdCategoria " +
                         "WHERE e.IdJugador = ? " +
                         "GROUP BY c.Nombre " +
                         "ORDER BY c.Nombre";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, IdJugador);
            ResultSet rs = stmt.executeQuery();

            out.println("<html lang='es'><head><meta charset='UTF-8'><title>Mis Estadisticas</title>");
            out.println("<style>body { font-family: Arial, sans-serif; text-align: center; } ");
            out.println("table {width: 60%; margin: 20px auto; border-collapse: collapse;}");
            out.println("th, td {padding: 10px; border: 1px solid #ddd; text-align: center;}");
            out.println("th {background-color: #f2f2f2;}</style>");
            out.println("</head><body>");
            out.println("<h1>Estadisticas del Jugador</h1>");
            out.println("<table><tr><th>Categoria</th><th>Acertadas</th><th>Falladas</th></tr>");
    
            boolean hayResultados = false;
            while (rs.next()) {
                hayResultados = true;
                out.println("<tr><td>" + rs.getString("Categoria") + "</td><td>" 
                        + rs.getInt("Acertadas") + "</td><td>" + rs.getInt("Falladas") + "</td></tr>");
            }
    
            if (!hayResultados) {
                out.println("<tr><td colspan='3'>No hay datos de estadisticas.</td></tr>");
            }
    
            out.println("</table>");
            out.println("<br><a href='inicioPartida.html' style='text-decoration:none; color: white; background-color: #007bff; padding: 10px 20px; border-radius: 5px;'>Volver al Inicio</a>");
            out.println("</body></html>");
    
        } catch (ClassNotFoundException e) {
            out.println("<h2>Error: No se encontró el driver JDBC.</h2>");
            e.printStackTrace();
        } catch (SQLException e) {
            out.println("<h2>Error de base de datos: " + e.getMessage() + "</h2>");
            e.printStackTrace();
        }
    }
}