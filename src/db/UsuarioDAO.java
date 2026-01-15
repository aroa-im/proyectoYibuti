package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import domain.Admin;
import domain.Cliente;
import domain.LogAccion;
import domain.Review;
import domain.Usuario;
import main.main;

public class UsuarioDAO implements UsuarioDAOInterface {

    private Connection conexionBD;
    private Logger logger;

    public UsuarioDAO() {
        this.conexionBD = main.getConexionBD();
        this.logger = main.getLogger();
    }

    public UsuarioDAO(Connection conexionBD, Logger logger) {
        this.conexionBD = conexionBD;
        this.logger = logger;
    }


    @Override
    public boolean addUsuario(Usuario usuario) {
        try {
            String insertSQL = "INSERT INTO Usuario (dni, nombre, email, contrasena) VALUES (?,?,?,?)";
            PreparedStatement preparedStmt = conexionBD.prepareStatement(insertSQL);
            preparedStmt.setString(1, usuario.getDni());
            preparedStmt.setString(2, usuario.getNombre());
            preparedStmt.setString(3, usuario.getEmail());
            preparedStmt.setString(4, usuario.getContrasena());

            int filas = preparedStmt.executeUpdate();
            preparedStmt.close();

            PreparedStatement preparedStmt2;
            if (usuario instanceof Cliente) {
                preparedStmt2 = conexionBD.prepareStatement(
                        "INSERT INTO Cliente (dni, amonestaciones) VALUES (?,?)");
                preparedStmt2.setString(1, usuario.getDni());
                preparedStmt2.setInt(2, ((Cliente) usuario).getAmonestaciones());
            } else {
                preparedStmt2 = conexionBD.prepareStatement(
                        "INSERT INTO Admin (dni) VALUES (?)");
                preparedStmt2.setString(1, usuario.getDni());
            }

            int filas2 = preparedStmt2.executeUpdate();
            preparedStmt2.close();

            return filas > 0 && filas2 > 0;

        } catch (SQLException e) {
            if (logger != null)
                logger.log(Level.SEVERE, "Error al añadir el usuario", e);
            return false;
        }
    }


    @Override
    public boolean deleteUsuario(String dni) {
        try {
            PreparedStatement preparedStmt =
                    conexionBD.prepareStatement("DELETE FROM Usuario WHERE dni = ?");
            preparedStmt.setString(1, dni);
            preparedStmt.executeUpdate();
            preparedStmt.close();
            return true;
        } catch (SQLException e) {
            if (logger != null)
                logger.log(Level.SEVERE, "Error al eliminar el usuario", e);
            return false;
        }
    }


    @Override
    public boolean isUsuarioCorrecto(String dni, String password) {
        try {
            PreparedStatement preparedStmt = conexionBD.prepareStatement(
                    "SELECT 1 FROM Usuario WHERE dni = ? AND contrasena = ?");
            preparedStmt.setString(1, dni);
            preparedStmt.setString(2, password);

            ResultSet rs = preparedStmt.executeQuery();
            boolean correcto = rs.next();
            preparedStmt.close();
            return correcto;

        } catch (SQLException e) {
            if (logger != null)
                logger.log(Level.SEVERE, "Error en login", e);
            return false;
        }
    }

    @Override
    public UsuarioDTO getUsuario(String dni) {
        UsuarioDTO usuario = null;

        try {
            PreparedStatement preparedStmt = conexionBD.prepareStatement(
                    "SELECT nombre, email FROM Usuario WHERE dni = ?");
            preparedStmt.setString(1, dni);

            ResultSet rs = preparedStmt.executeQuery();
            if (rs.next()) {
                usuario = new UsuarioDTO();
                usuario.setDni(dni);
                usuario.setNombre(rs.getString("nombre"));
                usuario.setEmail(rs.getString("email"));
            }
            preparedStmt.close();

            if (usuario != null) {
                getDatosAdicionalesUsuario(usuario);
            }

        } catch (SQLException e) {
            if (logger != null)
                logger.log(Level.SEVERE, "Error al recuperar usuario", e);
        }

        return usuario;
    }


    @Override
    public ArrayList<Usuario> getUsuarios() {

        ArrayList<Usuario> result = new ArrayList<>();

        String sql =
            "SELECT u.dni, u.nombre, u.email, u.contrasena, c.amonestaciones, " +
            "CASE WHEN a.dni IS NOT NULL THEN 'ADMIN' " +
            "     WHEN c.dni IS NOT NULL THEN 'CLIENTE' END AS tipo_usuario " +
            "FROM Usuario u " +
            "LEFT JOIN Cliente c ON u.dni = c.dni " +
            "LEFT JOIN Admin a ON u.dni = a.dni";

        try (PreparedStatement ps = conexionBD.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                String contrasena = rs.getString("contrasena");
                String tipo = rs.getString("tipo_usuario");

                if ("ADMIN".equals(tipo)) {

                    ArrayList<LogAccion> logs = getLogAccionesByAdminDni(dni);
                    Admin admin = new Admin(dni, nombre, email, contrasena,
                            new ArrayList<>(), logs);
                    result.add(admin);

                } else {

                    int amonestaciones = rs.getInt("amonestaciones");

                    ArrayList<Review> reviews = new ArrayList<>();
                    if (main.getReviewDAO() != null) {
                        reviews = main.getReviewDAO().getReviewsByUsuarioDni(dni);
                    }

                    Cliente cliente = new Cliente(
                            dni, nombre, email, contrasena,
                            new ArrayList<>(),
                            reviews,
                            amonestaciones
                    );

                    
                    if (main.getAlquilerDAO() != null) {
                        cliente.setHistorial(
                                main.getAlquilerDAO()
                                        .getProductosAlquiladosByUsuario(dni)
                        );
                    }

                    result.add(cliente);
                }
            }

        } catch (SQLException e) {
            if (logger != null)
                logger.log(Level.SEVERE, "Error al obtener usuarios", e);
        }

        return result;
    }


    @Override
    public void getDatosAdicionalesUsuario(UsuarioDTO usuario) {
        try {
            PreparedStatement ps = conexionBD.prepareStatement(
                    "SELECT amonestaciones FROM Cliente WHERE dni = ?");
            ps.setString(1, usuario.getDni());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario.setAmonestaciones(rs.getInt("amonestaciones"));
                usuario.setAdmin(false);
            } else {
                usuario.setAdmin(true);
            }
            ps.close();

        } catch (SQLException e) {
            if (logger != null)
                logger.log(Level.SEVERE, "Error datos adicionales", e);
        }
    }

    @Override
    public ArrayList<LogAccion> getLogAccionesByAdminDni(String dniAdmin) {

        ArrayList<LogAccion> logs = new ArrayList<>();

        try {
            PreparedStatement ps = conexionBD.prepareStatement(
                    "SELECT * FROM LogAccion WHERE dni_admin = ?");
            ps.setString(1, dniAdmin);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                logs.add(new LogAccion(
                        rs.getInt("id"),
                        LocalDateTime.parse(rs.getString("fecha")),
                        rs.getString("descripcion"),
                        dniAdmin
                ));
            }
            ps.close();

        } catch (SQLException e) {
            if (logger != null)
                logger.log(Level.SEVERE, "Error logs admin", e);
        }

        return logs;
    }

    @Override
    public void borrarRegistros() {
        try (Statement stmt = conexionBD.createStatement()) {
            stmt.executeUpdate(
                "DELETE FROM LogAccion;" +
                "DELETE FROM Cliente;" +
                "DELETE FROM Admin;" +
                "DELETE FROM Usuario;"
            );
        } catch (SQLException e) {
            if (logger != null)
                logger.log(Level.SEVERE, "Error al borrar registros", e);
        }
    }

    @Override
    public boolean updateUsuario(Usuario usuario) {
        try {
            String sql = "UPDATE Usuario SET nombre = ?, email = ? WHERE dni = ?";
            PreparedStatement ps = conexionBD.prepareStatement(sql);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getDni());

            int filas = ps.executeUpdate();
            ps.close();

            return filas > 0;

        } catch (SQLException e) {
            if (logger != null)
                logger.log(Level.SEVERE, "Error al actualizar usuario", e);
            return false;
        }
    }

    @Override
    public boolean updatePassword(UsuarioDTO usuario, String password) {
        try {
            String sql = "UPDATE Usuario SET contrasena = ? WHERE dni = ?";
            PreparedStatement ps = conexionBD.prepareStatement(sql);
            ps.setString(1, password);
            ps.setString(2, usuario.getDni());

            int filas = ps.executeUpdate();
            ps.close();

            return filas > 0;

        } catch (SQLException e) {
            if (logger != null)
                logger.log(Level.SEVERE, "Error al actualizar contraseña", e);
            return false;
        }
    }

    @Override
    public boolean addLogAccion(LogAccion logAccion) {
        try {
            String sql = "INSERT INTO LogAccion VALUES (NULL, ?, ?, ?)";
            PreparedStatement ps = conexionBD.prepareStatement(sql);
            ps.setString(1, logAccion.getFecha().toString());
            ps.setString(2, logAccion.getDescripcion());
            ps.setString(3, logAccion.getDniAdmin());

            int filas = ps.executeUpdate();
            ps.close();

            return filas > 0;

        } catch (SQLException e) {
            if (logger != null)
                logger.log(Level.SEVERE, "Error al añadir log de acción", e);
            return false;
        }
    }
    @Override
    public boolean updateAmonestaciones(UsuarioDTO usuario, int nuevasAmonestaciones) {
        try {
            String sql = "UPDATE Cliente SET amonestaciones = ? WHERE dni = ?";
            PreparedStatement ps = conexionBD.prepareStatement(sql);
            ps.setInt(1, nuevasAmonestaciones);
            ps.setString(2, usuario.getDni());

            int filas = ps.executeUpdate();
            ps.close();

            return filas > 0;

        } catch (SQLException e) {
            if (logger != null)
                logger.log(Level.SEVERE, "Error al actualizar amonestaciones", e);
            return false;
        }
    }
}