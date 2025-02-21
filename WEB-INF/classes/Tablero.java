import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class Tablero extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");

        PrintWriter out = res.getWriter();

        // Obtener el número del dado desde la URL
        String resultado = req.getParameter("numero");
        if (resultado == null) {
            resultado = "Lanzar dado";
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
        out.println("#dado-container {");
        out.println("  position: absolute;");
        out.println("  top: 250px;");
        out.println("  left: 50%;");
        out.println("  transform: translateX(-50%);");
        out.println("  text-align: center;");
        out.println("}");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        out.println("<div style='position: relative; display: inline-block;'>");
        out.println("<table>");
        out.println("<tr>\r\n" + //
                        "            <td class=\"verde\"><div id=\"ficha\" class=\"ficha\">1</div>▲</td>\r\n" + //
                        "            <td class=\"amarillo\"></td>\r\n" + //
                        "            <td class=\"blanco\">🎲</td>\r\n" + //
                        "            <td class=\"azul\"></td>\r\n" + //
                        "            <td class=\"verde\"></td>\r\n" + //
                        "            <td class=\"rojo\"></td>\r\n" + //
                        "            <td class=\"blanco\">🎲</td>\r\n" + //
                        "            <td class=\"amarillo\"></td>\r\n" + //
                        "            <td class=\"azul\"><div id=\"ficha2\" class=\"ficha\">2</div>▲</td>\r\n" + //
                        "        </tr>");
        out.println("<tr>\r\n" + //
                        "            <td class=\"rojo\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"verde\"></td>\r\n" + //
                        "        </tr>");
        out.println("<tr>\r\n" + //
                        "            <td class=\"blanco\">🎲</td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"blanco\">🎲</td>\r\n" + //
                        "        </tr>");
        out.println("<tr>\r\n" + //
                        "            <td class=\"verde\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"rojo\"></td>\r\n" + //
                        "        </tr>");
        out.println("<tr>\r\n" + //
                        "            <td class=\"amarillo\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"azul\"></td>\r\n" + //
                        "        </tr>");
        out.println("<tr>\r\n" + //
                        "            <td class=\"rojo\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"verde\"></td>\r\n" + //
                        "        </tr>");
        out.println("<tr>\r\n" + //
                        "            <td class=\"blanco\">🎲</td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"blanco\">🎲</td>\r\n" + //
                        "        </tr>");
        out.println("<tr>\r\n" + //
                        "            <td class=\"azul\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"negro\"></td>\r\n" + //
                        "            <td class=\"amarillo\"></td>\r\n" + //
                        "        </tr>");
        out.println("<tr>\r\n" + //
                        "            <td class=\"amarillo\">▲</td>\r\n" + //
                        "            <td class=\"rojo\"></td>\r\n" + //
                        "            <td class=\"blanco\">🎲</td>\r\n" + //
                        "            <td class=\"azul\"></td>\r\n" + //
                        "            <td class=\"amarillo\"></td>\r\n" + //
                        "            <td class=\"verde\"></td>\r\n" + //
                        "            <td class=\"blanco\">🎲</td>\r\n" + //
                        "            <td class=\"azul\"></td>\r\n" + //
                        "            <td class=\"rojo\">▲</td>\r\n" + //
                        "        </tr>");

        out.println("<br>");
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