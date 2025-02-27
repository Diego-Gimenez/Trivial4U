import javax.servlet.http.*;
import java.sql.*;
import java.io.*;

public class MoverFicha extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Connection con;
        Statement st;
        ResultSet rs;
        PrintWriter out = res.getWriter();
        String SQL;
        String resultado = req.getParameter("numero");
        String IdPartida = req.getParameter("IdPartida");

        if (resultado == null || resultado.isEmpty()) {
            out.println("<h1>Error: Parámetro 'numero' no recibido</h1>");
            return;
        }

        int resultadoDado = Integer.parseInt(resultado);
        int posicionActual = 1;
        int nuevaPosicion1 = 0;
        int nuevaPosicion2 = 0;

        int idPartida = -1;
        if (IdPartida != null && !IdPartida.isEmpty()) {
            idPartida = Integer.parseInt(IdPartida);
        }

        /* 
        String aciertoPregunta = req.getParameter("acierto");
        int acierto = -1;
        if (aciertoPregunta != null && !aciertoPregunta.isEmpty()) {
            acierto = Integer.parseInt(aciertoPregunta);
        }
        */

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "");

            st = con.createStatement();
            SQL = "SELECT * FROM detallespartida WHERE IdPartida = " + idPartida + " AND Turno = 1";
            rs = st.executeQuery(SQL);

            out = res.getWriter();
            res.setContentType("text/html");

            int turno = 0;

            if (rs.next()) {
                turno = rs.getInt("Turno");
                System.out.println("Turno J1: " + turno);
            }
            
            if (turno == 1) {
                posicionActual = rs.getInt("NumCasilla");

                nuevaPosicion1 = posicionActual + resultadoDado;
                if (nuevaPosicion1 > 32) {
                    nuevaPosicion1 = nuevaPosicion1 - 32;
                }

                nuevaPosicion2 = posicionActual - resultadoDado;
                if (nuevaPosicion2 < 1) {
                    nuevaPosicion2 = nuevaPosicion2 + 32;
                }
                res.sendRedirect("Tablero?numero=" + resultado + "&pos1=" + nuevaPosicion1 + "&pos2=" + nuevaPosicion2 + "&IdPartida=" + idPartida);
                return;
            }

            System.out.println("Error con los turnos");
            res.sendRedirect("Tablero?NumCasilla=" + posicionActual + "&IdPartida=" + idPartida);
            // res.sendRedirect("Tablero?NumCasilla&acierto=" + acierto);
            return;
        }

        catch (NumberFormatException e) {
            out.println("<h1>Error: Número inválido</h1>");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h1>Error en el servidor: " + e.getMessage() + "</h1>");
        } 
    }
}