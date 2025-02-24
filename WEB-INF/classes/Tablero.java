import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class Tablero extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Connection con;
        Statement st, st2;
        ResultSet rs, rs2;
        PrintWriter out;
        String SQL, SQL2;
        String resultado = req.getParameter("numero");
        String nuevaPosicion1 = req.getParameter("pos1");
        String nuevaPosicion2 = req.getParameter("pos2");
        int pos1 = -1;
        int pos2 = -1;

        if (nuevaPosicion1 != null && !nuevaPosicion1.isEmpty()) {
            pos1 = Integer.parseInt(nuevaPosicion1);
        }

        if (nuevaPosicion2 != null && !nuevaPosicion2.isEmpty()) {
            pos2 = Integer.parseInt(nuevaPosicion2);
        }


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto?useUnicode=true&characterEncoding=UTF-8", "root", "");
            st = con.createStatement();
            SQL = "SELECT * FROM tablero ORDER BY Fila, Columna";
            rs = st.executeQuery(SQL);
            st2 = con.createStatement();
            SQL2 = "SELECT NumCasilla FROM detallespartida";
            rs2 = st2.executeQuery(SQL2);
            out = res.getWriter();
            res.setContentType("text/html; charset=UTF-8");
            res.setCharacterEncoding("UTF-8");
            String[][] tablero = new String[10][10];
            
            if (resultado == null) {
                resultado = "Lanzar dado";
            }

            int posicion = -1;
            if (rs2.next()) {
                posicion = rs2.getInt("NumCasilla");
            }

            while (rs.next()) {
                int id = rs.getInt("NumeroCasilla");
                int fila = rs.getInt("Fila");
                int columna = rs.getInt("Columna");
                String tipo = rs.getString("IdTipo");
                String categoria = rs.getString("IdCategoria");

                if (tipo.equals("3")) {
                    tipo = "blanco";
                } else {
                    switch(categoria) {
                        case "1": tipo = "verde"; break;
                        case "3": tipo = "azul"; break;
                        case "6": tipo = "amarillo"; break;
                        case "7": tipo = "rojo"; break;
                        default: tipo = "gris";
                    }
                }
                String contenido = rs.getString("Contenido");
                if (contenido == null) contenido = ""; 
                contenido = contenido.replace("🎲", "&#127922;");
                contenido = contenido.replace("▲", "&#9650;");

                if (id == posicion) {
                    contenido += "<div id='ficha' class='ficha'>1</div>";
                }
                    // ✔
                if (id == pos1 || id == pos2) {
                    contenido = "<form action='SeleccionarCasilla' method='POST'>" +
                                "<input type='hidden' name='casilla' value='" + id + "'>" +
                                "<button type='submit' class='seleccionar-casilla'>&#10004;</button>" +
                                "</form>";
                    tipo += " resaltado";
                }
                tablero[fila][columna] = "<td class='" + tipo + "'>" + contenido + "</td>";
            }

            // Estructura HTML
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
            out.println("<table>");

            for (int fila = 1; fila < 10; fila++) {
                out.println("<tr>");
                for (int columna = 1; columna < 10; columna++) {
                    // Celda negra
                    if (fila >= 2 && fila <= 8 && columna >= 2 && columna <= 8) {
                        out.println("<td class='negro'></td>");
                    } else {
                        out.println(tablero[fila][columna] != null ? tablero[fila][columna] : "<td></td>");
                    }
                }
                out.println("</tr>");
            }
            out.println("</tr></table>");
            out.println("<br>");
            out.println("<div id='dado-container'>");
            out.println("<form action='Dado' method='GET'>");
            out.println("<button type='submit'>&#127922;</button>");
            out.println("</form>");
            out.println("<div id='resultadoDado1'><strong>" + resultado + "</strong></div>");
            out.println("</div>");

            out.println("</body></html>");
            rs.close();
            st.close();
            con.close();
            out.close();
        }
        catch (Exception e) {
            out = res.getWriter();
            e.printStackTrace(); // Esto imprimirá el error en los logs
            out.println("<p>Error al conectar con la base de datos: " + e.getMessage() + "</p>");
        }
    }
}
