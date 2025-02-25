import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import java.io.*;

public class SeleccionarCasilla extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Connection con = null;
        PreparedStatement psUpdate = null;
        PreparedStatement psSelectTablero = null;
        PreparedStatement psSelectPregunta = null;
        ResultSet rsTablero = null;
        ResultSet rsPregunta = null;
        int nuevaPosicion = 0;
        String idPregunta = "0";

        try {
            // Obtener la sesión y validar que el usuario ha iniciado sesión
            HttpSession session = req.getSession();
            Integer idJugador = (Integer) session.getAttribute("IdJugador");
            Integer idPartida = (Integer) session.getAttribute("IdPartida"); // Obtener la partida actual de la sesión

            if (idJugador == null || idPartida == null) {
                res.sendRedirect("login.html"); // Si no hay sesión válida, redirigir al login
                return;
            }

            // Obtener la nueva casilla desde el formulario
            String nuevaCasilla = req.getParameter("casilla");
            if (nuevaCasilla != null) {
                nuevaPosicion = Integer.parseInt(nuevaCasilla);

                // Conectar con la base de datos
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "");

                // Actualizar solo la casilla del jugador en la partida actual
                String sqlUpdate = "UPDATE detallespartida SET NumCasilla = ? WHERE IdJugador = ? AND IdPartida = ?";
                psUpdate = con.prepareStatement(sqlUpdate);
                psUpdate.setInt(1, nuevaPosicion);
                psUpdate.setInt(2, idJugador);
                psUpdate.setInt(3, idPartida);
                psUpdate.executeUpdate();

                // Obtener la categoría de la casilla seleccionada
                String sqlSelectTablero = "SELECT IdCategoria FROM tablero WHERE NumeroCasilla = ?";
                psSelectTablero = con.prepareStatement(sqlSelectTablero);
                psSelectTablero.setInt(1, nuevaPosicion);
                rsTablero = psSelectTablero.executeQuery();

                int categoria = 0;
                if (rsTablero.next()) {
                    categoria = rsTablero.getInt("IdCategoria");
                }

                // Seleccionar una pregunta aleatoria de la categoría obtenida
                String sqlSelectPregunta = "SELECT IdPregunta FROM preguntas WHERE IdCategoria = ? ORDER BY RAND() LIMIT 1";
                psSelectPregunta = con.prepareStatement(sqlSelectPregunta);
                psSelectPregunta.setInt(1, categoria);
                rsPregunta = psSelectPregunta.executeQuery();

                if (rsPregunta.next()) {
                    idPregunta = rsPregunta.getString("IdPregunta");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos para evitar fugas de memoria
            try {
                if (rsTablero != null) rsTablero.close();
                if (rsPregunta != null) rsPregunta.close();
                if (psUpdate != null) psUpdate.close();
                if (psSelectTablero != null) psSelectTablero.close();
                if (psSelectPregunta != null) psSelectPregunta.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Redirigir al tablero con la nueva posición y la pregunta asignada
        res.sendRedirect("Tablero?numCasilla=" + nuevaPosicion + "&idpregunta=" + idPregunta);
    }
}
