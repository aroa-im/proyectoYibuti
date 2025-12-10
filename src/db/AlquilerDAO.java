package db;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import domain.Alquiler;
import domain.Cliente;
import domain.Producto;
import main.main;

public class AlquilerDAO {
    private Connection conexionBD;
    private Logger logger;
    private ProductoDAO productoDAO;

    public AlquilerDAO() {
        this.conexionBD = main.getConexionBD();
        this.logger = main.getLogger();
        this.productoDAO = new ProductoDAO();
    }

    public boolean insertar(Alquiler alquiler) {
        String sql = "INSERT INTO Alquiler (fecha_inicio, fecha_fin, devuelto, id_producto, dni_cliente) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conexionBD.prepareStatement(sql)) {
            pstmt.setString(1, alquiler.getFechaInicio().toString());
            pstmt.setString(2, alquiler.getFechaFin().toString());
            pstmt.setInt(3, alquiler.isDevuelto());
            pstmt.setInt(4, (int) alquiler.getProducto().getId());
            pstmt.setString(5, alquiler.getCliente().getDni());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al insertar alquiler: ", e);
            return false;
        }
    }

    // Obtener un alquiler por ID
    public Alquiler obtenerPorId(int id) {
        String sql = "SELECT * FROM Alquiler WHERE id = ?";
        
        try (PreparedStatement pstmt = conexionBD.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapearAlquiler(rs);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener alquiler por ID: ", e);
        }
        return null;
    }

    // Obtener todos los alquileres
    public List<Alquiler> obtenerTodos() {
        List<Alquiler> alquileres = new ArrayList<>();
        String sql = "SELECT * FROM Alquiler";
        
        try (Statement stmt = conexionBD.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                alquileres.add(mapearAlquiler(rs));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener todos los alquileres: ", e);
        }
        return alquileres;
    }

    // Obtener alquileres por cliente
    public List<Alquiler> obtenerPorCliente(String dniCliente) {
        List<Alquiler> alquileres = new ArrayList<>();
        String sql = "SELECT * FROM Alquiler WHERE dni_cliente = ?";
        
        try (PreparedStatement pstmt = conexionBD.prepareStatement(sql)) {
            pstmt.setString(1, dniCliente);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                alquileres.add(mapearAlquiler(rs));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener alquileres por cliente: ", e);
        }
        return alquileres;
    }

    // Obtener alquileres activos (no devueltos) de un cliente
    public List<Alquiler> obtenerActivosPorCliente(String dniCliente) {
        List<Alquiler> alquileres = new ArrayList<>();
        String sql = "SELECT * FROM Alquiler WHERE dni_cliente = ? AND devuelto = 0";
        
        try (PreparedStatement pstmt = conexionBD.prepareStatement(sql)) {
            pstmt.setString(1, dniCliente);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                alquileres.add(mapearAlquiler(rs));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener alquileres activos por cliente: ", e);
        }
        return alquileres;
    }

    // Obtener alquileres por producto
    public List<Alquiler> obtenerPorProducto(int idProducto) {
        List<Alquiler> alquileres = new ArrayList<>();
        String sql = "SELECT * FROM Alquiler WHERE id_producto = ?";
        
        try (PreparedStatement pstmt = conexionBD.prepareStatement(sql)) {
            pstmt.setInt(1, idProducto);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                alquileres.add(mapearAlquiler(rs));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener alquileres por producto: ", e);
        }
        return alquileres;
    }

    // Actualizar un alquiler
    public boolean actualizar(Alquiler alquiler) {
        String sql = "UPDATE Alquiler SET fecha_inicio = ?, fecha_fin = ?, devuelto = ?, id_producto = ?, dni_cliente = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = conexionBD.prepareStatement(sql)) {
            pstmt.setString(1, alquiler.getFechaInicio().toString());
            pstmt.setString(2, alquiler.getFechaFin().toString());
            pstmt.setInt(3, alquiler.isDevuelto());
            pstmt.setInt(4, (int) alquiler.getProducto().getId());
            pstmt.setString(5, alquiler.getCliente().getDni());
            pstmt.setInt(6, alquiler.getId());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al actualizar alquiler: ", e);
            return false;
        }
    }

    // Marcar como devuelto
    public boolean marcarComoDevuelto(int id) {
        String sql = "UPDATE Alquiler SET devuelto = 1 WHERE id = ?";
        
        try (PreparedStatement pstmt = conexionBD.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al marcar alquiler como devuelto: ", e);
            return false;
        }
    }

    // Verificar si un producto está actualmente alquilado
    public boolean productoEstaAlquilado(int idProducto) {
        String sql = "SELECT COUNT(*) FROM Alquiler WHERE id_producto = ? AND devuelto = 0";
        
        try (PreparedStatement pstmt = conexionBD.prepareStatement(sql)) {
            pstmt.setInt(1, idProducto);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al verificar si producto está alquilado: ", e);
        }
        return false;
    }

    // Obtener alquileres vencidos (fecha_fin pasada y no devueltos)
    public List<Alquiler> obtenerAlquileresVencidos() {
        List<Alquiler> alquileres = new ArrayList<>();
        String sql = "SELECT * FROM Alquiler WHERE devuelto = 0 AND fecha_fin < ?";
        
        try (PreparedStatement pstmt = conexionBD.prepareStatement(sql)) {
            pstmt.setString(1, LocalDate.now().toString());
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                alquileres.add(mapearAlquiler(rs));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener alquileres vencidos: ", e);
        }
        return alquileres;
    }

    // Eliminar un alquiler
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Alquiler WHERE id = ?";
        
        try (PreparedStatement pstmt = conexionBD.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al eliminar alquiler: ", e);
            return false;
        }
    }

    // Método auxiliar para mapear ResultSet a objeto Alquiler
    private Alquiler mapearAlquiler(ResultSet rs) throws SQLException {
        Alquiler alquiler = new Alquiler();
        alquiler.setId(rs.getInt("id"));
        alquiler.setFechaInicio(LocalDate.parse(rs.getString("fecha_inicio")));
        alquiler.setFechaFin(LocalDate.parse(rs.getString("fecha_fin")));
        alquiler.setDevuelto(rs.getInt("devuelto"));
        
        // Obtener DNI del cliente y crear objeto Cliente básico
        String dniCliente = rs.getString("dni_cliente");
        Cliente cliente = obtenerClienteBasico(dniCliente);
        alquiler.setCliente(cliente);
        
        // Cargar el producto completo usando ProductoDAO
        int idProducto = rs.getInt("id_producto");
        ProductoDTO productoDTO = productoDAO.getProducto(idProducto);
        
        if (productoDTO != null) {
            // Aquí necesitarías convertir ProductoDTO a Producto
            // Como ProductoDAO ya tiene métodos que retornan Producto, usamos getProductos y filtramos
            Producto producto = null;
            for (Producto p : productoDAO.getProductos()) {
                if (p.getId() == idProducto) {
                    producto = p;
                    break;
                }
            }
            alquiler.setProducto(producto);
        }
        
        return alquiler;
    }
    
    // Método auxiliar para crear un Cliente básico desde la BD
    private Cliente obtenerClienteBasico(String dni) {
        String sql = "SELECT u.dni, u.nombre, u.email, u.contrasena, c.amonestaciones " +
                     "FROM Usuario u INNER JOIN Cliente c ON u.dni = c.dni WHERE u.dni = ?";
        
        try (PreparedStatement pstmt = conexionBD.prepareStatement(sql)) {
            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setDni(rs.getString("dni"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setEmail(rs.getString("email"));
                cliente.setContrasena(rs.getString("contrasena"));
                cliente.setAmonestaciones(rs.getInt("amonestaciones"));
                // Historial y reviews se pueden cargar después si es necesario
                cliente.setHistorial(new ArrayList<>());
                cliente.setListaReviews(new ArrayList<>());
                return cliente;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener cliente básico: ", e);
        }
        
        return new Cliente(); // Retorna cliente vacío si hay error
    }

    /**
     * Borrar todos los registros
     */
    public void borrarRegistros() {
        try (Statement stmt = conexionBD.createStatement()) {
            String instruccion = "DELETE FROM Alquiler;";
            stmt.executeUpdate(instruccion);
        } catch (SQLException e) {
            if (logger != null)
                logger.log(Level.SEVERE, "Error al borrar los registros de Alquiler: ", e);
        }
    }
}