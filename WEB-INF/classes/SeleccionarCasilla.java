import javax.servlet.http.*;
import java.sql.*;
import java.io.*;

public class SeleccionarCasilla extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Connection con = null;
        PreparedStatement psUpdate = null, psTablero = null, psPregunta = null;
        ResultSet rsTablero = null, rsPregunta = null;
        int nuevaPosicion = 0;
<<<<<<< HEAD
        int IdPregunta = 0;
        
=======
        String idPregunta = "0";
        //int IdPartida = 0;
>>>>>>> 1ffbded3fa364adaf181810a257765f0656f5bdf
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
<<<<<<< HEAD
            String idJugador = req.getParameter("idJugador");
            String aciertoPregunta = req.getParameter("acierto");
            int acierto = -1;

            if (aciertoPregunta != null && !aciertoPregunta.isEmpty()) {
                acierto = Integer.parseInt(aciertoPregunta);
            }
            System.out.println("Valor de acierto recibido: " + acierto);

            if (nuevaCasilla != null && idJugador != null) {
                nuevaPosicion = Integer.parseInt(nuevaCasilla);
                
                SQL = "UPDATE detallespartida SET NumCasilla = " + nuevaPosicion +  " WHERE IdPartida = 1 AND IdJugador = " + idJugador;
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
=======
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
>>>>>>> 1ffbded3fa364adaf181810a257765f0656f5bdf
                }
                
                if (IdPregunta != 0 || acierto == 0) {
                    if (idJugador.equals("1")) {
                        st.executeUpdate("UPDATE detallespartida SET Turno = 0 WHERE IdPartida = 1 AND IdJugador = 1");
                        st.executeUpdate("UPDATE detallespartida SET Turno = 1 WHERE IdPartida = 1 AND IdJugador = 2");
                    } else if (idJugador.equals("2")) {
                        st.executeUpdate("UPDATE detallespartida SET Turno = 0 WHERE IdPartida = 1 AND IdJugador = 2");
                        st.executeUpdate("UPDATE detallespartida SET Turno = 1 WHERE IdPartida = 1 AND IdJugador = 1");
                    }
                } 
            }
<<<<<<< HEAD
            st.close();
            con.close();
=======

>>>>>>> 1ffbded3fa364adaf181810a257765f0656f5bdf
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
