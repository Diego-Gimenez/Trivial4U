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
        String IdPartida = req.getParameter("IdPartida");
        int idPartida = -1;

        if (IdPartida != null && !IdPartida.isEmpty()) {
            idPartida = Integer.parseInt(IdPartida);
        }

        if (pregunta == null || respuesta == null) {
            res.setContentType("text/html");
            out = res.getWriter();
            out.println("<html><body>");
            out.println("<h2>Error: Falta información.</h2>");
            out.println("<p>No se ha recibido una pregunta o respuesta válida.</p>");
            out.println("<a href='Tablero'>Volver al juego</a>");
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
                        acierto = 1;
                        // ACIERTO - Continua el turno del jugador + Permitir otro lanzamiento de dado
                    } else {
                        acierto = 0;
                        // FALLO - Termina el turno del jugador
                    }
                } else {
                    out.println("<h2>Error</h2>");
                    out.println("<p>No se encontró la pregunta en la base de datos.</p>");
                }

                res.sendRedirect("Tablero?acierto=" + acierto + "&IdPartida=" + idPartida);
                return;
            }
            catch (Exception e) {
                System.err.println(e);
            }
    }
}