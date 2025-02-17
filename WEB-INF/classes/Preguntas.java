import javax.servlet.http.*;
import java.sql.*;
import java.io.*;

public class Preguntas extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        Connection con;
        Statement st;
        ResultSet rs;
        PrintWriter out;
        String SQL;
        // String casilla = req.getParameter("casilla");
        String categoria = req.getParameter("categoria");
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "");
                st = con.createStatement();
                SQL = "SELECT * FROM preguntas WHERE IdCategoria =" + categoria;
                rs = st.executeQuery(SQL);
                out = res.getWriter();
                res.setContentType("text/html");

                out.println("<HTML><BODY>");
                out.println("<INPUT TYPE=TEXT VALUE=" + rs.getString(2) + ">"); //Pregunta (no aleatoria de momento)
                out.println("<FORM ACTION=ValidarRespuesta METHOD=GET>");
                out.println("<SELECT NAME=Seleccione una respuesta>");
                out.println("<OPTION VALUE=a>" + rs.getString(3)); // Posible respuesta 1
                out.println("<OPTION VALUE=b>" + rs.getString(4)); // Posible respuesta 2
                out.println("<OPTION VALUE=b>" + rs.getString(5)); // Posible respuesta 3
                out.println("<OPTION VALUE=d>" + rs.getString(6)); // Posible respuesta 4
                out.println("</SELECT><INPUT TYPE=SUBMIT VALUE=Confirmar respuesta></FORM>");
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