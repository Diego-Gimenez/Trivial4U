import javax.servlet.http.*;
import java.sql.*;
import java.io.*;

public class ValidarRespuesta extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        Connection con;
        Statement st;
        ResultSet rs;
        PrintWriter out;
        String respuestaCorrecta;
        String pregunta = req.getParameter("IdPregunta");
        String respuesta = req.getParameter("Respuesta");
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "");
                st = con.createStatement();
                respuestaCorrecta = "SELECT Correcta FROM preguntas WHERE IdPregunta =" + pregunta;
                rs = st.executeQuery(respuestaCorrecta);
                out = res.getWriter();
                res.setContentType("text/html");

                out.println("<HTML><BODY>");
                if (respuestaCorrecta == respuesta) {
                    out.println("<INPUT TYPE=TEXT VALUE=Respuesta correcta");
                    // Continua el turno del jugador
                    // Permitir otro lanzamiento de dado
                    // Si la casilla es especial, se añade quesito
                }
                else {
                    out.println("<INPUT TYPE=TEXT VALUE=Respuesta incorrecta");
                    // Termina el turno del jugador
                    // No se añade nada
                }
                out.println("</BODY><HTML>");

                rs.close();
                st.close();
                con.close();
                out.close();
            }
            catch (Exception e) {
                System.err.println(e);
            }
    }
}