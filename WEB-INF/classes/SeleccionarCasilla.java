import javax.servlet.http.*;
import java.sql.*;
import java.io.*;

public class SeleccionarCasilla extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Connection con;
        Statement st, st2, st3;
        String SQL, SQL2, SQL3;
        ResultSet rs2, rs3;
        int nuevaPosicion = 0;
        int IdPregunta = 0;
        int idPartida = 0;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "");
            st = con.createStatement();

            String nuevaCasilla = req.getParameter("casilla");
            String idJugador = req.getParameter("idJugador");
            String aciertoPregunta = req.getParameter("acierto");
            String IdPartida = req.getParameter("idPartida");
            int acierto = -1;

            if (aciertoPregunta != null && !aciertoPregunta.isEmpty()) {
                acierto = Integer.parseInt(aciertoPregunta);
            }
            System.out.println("Valor de acierto recibido: " + acierto);

            if (IdPartida != null && !IdPartida.isEmpty()) {
                idPartida = Integer.parseInt(IdPartida);
            }

            if (nuevaCasilla != null && idJugador != null) {
                nuevaPosicion = Integer.parseInt(nuevaCasilla);
                
                SQL = "UPDATE detallespartida SET NumCasilla = " + nuevaPosicion +  " WHERE IdPartida = " + idPartida + " AND IdJugador = " + idJugador;
                st.executeUpdate(SQL);

                st2 = con.createStatement();
                SQL2 = "SELECT IdCategoria from tablero WHERE NumeroCasilla = " + nuevaPosicion;
                rs2 = st2.executeQuery(SQL2);
                int categoria = 0;

                if (rs2.next()) {
                    categoria = rs2.getInt("IdCategoria");
                }

                st3 = con.createStatement();
                SQL3 = "SELECT IdPregunta FROM preguntas WHERE IdCategoria = " + categoria +  " ORDER BY RAND() LIMIT 1";
                rs3 = st3.executeQuery(SQL3);
                
                if (rs3.next()) {
                    IdPregunta = rs3.getInt("IdPregunta");
                } else {
                    IdPregunta = 0;
                }
            }
            st.close();
            con.close();
        } catch (Exception e) {
            System.err.println(e);
        }

        res.sendRedirect("Tablero?numCasilla=" + nuevaPosicion + "&idpregunta=" + IdPregunta + "&IdPartida=" + idPartida);
    }
}