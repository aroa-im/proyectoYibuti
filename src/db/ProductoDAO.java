package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import domain.Cliente;
import domain.GeneroPelicula;
import domain.GeneroVideoJuego;
import domain.Pelicula;
import domain.Producto;
import domain.TipoConsola;
import domain.TipoPelicula;
import domain.Videojuego;
import main.main;
import utils.Utils;

public class ProductoDAO implements ProductoDAOInterface {

    private Connection conexionBD;
    private Logger logger;

    public ProductoDAO() {
        this.conexionBD = main.getConexionBD();
        this.logger = main.getLogger();
    }
    
    public ProductoDAO(Connection conexionBD, Logger logger) {
        this.conexionBD = conexionBD;
        this.logger = logger;
    }

    // --- Métodos Auxiliares de Mapeo ---
    
    /**
     * Convierte un Producto de dominio a un ProductoDTO para persistencia.
     */
    private ProductoDTO crearDTODesdeProducto(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        
        dto.setId(producto.getId()); // Se asume que el ID está en el objeto de dominio
        dto.setTitulo(producto.getTitulo());
        dto.setSinopsis(producto.getSinopsis());
        dto.setPrecio(producto.getPrecio());
        dto.setRating(producto.getRating());
        
        // La URL de la foto se guarda como String (ajustar la lógica si la foto es un campo estático)
        // Usamos un placeholder si no hay una URL de foto explícita en Producto
        dto.setUrlFoto(producto instanceof Pelicula ? "peliculas/default.jpg" : "videojuegos/default.png");
        
        if (producto instanceof Pelicula) {
            Pelicula pelicula = (Pelicula) producto;
            dto.setTipoProducto("PELICULA");
            dto.setTipoPelicula(pelicula.getTipo());
            dto.setGeneroPelicula(pelicula.getGenero());
            dto.setDirector(pelicula.getDirector());
            dto.setDuracion(pelicula.getDuracion());
            
        } else if (producto instanceof Videojuego) {
            Videojuego videojuego = (Videojuego) producto;
            dto.setTipoProducto("VIDEOJUEGO");
            dto.setGeneroVideojuego(videojuego.getGenero());
            dto.setTipoConsola(videojuego.getTipo());
            dto.setAutorVideojuego(videojuego.getAutor());
        }
        return dto;
    }
    
    /**
     * Convierte un ProductoDTO de la BD a la clase de dominio correcta (Pelicula o Videojuego).
     */
    private Producto crearProductoDesdeDTO(ProductoDTO dto) {
        // Cargar la imagen (asumo dimensiones de miniatura 115x160 como en VentanaVideojuegos)
        ImageIcon foto = Utils.loadImage(dto.getUrlFoto(), 115, 160);
        
        // Cargar Reviews (se necesita el ReviewDAO)
        // Nota: ReviewDAOInterface no está completa, asumimos un método getReviewsProductoById
        ArrayList<domain.Review> reviews = new ArrayList<>();
        if (main.getReviewDAO() != null) {
             // Esto es una suposición de cómo se llamaría el método si ReviewDAO estuviera completo
             // reviews = main.getReviewDAO().getReviewsProductoById(dto.getId()); 
        }

        if ("PELICULA".equals(dto.getTipoProducto())) {
            return new Pelicula(
                dto.getId(), // ID añadido
                dto.getTitulo(),
                dto.getSinopsis(),
                dto.getPrecio(),
                dto.getRating(),
                reviews, 
                dto.getTipoPelicula(),
                dto.getGeneroPelicula(),
                dto.getDirector(),
                dto.getDuracion(),
                foto
            );
        } else if ("VIDEOJUEGO".equals(dto.getTipoProducto())) {
            return new Videojuego(
                dto.getId(), // ID añadido
                dto.getTitulo(),
                dto.getSinopsis(),
                dto.getPrecio(),
                dto.getRating(),
                reviews, 
                dto.getGeneroVideojuego(),
                dto.getTipoConsola(),
                dto.getAutorVideojuego(),
                foto
            );
        }
        return null;
    }
    
    /**
     * Mapea un ResultSet a un ProductoDTO.
     */
    private ProductoDTO mapearResultSetAProductoDTO(ResultSet rs) throws SQLException {
        ProductoDTO dto = new ProductoDTO();
        
        dto.setId(rs.getLong("id"));
        dto.setTitulo(rs.getString("titulo"));
        dto.setSinopsis(rs.getString("sinopsis"));
        dto.setPrecio(rs.getFloat("precio"));
        dto.setRating(rs.getInt("rating"));
        dto.setUrlFoto(rs.getString("url_foto"));
        dto.setTipoProducto(rs.getString("tipo_producto"));

        // Campos específicos de Pelicula
        String tipoPeliculaStr = rs.getString("tipo_pelicula");
        if (tipoPeliculaStr != null) {
            dto.setTipoPelicula(TipoPelicula.valueOf(tipoPeliculaStr));
        }
        String generoPeliculaStr = rs.getString("genero_pelicula");
        if (generoPeliculaStr != null) {
            dto.setGeneroPelicula(GeneroPelicula.valueOf(generoPeliculaStr));
        }
        dto.setDirector(rs.getString("director"));
        dto.setDuracion(rs.getInt("duracion"));

        // Campos específicos de Videojuego
        String generoVideojuegoStr = rs.getString("genero_videojuego");
        if (generoVideojuegoStr != null) {
            dto.setGeneroVideojuego(GeneroVideoJuego.valueOf(generoVideojuegoStr));
        }
        String tipoConsolaStr = rs.getString("tipo_consola");
        if (tipoConsolaStr != null) {
            dto.setTipoConsola(TipoConsola.valueOf(tipoConsolaStr));
        }
        dto.setAutorVideojuego(rs.getString("autor_videojuego"));

        return dto;
    }

    // --- Implementación de ProductoDAOInterface ---

    @Override
    public boolean addProducto(Producto producto) {
        ProductoDTO dto = crearDTODesdeProducto(producto);

        // Se insertan todos los campos. Los campos no aplicables (ej: Videojuego en Pelicula) serán NULL.
        // Se incluye el ID en la inserción (asumo que se pre-asigna o es manual, similar a LibroDAO)
        String insertSQL = "INSERT INTO Producto (id, titulo, sinopsis, precio, rating, url_foto, tipo_producto, tipo_pelicula, genero_pelicula, director, duracion, genero_videojuego, tipo_consola, autor_videojuego) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStmt = conexionBD.prepareStatement(insertSQL)) {

            preparedStmt.setLong(1, dto.getId()); // Se inserta el ID
            preparedStmt.setString(2, dto.getTitulo());
            preparedStmt.setString(3, dto.getSinopsis());
            preparedStmt.setFloat(4, dto.getPrecio());
            preparedStmt.setInt(5, dto.getRating());
            preparedStmt.setString(6, dto.getUrlFoto());
            preparedStmt.setString(7, dto.getTipoProducto());

            if ("PELICULA".equals(dto.getTipoProducto())) {
                preparedStmt.setString(8, dto.getTipoPelicula() != null ? dto.getTipoPelicula().toString() : null);
                preparedStmt.setString(9, dto.getGeneroPelicula() != null ? dto.getGeneroPelicula().toString() : null);
                preparedStmt.setString(10, dto.getDirector());
                preparedStmt.setInt(11, dto.getDuracion());
                preparedStmt.setObject(12, null); // genero_videojuego
                preparedStmt.setObject(13, null); // tipo_consola
                preparedStmt.setObject(14, null); // autor_videojuego
            } else if ("VIDEOJUEGO".equals(dto.getTipoProducto())) {
                preparedStmt.setObject(8, null); // tipo_pelicula
                preparedStmt.setObject(9, null); // genero_pelicula
                preparedStmt.setObject(10, null); // director
                preparedStmt.setObject(11, null); // duracion
                preparedStmt.setString(12, dto.getGeneroVideojuego() != null ? dto.getGeneroVideojuego().toString() : null);
                preparedStmt.setString(13, dto.getTipoConsola() != null ? dto.getTipoConsola().toString() : null);
                preparedStmt.setString(14, dto.getAutorVideojuego());
            } else {
                 preparedStmt.setObject(8, null); preparedStmt.setObject(9, null); 
                 preparedStmt.setObject(10, null); preparedStmt.setObject(11, null);
                 preparedStmt.setObject(12, null); preparedStmt.setObject(13, null);
                 preparedStmt.setObject(14, null);
            }

            int filas = preparedStmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            if (logger != null)
                logger.log(Level.SEVERE, "Error al añadir el producto: ", e);
            return false;
        }
    }

    @Override
    public ProductoDTO getProducto(long id) {
        String selectSQL = "SELECT * FROM Producto WHERE id = ?";
        try (PreparedStatement preparedStmt = conexionBD.prepareStatement(selectSQL)) {
            preparedStmt.setLong(1, id);

            try (ResultSet rs = preparedStmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetAProductoDTO(rs);
                }
            }
        } catch (SQLException e) {
            if (logger != null)
                logger.log(Level.SEVERE, "Error al obtener el producto con id: " + id, e);
        }
        return null;
    }
    
    @Override
    public ArrayList<Producto> getProductos() {
        ArrayList<Producto> result = new ArrayList<>();
        String selectSQL = "SELECT * FROM Producto";
        
        try (Statement stmt = conexionBD.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            while (rs.next()) {
                ProductoDTO dto = mapearResultSetAProductoDTO(rs);
                Producto producto = crearProductoDesdeDTO(dto);
                if (producto != null) {
                    result.add(producto);
                }
            }
        } catch (SQLException e) {
            if (logger != null)
                logger.log(Level.SEVERE, "Error al obtener todos los productos: ", e);
        }
        return result;
    }

    @Override
    public boolean añadirReserva(long id, int diasDevolucion, Cliente cliente) {
        LocalDate fechaReserva = LocalDate.now();
        LocalDate fechaDevolucion = fechaReserva.plusDays(diasDevolucion);
        
        // Uso de una tabla genérica 'Reserva' o 'ReservaProducto'
        // Nos basamos en la estructura de LibroDAO.añadirReserva (fecha_inicio, fecha_fin, id_producto, dni_cliente)
        String insertSQL = "INSERT INTO Reserva (fecha_inicio, fecha_fin, id_producto, dni_cliente) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement preparedStmt = conexionBD.prepareStatement(insertSQL)) {
            preparedStmt.setString(1, fechaReserva.toString());
            preparedStmt.setString(2, fechaDevolucion.toString());
            preparedStmt.setLong(3, id);
            preparedStmt.setString(4, cliente.getDni());
            
            int filas = preparedStmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            if (logger != null)
                logger.log(Level.SEVERE, "Error al añadir la reserva: ", e);
            return false;
        }
    }

    @Override
    public ArrayList<ProductoDTO> getHistorialByCliente(String dniCliente) {
        ArrayList<ProductoDTO> historialCliente = new ArrayList<>();
        
        // Query adaptada de LibroDAO.getHistorialByCliente
        String insertSQL = "SELECT P.* FROM Reserva R, Producto P WHERE R.id_producto = P.id AND R.dni_cliente = ?;";
        
        try (PreparedStatement preparedStmt = conexionBD.prepareStatement(insertSQL)) {
            preparedStmt.setString(1, dniCliente);
            
            try (ResultSet rs = preparedStmt.executeQuery()) {
                while (rs.next()) {
                    ProductoDTO productoDTO = mapearResultSetAProductoDTO(rs);
                    historialCliente.add(productoDTO);
                }
            }
        } catch (SQLException e) {
            if (logger != null)
                logger.log(Level.SEVERE, "Error al obtener el historial por cliente: ", e);
        }
        return historialCliente;
    }

    @Override
    public boolean productoUsadoByDniCliente(String dniCliente, Long id) {
        // Adaptado de LibroDAO.libroLeidoByDniCliente
        String query = "SELECT count(*) AS veces_usado FROM Reserva WHERE dni_cliente = ? AND id_producto = ?";

        try (PreparedStatement preparedStmt = conexionBD.prepareStatement(query)) {
           
            preparedStmt.setString(1, dniCliente);
            preparedStmt.setLong(2, id);

            try (ResultSet rs = preparedStmt.executeQuery()) {
                return rs.next() && rs.getInt("veces_usado") > 0;
            }
        } catch (SQLException e) {
            if (logger != null) {
                logger.log(Level.SEVERE, "Error al verificar si el producto fue usado por el cliente: ", e);
            }
        }
        return false;
    }

    @Override
    public boolean updateProducto(ProductoDTO producto, long idAntiguo) {
        // Adaptado de LibroDAO.updateLibro
        String updateSQL = "UPDATE Producto SET id = ?, titulo = ?, sinopsis = ?, precio = ?, rating = ?, url_foto = ?, tipo_producto = ?, tipo_pelicula = ?, genero_pelicula = ?, director = ?, duracion = ?, genero_videojuego = ?, tipo_consola = ?, autor_videojuego = ? WHERE id = ?";
        
        try (PreparedStatement preparedStmt = conexionBD.prepareStatement(updateSQL)) {
            
            preparedStmt.setLong(1, producto.getId());
            preparedStmt.setString(2, producto.getTitulo());
            preparedStmt.setString(3, producto.getSinopsis());
            preparedStmt.setFloat(4, producto.getPrecio());
            preparedStmt.setInt(5, producto.getRating());
            preparedStmt.setString(6, producto.getUrlFoto());
            preparedStmt.setString(7, producto.getTipoProducto());

            // Campos específicos
            if ("PELICULA".equals(producto.getTipoProducto())) {
                preparedStmt.setString(8, producto.getTipoPelicula() != null ? producto.getTipoPelicula().toString() : null);
                preparedStmt.setString(9, producto.getGeneroPelicula() != null ? producto.getGeneroPelicula().toString() : null);
                preparedStmt.setString(10, producto.getDirector());
                preparedStmt.setInt(11, producto.getDuracion());
                preparedStmt.setObject(12, null); 
                preparedStmt.setObject(13, null); 
                preparedStmt.setObject(14, null);
            } else if ("VIDEOJUEGO".equals(producto.getTipoProducto())) {
                preparedStmt.setObject(8, null); 
                preparedStmt.setObject(9, null); 
                preparedStmt.setObject(10, null); 
                preparedStmt.setObject(11, null);
                preparedStmt.setString(12, producto.getGeneroVideojuego() != null ? producto.getGeneroVideojuego().toString() : null);
                preparedStmt.setString(13, producto.getTipoConsola() != null ? producto.getTipoConsola().toString() : null);
                preparedStmt.setString(14, producto.getAutorVideojuego());
            } else {
                 preparedStmt.setObject(8, null); preparedStmt.setObject(9, null); 
                 preparedStmt.setObject(10, null); preparedStmt.setObject(11, null);
                 preparedStmt.setObject(12, null); preparedStmt.setObject(13, null);
                 preparedStmt.setObject(14, null);
            }
            
            preparedStmt.setLong(15, idAntiguo);

            int filas = preparedStmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            if (logger != null)
                logger.log(Level.SEVERE, "Error al actualizar el producto: ", e);
            return false;
        }
    }

    @Override
    public boolean deleteProductoById(long idProducto) {
        // Se debe borrar primero en las tablas que referencian a Producto (Reserva, Review)
        String deleteReservaSQL = "DELETE FROM Reserva WHERE id_producto = ?";
        String deleteReviewSQL = "DELETE FROM Review WHERE id_producto = ?";
        String deleteProductoSQL = "DELETE FROM Producto WHERE id = ?";
        
        try {
            // Eliminar dependencias (asumiendo que la tabla de reservas se llama 'Reserva')
            try (PreparedStatement stmtReserva = conexionBD.prepareStatement(deleteReservaSQL)) {
                stmtReserva.setLong(1, idProducto);
                stmtReserva.executeUpdate();
            }
            // Eliminar reviews
            try (PreparedStatement stmtReview = conexionBD.prepareStatement(deleteReviewSQL)) {
                stmtReview.setLong(1, idProducto);
                stmtReview.executeUpdate();
            }

            // Eliminar producto
            try (PreparedStatement stmtProducto = conexionBD.prepareStatement(deleteProductoSQL)) {
                stmtProducto.setLong(1, idProducto);
                int filas = stmtProducto.executeUpdate();
                return filas > 0;
            }
        } catch (SQLException e) {
            if (logger != null)
                logger.log(Level.SEVERE, "Error al eliminar el producto con id: " + idProducto, e);
            return false;
        }
    }

    @Override
    public void borrarRegistros() {
        // Adaptado de LibroDAO.borrarRegistros
        try {
            Statement stmt = conexionBD.createStatement();
            String instruccion = "DELETE FROM Producto;";

            stmt.executeUpdate(instruccion);
            stmt.close();
        } catch (SQLException e) {
            if (logger != null)
                logger.log(Level.SEVERE, "Error al borrar los registros de Producto: ", e);
        }
    }
}