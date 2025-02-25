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
        String IdPregunta = "0";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "");
            st = con.createStatement();

            String nuevaCasilla = req.getParameter("casilla");
            if (nuevaCasilla != null) {
                nuevaPosicion = Integer.parseInt(nuevaCasilla);
                SQL = "UPDATE detallespartida SET NumCasilla = " + nuevaPosicion + " WHERE IdJugador = 1";
                st.executeUpdate(SQL);

                st2 = con.createStatement();
                SQL2 = "SELECT * from tablero WHERE NumeroCasilla = " + nuevaPosicion;
                rs2 = st2.executeQuery(SQL2);
                int categoria = 0;
                if (rs2.next()) {
                    String IdCat = rs2.getString("IdCategoria");
                    categoria = Integer.parseInt(IdCat);
                }

                st3 = con.createStatement();
                SQL3 = "SELECT IdPregunta FROM preguntas WHERE IdCategoria = " + categoria +  " ORDER BY RAND() LIMIT 1";
                rs3 = st3.executeQuery(SQL3);
                if (rs3.next()) {
                    IdPregunta = rs3.getString("IdPregunta");
                } else {
                    IdPregunta = "0";
                }
            }

            st.close();
            con.close();
        } catch (Exception e) {
            System.err.println(e);
        }

        res.sendRedirect("Tablero?numCasilla=" + nuevaPosicion + "&idpregunta=" + IdPregunta);
    }
}