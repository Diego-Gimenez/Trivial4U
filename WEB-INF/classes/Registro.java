import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;

public class Registro extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
    throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String nombre = req.getParameter("nombre");
        String pass = req.getParameter("password");
        String pass2 = req.getParameter("password2");

        if (pass.equals(pass2)) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "");

                // Verificar si el nombre de usuario ya existe
                PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM jugadores WHERE nombre = ?");
                ps.setString(1, nombre);
                ResultSet rs = ps.executeQuery();
                
                rs.next(); // Mover el cursor a la primera fila
                
                int count = rs.getInt(1);

                if (count > 0) {
                    out.println("El nombre de usuario ya existe. Por favor, elige otro nombre.");
                } else {
                    // Registrar nuevo usuario
                    PreparedStatement stmt = con.prepareStatement("INSERT INTO jugadores (nombre, password) VALUES (?, ?)");
                    stmt.setString(1, nombre);
                    stmt.setString(2, pass);
                    stmt.executeUpdate();

                    HttpSession session = req.getSession(); // crea una sesión
                    session.setAttribute("nombre", nombre); // guarda el nombre del usuario en la sesión
                    session.setAttribute("IdJugador", stmt.getGeneratedKeys().getInt(1)); // guarda el id del usuario en la sesión
                    session.setMaxInactiveInterval(30 * 60); // La sesión dura 30 minutos    

                    res.sendRedirect("inicioPartida.html");

                    stmt.close();
                }

                rs.close();
                ps.close();
                con.close();
            } catch (Exception e) {
                out.println("Error en el registro: " + e.getMessage());
            }
        } else {
            out.println("Las contraseñas no coinciden");
        }
        out.close();
    }
}
