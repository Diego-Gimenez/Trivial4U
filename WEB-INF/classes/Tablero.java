import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class Tablero extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Connection con;
        Statement st, stJ1, stJ2, st2, st3, stJugador, st4;
        ResultSet rs, rsJ1, rsJ2, rs2, rs3, rsJugador, rs4;
        rs3 = null;
        PrintWriter out;
        String SQL, SQLJ1, SQLJ2, SQL2, SQL3, SQLJugador, SQL4;
        String resultado = req.getParameter("numero");
        String nuevaPosicion1 = req.getParameter("pos1");
        String nuevaPosicion2 = req.getParameter("pos2");
        String IdPregunta = req.getParameter("idpregunta");
        String aciertoPregunta = req.getParameter("acierto");
        String IdPartida = req.getParameter("IdPartida");
        int pos1 = -1;
        int pos2 = -1;
        int acierto = -1;
        int idPartida = -1;

        if (nuevaPosicion1 != null && !nuevaPosicion1.isEmpty()) {
            pos1 = Integer.parseInt(nuevaPosicion1);
        }

        if (nuevaPosicion2 != null && !nuevaPosicion2.isEmpty()) {
            pos2 = Integer.parseInt(nuevaPosicion2);
        }

        if (IdPartida != null && !IdPartida.isEmpty()) {
            idPartida = Integer.parseInt(IdPartida);
        }

        // test
        if (aciertoPregunta != null && !aciertoPregunta.isEmpty()) {
            // acierto = Integer.parseInt(aciertoPregunta);
            try {
                acierto = Integer.parseInt(aciertoPregunta);
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir acierto: " + aciertoPregunta);
            }
        }
        System.out.println("Valor de acierto en Tablero.java después de recibir parámetro: " + acierto);
        // test

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto?useUnicode=true&characterEncoding=UTF-8", "root", "");
           
            st = con.createStatement();
            SQL = "SELECT * FROM tablero ORDER BY Fila, Columna";
            rs = st.executeQuery(SQL);

            stJugador = con.createStatement();
            SQLJugador = "SELECT idCreador, contrincante from partida WHERE IdPartida = " + idPartida;
            rsJugador = stJugador.executeQuery(SQLJugador);

            int idJugador1 = -1;
            int idJugador2 = -1;

            if (rsJugador.next()) {
                idJugador1 = rsJugador.getInt("idCreador");
                idJugador2 = rsJugador.getInt("contrincante");
            }

            stJ1 = con.createStatement();
            SQLJ1 = "SELECT NumCasilla FROM detallespartida INNER JOIN partida ON detallespartida.IdPartida = partida.IdPartida WHERE detallespartida.IdPartida =" + idPartida + " AND detallespartida.IdJugador = " + idJugador1;
            rsJ1 = stJ1.executeQuery(SQLJ1);

            stJ2 = con.createStatement();
            SQLJ2 = "SELECT NumCasilla FROM detallespartida INNER JOIN partida ON detallespartida.IdPartida = partida.IdPartida WHERE detallespartida.IdPartida =" + idPartida + " AND detallespartida.IdJugador = " + idJugador2;
            rsJ2 = stJ2.executeQuery(SQLJ2);

            out = res.getWriter();
            res.setContentType("text/html; charset=UTF-8");
            res.setCharacterEncoding("UTF-8");
            String[][] tablero = new String[10][10];
            
            if (resultado == null) {
                resultado = "Lanzar dado";
            }

            int posicionJ1 = -1;
            if (rsJ1.next()) {
                posicionJ1 = rsJ1.getInt("NumCasilla");
            }

            int posicionJ2 = -1;
            if (rsJ2.next()) {
                posicionJ2 = rsJ2.getInt("NumCasilla");
            }

            if (IdPregunta != "0") {
                st3 = con.createStatement();
                SQL3 = "SELECT * FROM preguntas WHERE IdPregunta =" + IdPregunta;
                rs3 = st3.executeQuery(SQL3);
            } else {
                out.println("Volver a tirar");
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

                if (id == posicionJ1) {
                    contenido += "<div id='ficha' class='ficha'>1</div>";
                }

                if (id == posicionJ2) {
                    contenido += "<div id='ficha2' class='ficha2'>2</div>";
                }
                
                if (id == pos1 || id == pos2) {
                    st2 = con.createStatement();
                    SQL2 = "SELECT * FROM detallespartida WHERE Turno = 1 AND IdPartida = " + idPartida;
                    rs2 = st2.executeQuery(SQL2);

                    int idJugador = -1;
                    if (rs2.next()) {
                        idJugador = rs2.getInt("IdJugador");
                    }
                    System.out.println("Formulario generado con acierto: " + acierto);
                    contenido = "<form action='SeleccionarCasilla' method='POST'>" +
                                "<input type='hidden' name='idPartida' value='" + idPartida + "'>" +
                                "<input type='hidden' name='idJugador' value='" + idJugador + "'>" +
                                "<input type='hidden' name='acierto' value='" + acierto + "'>" +
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

            if (rs3 != null && rs3.next()) {
                String pregunta = rs3.getString("Texto");
                String res1 = rs3.getString("Respuesta1");
                String res2 = rs3.getString("Respuesta2");
                String res3 = rs3.getString("Respuesta3");
                String res4 = rs3.getString("Respuesta4");
                String idPregunta = rs3.getString("IdPregunta");

                out.println("<h2>" + pregunta + "</h2>");
                out.println("<form action='ValidarRespuesta' method='GET'>");
                out.println("<input type='hidden' name='IdPregunta' value='" + idPregunta + "'>");
                out.println("<input type='hidden' name='IdPartida' value='" + idPartida + "'>");
                out.println("<input type='radio' name='respuesta' value='" + "1" + "' required> " + res1 + "<br>");
                out.println("<input type='radio' name='respuesta' value='" + "2" + "'> " + res2 + "<br>");
                out.println("<input type='radio' name='respuesta' value='" + "3" + "'> " + res3 + "<br>");
                out.println("<input type='radio' name='respuesta' value='" + "4" + "'> " + res4 + "<br>");
                out.println("<input type='submit' value='Responder'>");
                out.println("</form>");
            }

            st4 = con.createStatement();
            SQL4 = "SELECT IdJugador FROM detallespartida WHERE Turno = 1 AND IdPartida = " + idPartida;
            rs4 = st4.executeQuery(SQL4);

            int JugadorTurno = -1;
            if (rs4.next()) {
                JugadorTurno = rs4.getInt("IdJugador");
            }

            if(acierto == 1) {
                out.println("<h2>Respuesta correcta</h2>");
                out.println("<p>Vuelve a lanzar el dado</p>");

            } else if (acierto == 0) {
                out.println("<h2>Respuesta incorrecta</h2>");
                out.println("<p>Pierdes el turno</p>");
                if (JugadorTurno == idJugador1) {
                    st.executeUpdate("UPDATE detallespartida SET Turno = 0 WHERE IdPartida = " + idPartida + " AND IdJugador = " + idJugador1);
                    st.executeUpdate("UPDATE detallespartida SET Turno = 1 WHERE IdPartida = " + idPartida + " AND IdJugador = " + idJugador2);
                } else if (JugadorTurno == idJugador2) {
                    st.executeUpdate("UPDATE detallespartida SET Turno = 0 WHERE IdPartida = " + idPartida + " AND IdJugador = " + idJugador2);
                    st.executeUpdate("UPDATE detallespartida SET Turno = 1 WHERE IdPartida = " + idPartida + " AND IdJugador = " + idJugador1);
                } else {
                    out.println("<p>Error actualizando turnos. TurnoJugador = " + JugadorTurno + "</p>");
                }
            }

            out.println("<br>");

            if (acierto != 0) {
                out.println("<div id='dado-container'>");
                out.println("<form action='Dado' method='GET'>");
                out.println("<input type='hidden' name='IdPartida' value='" + idPartida + "'>");
                out.println("<button type='submit'>&#127922;</button>");
                out.println("</form>");
                out.println("<div id='resultadoDado1'><strong>" + resultado + "</strong></div>");
                out.println("</div>");
            } else {
                out.println("<p>Es el turno del otro jugador</p>");
            }

            out.println("</body></html>");
            rs.close();
            st.close();
            con.close();
            out.close();
        }
        catch (Exception e) {
            out = res.getWriter();
            e.printStackTrace();
            out.println("<p>Error al conectar con la base de datos: " + e.getMessage() + "</p>");
        }
    }
}
