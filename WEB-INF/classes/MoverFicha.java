import javax.servlet.http.*;
import java.sql.*;
import java.io.*;

public class MoverFicha extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Connection con;
        Statement st;
        ResultSet rs;
        PrintWriter out;
        String SQL;
        String direccion = req.getParameter("direccion");
        int nuevaPosicion = 0;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "");
                st = con.createStatement();
                SQL = "SELECT NumCasilla FROM detallespartida WHERE IdJugador = 1";
                rs = st.executeQuery(SQL);
                out = res.getWriter();
                res.setContentType("text/html");

                if (rs.next()) {
                    int posicionActual = rs.getInt("NumCasilla");
    
                    switch (direccion) {
                        case "arriba":
                            nuevaPosicion = posicionActual - 9;
                            break;
                        case "abajo":
                            nuevaPosicion = posicionActual + 9;
                            break;
                        case "izquierda":
                            nuevaPosicion = posicionActual - 1;
                            break;
                        case "derecha":
                            nuevaPosicion = posicionActual + 1;
                            break;
                    }
    
                    // Verificar la nueva posición
                    PreparedStatement checkStmt = con.prepareStatement("SELECT IdTipo, IdCategoria FROM tablero WHERE NumeroCasilla = ?");
                    checkStmt.setInt(1, nuevaPosicion);
                    ResultSet checkRs = checkStmt.executeQuery();

                    if (checkRs.next() && checkRs.getObject("IdTipo") != null) {  // No es negra
                        // Actualizar la posición de la ficha
                        PreparedStatement updateStmt = con.prepareStatement("UPDATE ficha SET posicion = ? WHERE id = 1");
                        updateStmt.setInt(1, nuevaPosicion);
                        updateStmt.executeUpdate();

                        if (checkRs.getObject("IdCategoria") != null) { // Si hay categoría, obtener pregunta
                            int categoria = checkRs.getInt("IdCategoria");
                            PreparedStatement preguntaStmt = con.prepareStatement("SELECT Texto, Respuesta1, Respuesta2, Respuesta3, Respuesta4 FROM preguntas WHERE IdCategoria = ? ORDER BY RAND() LIMIT 1");
                            preguntaStmt.setInt(1, categoria);
                            ResultSet preguntaRs = preguntaStmt.executeQuery();
                        
                            if (preguntaRs.next()) {
                                String pregunta = preguntaRs.getString("Texto");
                                String res1 = preguntaRs.getString("Respuesta1");
                                String res2 = preguntaRs.getString("Respuesta2");
                                String res3 = preguntaRs.getString("Respuesta3");
                                String res4 = preguntaRs.getString("Respuesta4");

                                out.println("<html><body>");
                                out.println("<h2>" + pregunta + "</h2>");
                                out.println("<form action='validarRespuesta' method='POST'>");
                                out.println("<input type='radio' name='respuesta' value='" + res1 + "' required> " + res1 + "<br>");
                                out.println("<input type='radio' name='respuesta' value='" + res2 + "'> " + res2 + "<br>");
                                out.println("<input type='radio' name='respuesta' value='" + res3 + "'> " + res3 + "<br>");
                                out.println("<input type='radio' name='respuesta' value='" + res4 + "'> " + res4 + "<br>");
                                out.println("<input type='submit' value='Responder'>");
                                out.println("</form>");
                                out.println("</body></html>");

                            } else {
                            out.println("<p>No hay preguntas disponibles en esta categoría.</p>");
                            }
                        }
                    } else { 
                        res.getWriter().write("Vuelve a lanzar el dado");
                    }
                }

                rs.close();
                st.close();
                con.close();
                out.close();
            }

            catch (Exception e) {
                System.err.println(e);
            }

    res.sendRedirect("tablero.html");
    }
}