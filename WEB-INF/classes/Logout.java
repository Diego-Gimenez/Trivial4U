import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

public class Logout extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res)
    throws IOException, ServletException {
        // Obtener la sesión actual
        HttpSession session = req.getSession(false);

        // Si la sesión existe, invalidarla
        if (session != null) {
            session.invalidate();  // Invalidamos la sesión
        }

        // Redirigir al usuario a la página de login o inicio
        res.sendRedirect("index.html");
    }
}
