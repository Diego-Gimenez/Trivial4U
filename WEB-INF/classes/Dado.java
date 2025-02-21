import javax.servlet.http.*;
import java.io.*;
import java.util.Random;

public class Dado extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Random rand = new Random();
        int resultado = rand.nextInt(6) + 1;
        res.sendRedirect("tablero?numero=" + resultado);
    }
}
