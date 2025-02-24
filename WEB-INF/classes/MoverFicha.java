import javax.servlet.http.*;
import java.sql.*;
import java.io.*;

public class MoverFicha extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Connection con;
        Statement st;
        ResultSet rs;
        PrintWriter out;
        String SQL;
        String resultado = req.getParameter("numero");
        int resultadoDado = Integer.parseInt(resultado);
        int posicionActual = 1;
        int nuevaPosicion1 = 0;
        int nuevaPosicion2 = 0;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "");
            st = con.createStatement();
            SQL = "SELECT * FROM detallespartida";
            rs = st.executeQuery(SQL);
            out = res.getWriter();
            res.setContentType("text/html");
            out.println("<h1>Debug: MoverFicha funcionando</h1>");

            if (rs.next()) {
                posicionActual = rs.getInt("NumCasilla");
            }

            nuevaPosicion1 = posicionActual + resultadoDado;
            if (nuevaPosicion1 > 32) {
                nuevaPosicion1 = nuevaPosicion1 - 32;
            }

            nuevaPosicion2 = posicionActual - resultadoDado;
            if (nuevaPosicion2 < 1) {
                nuevaPosicion2 = nuevaPosicion2 + 32;
            }
            out.println("<p>Posición actual: " + posicionActual + "</p>");
            out.println("<p>Nueva Posición 1: " + nuevaPosicion1 + "</p>");
            out.println("<p>Nueva Posición 2: " + nuevaPosicion2 + "</p>");
            res.sendRedirect("Tablero?numero=" + resultado + "&pos1=" + nuevaPosicion1 + "&pos2=" + nuevaPosicion2);
            return;
        }

        catch (Exception e) {
            e.printStackTrace();
            System.out.println("<p>Error en el servidor: " + e.getMessage() + "</p>");
        }
    }
}