import javax.servlet.http.*;
import java.io.*;
import java.util.Random;

public class Dado extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String IdPartida = req.getParameter("IdPartida");
        int idPartida = -1;
        if (IdPartida != null && !IdPartida.isEmpty()) {
            idPartida = Integer.parseInt(IdPartida);
        }
        
        Random rand = new Random();
        int resultado = rand.nextInt(6) + 1;
        System.out.println("Context Path: " + req.getContextPath());
        res.sendRedirect(req.getContextPath() + "/MoverFicha?numero=" + resultado + "&IdPartida=" + idPartida);
    }
}
