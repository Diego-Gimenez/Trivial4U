import javax.servlet.http.*;
import java.sql.*;
import java.io.*;

public class SeleccionarCasilla extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Connection con = null;
        PreparedStatement psUpdate = null, psTablero = null, psPregunta = null;
        ResultSet rsTablero = null, rsPregunta = null;
        int nuevaPosicion = 0;
        String idPregunta = "0";
        //int IdPartida = 0;
        try {
            // Obtener la sesión y el IdJugador
            HttpSession session = req.getSession();
            Integer idJugador = (Integer) session.getAttribute("IdJugador");
            
            if (idJugador == null) {
                res.sendRedirect("login.html"); // Si no hay sesión, redirige al login
                return;
            }

            // Obtener la nueva casilla desde el formulario
            String nuevaCasilla = req.getParameter("casilla");
            //String idPartida = req.getParameter("IdPartida");
            //IdPartida = Integer.parseInt(idPartida);
            if (nuevaCasilla != null) {
                nuevaPosicion = Integer.parseInt(nuevaCasilla);

                // Conectar con la base de datos
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "");

                // Actualizar la casilla del jugador en detallespartida
                String sqlUpdate = "UPDATE detallespartida SET NumCasilla = ? WHERE IdJugador = ?";
                psUpdate = con.prepareStatement(sqlUpdate);
                psUpdate.setInt(1, nuevaPosicion);  // Establece la nueva posición de la casilla
                psUpdate.setInt(2, idJugador);      // Establece el id del jugador
                psUpdate.executeUpdate();


                // Obtener la categoría de la casilla
                String sqlTablero = "SELECT IdCategoria FROM tablero WHERE NumeroCasilla = ?";
                psTablero = con.prepareStatement(sqlTablero);
                psTablero.setInt(1, nuevaPosicion);
                rsTablero = psTablero.executeQuery();
                
                int categoria = 0;
                if (rsTablero.next()) {
                    categoria = rsTablero.getInt("IdCategoria");
                }

                // Obtener una pregunta aleatoria de esa categoría
                String sqlPregunta = "SELECT IdPregunta FROM preguntas WHERE IdCategoria = ? ORDER BY RAND() LIMIT 1";
                psPregunta = con.prepareStatement(sqlPregunta);
                psPregunta.setInt(1, categoria);
                rsPregunta = psPregunta.executeQuery();

                if (rsPregunta.next()) {
                    idPregunta = rsPregunta.getString("IdPregunta");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (rsTablero != null) rsTablero.close();
                if (rsPregunta != null) rsPregunta.close();
                if (psUpdate != null) psUpdate.close();
                if (psTablero != null) psTablero.close();
                if (psPregunta != null) psPregunta.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Redirigir al tablero con los nuevos datos
        res.sendRedirect("Tablero?numCasilla=" + nuevaPosicion + "&idpregunta=" + idPregunta);
    }
}
