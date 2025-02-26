import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;

public class CrearPartida extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        // Obtener el nombre de la partida desde el formulario
        String nombrePartida = req.getParameter("nombrePartida");

        // Obtener el id del creador de la partida desde la sesión
        HttpSession session = req.getSession();
        Integer idCreador = (Integer) session.getAttribute("IdJugador");

        try {
            // Conexión a la base de datos
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "");

            // Crear la nueva partida con nombre generado por la base de datos
            PreparedStatement ps = con.prepareStatement("INSERT INTO partida (nombrePartida, idCreador, activa) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, nombrePartida);  // Utiliza un nombre de partida, si quieres otro tipo de nombre, como la ID, puedes dejarlo vacío
            ps.setInt(2, idCreador);
            ps.setBoolean(3, true);  // Estado inicial de la partida es 'creada'
                
            int resultado = ps.executeUpdate();
                
            if (resultado > 0) {
                // Obtener el id de la nueva partida generada
                ResultSet rs = ps.getGeneratedKeys();
                int idPartidaGenerada = -1;
                if (rs.next()) {
                    idPartidaGenerada = rs.getInt(1);  // Obtén el ID de la partida recién creada

                    PreparedStatement ps2 = con.prepareStatement("INSERT INTO detallespartida (IdJugador, IdPartida, Turno, NumCasilla)"+
                    "VALUES (?, ?, 1, 1)", Statement.RETURN_GENERATED_KEYS); // Insertar el creador de la partida en la tabla detallespartida
                    ps2.setInt(1, idCreador);
                    ps2.setInt(2, idPartidaGenerada);
                    ps2.executeUpdate();

                    session.setAttribute("IdPartida", idPartidaGenerada);  // Guardar el id de la partida en la sesión
                    session.setAttribute("Turno", 1);  // Guardar el turno en la sesión
                    ps2.close();
                }
                
                rs.close();
                // Mostrar un mensaje con el idPartida o redirigir a la página de inicio de partida
                out.println("<h2>Partida con ID " + idPartidaGenerada + " creada exitosamente.</h2>");
                res.sendRedirect("Tablero?IdPartida=" + idPartidaGenerada);  // Redirigir a la página principal de la partida
            } else {
                out.println("<h2>Hubo un error al crear la partida. Intenta de nuevo.</h2>");
            }

            ps.close();
            con.close();
        } catch (Exception e) {
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        }

        out.close();
    }
}