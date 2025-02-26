import javax.servlet.http.*;
import java.sql.*;
import java.io.*;

public class MoverFicha extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Connection con;
        Statement stJ1, stJ2;
        ResultSet rsJ1, rsJ2;
        PrintWriter out = res.getWriter();
        String SQLJ1, SQLJ2;
        String resultado = req.getParameter("numero");
        if (resultado == null || resultado.isEmpty()) {
            out.println("<h1>Error: Parámetro 'numero' no recibido</h1>");
            return;
        }
        int resultadoDado = Integer.parseInt(resultado);
        int posicionActualJ1 = 1;
        int nuevaPosicion1J1 = 0;
        int nuevaPosicion2J1 = 0;

        int posicionActualJ2 = 1;
        int nuevaPosicion1J2 = 0;
        int nuevaPosicion2J2 = 0;
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

            stJ1 = con.createStatement();
            SQLJ1 = "SELECT * FROM detallespartida WHERE IdPartida = 1 AND IdJugador = 1";
            rsJ1 = stJ1.executeQuery(SQLJ1);

            stJ2 = con.createStatement();
            SQLJ2 = "SELECT * FROM detallespartida WHERE IdPartida = 1 AND IdJugador = 2";
            rsJ2 = stJ2.executeQuery(SQLJ2);

            out = res.getWriter();
            res.setContentType("text/html");

            int turnoJ1 = 0;
            int turnoJ2 = 0;

            if (rsJ1.next()) {
                turnoJ1 = rsJ1.getInt("Turno");
                System.out.println("Turno J1: " + turnoJ1);
            }
            
            if (rsJ2.next()) {
                turnoJ2 = rsJ2.getInt("Turno");
                System.out.println("Turno J2: " + turnoJ2);
            }
            
            if (turnoJ1 == 1) {
                posicionActualJ1 = rsJ1.getInt("NumCasilla");

                nuevaPosicion1J1 = posicionActualJ1 + resultadoDado;
                if (nuevaPosicion1J1 > 32) {
                    nuevaPosicion1J1 = nuevaPosicion1J1 - 32;
                }

                nuevaPosicion2J1 = posicionActualJ1 - resultadoDado;
                if (nuevaPosicion2J1 < 1) {
                    nuevaPosicion2J1 = nuevaPosicion2J1 + 32;
                }
                res.sendRedirect("tablero?numero=" + resultado + "&pos1=" + nuevaPosicion1J1 + "&pos2=" + nuevaPosicion2J1);
                return;

            } else if (turnoJ2 == 1) {
                posicionActualJ2 = rsJ2.getInt("NumCasilla");
                
                nuevaPosicion1J2 = posicionActualJ2 + resultadoDado;
                if (nuevaPosicion1J2 > 32) {
                    nuevaPosicion1J2 = nuevaPosicion1J2 - 32;
                }

                nuevaPosicion2J2 = posicionActualJ2 - resultadoDado;
                if (nuevaPosicion2J2 < 1) {
                    nuevaPosicion2J2 = nuevaPosicion2J2 + 32;
                } 
                res.sendRedirect("tablero?numero=" + resultado + "&pos1=" + nuevaPosicion1J2 + "&pos2=" + nuevaPosicion2J2);
                return;
            }

            out.println("<p>Error con los turnos</p>");
            res.sendRedirect("tablero?NumCasilla");
            // res.sendRedirect("tablero?NumCasilla&acierto=" + acierto);
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