import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;

public class Login extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
    throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String nombre = req.getParameter("nombre");
        String pass = req.getParameter("password");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "");

            PreparedStatement ps = con.prepareStatement("SELECT * FROM jugadores WHERE Nombre = ? AND password = ?");
            ps.setString(1, nombre);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                //El usuario existe y la contraseña es correcta
                HttpSession session = req.getSession(); // crea una sesión
                session.setAttribute("usuario", nombre); // guarda el nombre del usuario en la sesión
                session.setAttribute("idJugador", rs.getInt("IdJugador")); // guarda el id del usuario en la sesión
                session.setMaxInactiveInterval(30 * 60); // La sesión dura 30 minutos

                out.println("Inicio de sesión exitoso. ¡Bienvenido, " + nombre + "!");
                res.sendRedirect("opcionesjuego.html");
            } else {
                out.println("Nombre de usuario o contraseña incorrectos");
            }

            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            out.println("Error en el login: " + e.getMessage());
        }
        out.close();
    }

}
