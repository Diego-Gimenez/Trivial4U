import javax.servlet.http.*;
import java.util.Random;
import java.io.*;

public class Dado extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        PrintWriter out;
            try {
                out = res.getWriter();
                res.setContentType("text/html");

                out.println("<HTML><BODY>");
                int resultado = new Random().nextInt(6) + 1;
                out.println("<" + resultado + ">");
                out.println("</BODY><HTML>");
                out.close();
            }
            catch (Exception e) {
                System.err.println(e);
            }
    }
}