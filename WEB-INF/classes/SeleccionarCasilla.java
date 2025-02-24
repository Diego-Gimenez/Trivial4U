import javax.servlet.http.*;
import java.sql.*;
import java.io.*;

public class SeleccionarCasilla extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Connection con;
        Statement st;
        String SQL;
        int nuevaPosicion = 0;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "");
            st = con.createStatement();

            String nuevaCasilla = req.getParameter("casilla");
            if (nuevaCasilla != null) {
                nuevaPosicion = Integer.parseInt(nuevaCasilla);
                SQL = "UPDATE detallespartida SET NumCasilla = " + nuevaPosicion + " WHERE IdJugador = 1";
                st.executeUpdate(SQL);
            }

            st.close();
            con.close();
        } catch (Exception e) {
            System.err.println(e);
        }

        res.sendRedirect("tablero?numCasilla=" + nuevaPosicion);
    }
}