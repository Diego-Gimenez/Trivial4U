import javax.servlet.http.*;
import java.sql.*;
import java.io.*;

public class ValidarRespuesta extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Connection con;
        Statement st;
        ResultSet rs;
        PrintWriter out;
        String SQL;
        String pregunta = req.getParameter("IdPregunta");
        String respuesta = req.getParameter("respuesta");

        if (pregunta == null || respuesta == null) {
            res.setContentType("text/html");
            out = res.getWriter();
            out.println("<html><body>");
            out.println("<h2>Error: Falta información.</h2>");
            out.println("<p>No se ha recibido una pregunta o respuesta válida.</p>");
            out.println("<a href='tablero'>Volver al juego</a>");
            out.println("</body></html>");
            return;
        }

        int resElegida;
        int acierto = -1;
        try {
            resElegida = Integer.parseInt(respuesta);
        } catch (NumberFormatException e) {
            resElegida = -1;
        }
        
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "");
                st = con.createStatement();
                SQL = "SELECT * FROM preguntas WHERE IdPregunta =" + pregunta;
                rs = st.executeQuery(SQL);
                out = res.getWriter();
                res.setContentType("text/html");

                out.println("<HTML><BODY>");
                if (rs.next()) {
                    int correcta = rs.getInt("Correcta");
                    String textoCorrecta = "";
                    switch (correcta) {
                        case 1: textoCorrecta = rs.getString("Respuesta1"); break;
                        case 2: textoCorrecta = rs.getString("Respuesta2"); break;
                        case 3: textoCorrecta = rs.getString("Respuesta3"); break;
                        case 4: textoCorrecta = rs.getString("Respuesta4"); break;
                        default: textoCorrecta = ""; break;
                    }

                    if (resElegida == correcta) {
                        out.println("<h2>¡Correcto!</h2>");
                        out.println("<p>Puedes continuar avanzando.</p>");
                        acierto = 1;
                        // Continua el turno del jugador
                        // Permitir otro lanzamiento de dado
                    } else {
                        out.println("<h2>¡Incorrecto!</h2>");
                        out.println("<p>Lo sentimos, la respuesta correcta era: " + textoCorrecta + "</p>");
                        acierto = 0;
                        // Termina el turno del jugador
                    }
                } else {
                    out.println("<h2>Error</h2>");
                    out.println("<p>No se encontró la pregunta en la base de datos.</p>");
                }
                System.out.println("Valor de acierto antes de enviar a tablero: " + acierto);
                System.out.println("Redirigiendo a: tablero?acierto=" + acierto);
                out.println("<a href='tablero?acierto=" + acierto + "'>Volver al juego</a>");
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