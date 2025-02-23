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
        String respuesta = req.getParameter("respuesta");
        
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "");
                st = con.createStatement();
                respuestaCorrecta = "SELECT Correcta FROM preguntas WHERE IdPregunta =" + pregunta;
                rs = st.executeQuery(respuestaCorrecta);
                out = res.getWriter();
                res.setContentType("text/html");

                if (rs.next()) {
                    respuestaCorrecta = rs.getString("Correcta");

                    out.println("<HTML><BODY>");
                    if (respuesta.equals(respuestaCorrecta)) {
                        out.println("<h2>¡Correcto!</h2>");
                        out.println("<p>Puedes continuar avanzando.</p>");
                        // Continua el turno del jugador
                        // Permitir otro lanzamiento de dado
                        // Si la casilla es especial, se añade quesito
                    } else {
                        out.println("<h2>¡Incorrecto!</h2>");
                        out.println("<p>Lo sentimos, la respuesta correcta era: " + respuestaCorrecta + "</p>");
                        // Termina el turno del jugador
                        // No se añade nada
                    }
                }
                out.println("<a href='index.html'>Volver al juego</a>");
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