import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class Tablero extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        // Obtener el número del dado desde la URL
        String resultado = req.getParameter("numero");
        if (resultado == null) {
            resultado = "Lanzar dado";
        } else {
            resultado = "🎲 " + resultado;
        }

        out.println("<!DOCTYPE html>");
        out.println("<html lang='es'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Trivial</title>");
        out.println("<link rel='stylesheet' href='./css/styles.css'>");
        out.println("<style>");
        out.println("table { border-collapse: collapse; width: 400px; height: 400px; }");
        out.println("td { width: 50px; height: 40px; border: 2px solid black; text-align: center; font-size: 25px; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        out.println("<div style='position: relative; display: inline-block;'>");
        out.println("<table>");
        out.println("<tr><td class='verde'>▲</td><td class='rosa'></td><td class='blanco'>🎲</td><td class='rojo'></td><td class='verde'></td><td class='rosa'></td><td class='blanco'>🎲</td><td class='rojo'></td><td class='azul'>▲</td></tr>");
        out.println("<tr><td class='azul'></td><td class='negro'></td><td class='negro'></td><td class='negro'></td><td class='azul'></td><td class='negro'></td><td class='negro'></td><td class='negro'></td><td class='verde'></td></tr>");
        out.println("<tr><td class='blanco'>🎲</td><td class='negro'></td><td class='negro'></td><td class='negro'></td><td class='blanco'>🎲</td><td class='negro'></td><td class='negro'></td><td class='negro'></td><td class='blanco'>🎲</td></tr>");
        out.println("<tr><td class='rojo'></td><td class='negro'></td><td class='negro'></td><td class='negro'></td><td class='rojo'></td><td class='negro'></td><td class='negro'></td><td class='negro'></td><td class='rosa'></td></tr>");
        out.println("<tr><td class='rosa'></td><td class='verde'></td><td class='blanco'>🎲</td><td class='azul'></td><td class='gris'>★</td><td class='rosa'></td><td class='blanco'>🎲</td><td class='rojo'></td><td class='azul'></td></tr>");
        out.println("<tr><td class='azul'></td><td class='negro'></td><td class='negro'></td><td class='negro'></td><td class='verde'></td><td class='negro'></td><td class='negro'></td><td class='negro'></td><td class='verde'></td></tr>");
        out.println("<tr><td class='blanco'>🎲</td><td class='negro'></td><td class='negro'></td><td class='negro'></td><td class='blanco'>🎲</td><td class='negro'></td><td class='negro'></td><td class='negro'></td><td class='blanco'>🎲</td></tr>");
        out.println("<tr><td class='rojo'></td><td class='negro'></td><td class='negro'></td><td class='negro'></td><td class='rosa'></td><td class='negro'></td><td class='negro'></td><td class='negro'></td><td class='rosa'></td></tr>");
        out.println("<tr><td class='rosa'>▲</td><td class='verde'></td><td class='blanco'>🎲</td><td class='azul'></td><td class='rojo'></td><td class='verde'></td><td class='blanco'>🎲</td><td class='azul'></td><td class='rojo'>▲</td></tr>");
        out.println("</table>");
        out.println("<div id='ficha' class='ficha'>1</div>");
        out.println("</div>");

        out.println("<br><br>");
        out.println("<div id='dado-container'>");
        out.println("<form action='Dado' method='GET'>");
        out.println("<button type='submit'>🎲</button>");
        out.println("</form>");
        out.println("<div id='resultadoDado1'><strong>" + resultado + "</strong></div>");
        out.println("</div>");

        out.println("<div id='controles'>");
        out.println("<form action='MoverFicha' method='post'>");
        out.println("<button type='submit' name='direccion' value='arriba'>⬆</button>");
        out.println("<button type='submit' name='direccion' value='izquierda'>⬅</button>");
        out.println("<button type='submit' name='direccion' value='derecha'>" + "➡" + "</button>");
        out.println("<button type='submit' name='direccion' value='abajo'>⬇</button>");
        out.println("</form>");
        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }
}