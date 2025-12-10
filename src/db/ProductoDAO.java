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
    
   
    private ProductoDTO crearDTODesdeProducto(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        
        dto.setId(producto.getId()); 
        dto.setTitulo(producto.getTitulo());
        dto.setSinopsis(producto.getSinopsis());
        dto.setPrecio(producto.getPrecio());
        dto.setRating(producto.getRating());
        
        if (producto instanceof Pelicula) {
            dto.setTipoProducto("PELICULA");
            dto.setUrlFoto("peliculas/" + producto.getId() + ".jpg");
            
            Pelicula pelicula = (Pelicula) producto;
            dto.setTipoPelicula(pelicula.getTipo());
            dto.setGeneroPelicula(pelicula.getGenero());
            dto.setDirector(pelicula.getDirector());
            dto.setDuracion(pelicula.getDuracion());
            
        } else if (producto instanceof Videojuego) {
            dto.setTipoProducto("VIDEOJUEGO");
            dto.setUrlFoto("videojuegos/" + producto.getId() + ".jpg");
            
            Videojuego videojuego = (Videojuego) producto;
            dto.setGeneroVideojuego(videojuego.getGenero());
            dto.setTipoConsola(videojuego.getTipo());
            dto.setAutorVideojuego(videojuego.getAutor());
        }
        return dto;
    }
    

    private Producto crearProductoDesdeDTO(ProductoDTO dto) {
        // Cambiar de 115x160 a 98x151 para que coincida con inputUtils
        ImageIcon foto = Utils.loadImage(dto.getUrlFoto(), 98, 151);
        
        ArrayList<domain.Review> reviews = new ArrayList<>();
        if (main.getReviewDAO() != null) {
            // cargar reviews si es necesario
        }

        if ("PELICULA".equals(dto.getTipoProducto())) {
            return new Pelicula(
                dto.getId(), 
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
                dto.getId(),
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
    
 
    private ProductoDTO mapearResultSetAProductoDTO(ResultSet rs) throws SQLException {
        ProductoDTO dto = new ProductoDTO();
        
        dto.setId(rs.getLong("id"));
        dto.setTitulo(rs.getString("titulo"));
        dto.setSinopsis(rs.getString("sinopsis"));
        dto.setPrecio(rs.getDouble("precio"));
        dto.setRating(rs.getInt("rating"));
        dto.setUrlFoto(rs.getString("url_foto"));
        dto.setTipoProducto(rs.getString("tipo_producto"));

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

    @Override
    public boolean addProducto(Producto producto) {
        ProductoDTO dto = crearDTODesdeProducto(producto);

        String insertSQL = "INSERT INTO Producto (id, titulo, sinopsis, precio, rating, url_foto, tipo_producto, tipo_pelicula, genero_pelicula, director, duracion, genero_videojuego, tipo_consola, autor_videojuego) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStmt = conexionBD.prepareStatement(insertSQL)) {

            preparedStmt.setLong(1, dto.getId()); 
            preparedStmt.setString(2, dto.getTitulo());
            preparedStmt.setString(3, dto.getSinopsis());
            preparedStmt.setDouble(4, dto.getPrecio());
            preparedStmt.setInt(5, dto.getRating());
            preparedStmt.setString(6, dto.getUrlFoto());
            preparedStmt.setString(7, dto.getTipoProducto());

            if ("PELICULA".equals(dto.getTipoProducto())) {
                preparedStmt.setString(8, dto.getTipoPelicula() != null ? dto.getTipoPelicula().toString() : null);
                preparedStmt.setString(9, dto.getGeneroPelicula() != null ? dto.getGeneroPelicula().toString() : null);
                preparedStmt.setString(10, dto.getDirector());
                preparedStmt.setInt(11, dto.getDuracion());
                preparedStmt.setObject(12, null); 
                preparedStmt.setObject(13, null); 
                preparedStmt.setObject(14, null); 
            } else if ("VIDEOJUEGO".equals(dto.getTipoProducto())) {
                preparedStmt.setObject(8, null); 
                preparedStmt.setObject(9, null); 
                preparedStmt.setObject(10, null);
                preparedStmt.setObject(11, null);
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

        String selectSQL = "SELECT P.* FROM Producto P " +
                          "INNER JOIN Alquiler A ON P.id = A.id_producto " +
                          "WHERE A.dni_cliente = ?";
        
        try (PreparedStatement preparedStmt = conexionBD.prepareStatement(selectSQL)) {
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

        String updateSQL = "UPDATE Producto SET id = ?, titulo = ?, sinopsis = ?, precio = ?, rating = ?, url_foto = ?, tipo_producto = ?, tipo_pelicula = ?, genero_pelicula = ?, director = ?, duracion = ?, genero_videojuego = ?, tipo_consola = ?, autor_videojuego = ? WHERE id = ?";
        
        try (PreparedStatement preparedStmt = conexionBD.prepareStatement(updateSQL)) {
            
            preparedStmt.setLong(1, producto.getId());
            preparedStmt.setString(2, producto.getTitulo());
            preparedStmt.setString(3, producto.getSinopsis());
            preparedStmt.setDouble(4, producto.getPrecio()); 
            preparedStmt.setInt(5, producto.getRating());
            preparedStmt.setString(6, producto.getUrlFoto());
            preparedStmt.setString(7, producto.getTipoProducto());

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

        String deleteReservaSQL = "DELETE FROM Reserva WHERE id_producto = ?";
        String deleteReviewSQL = "DELETE FROM Review WHERE id_producto = ?";
        String deleteProductoSQL = "DELETE FROM Producto WHERE id = ?";
        
        try {

            try (PreparedStatement stmtReserva = conexionBD.prepareStatement(deleteReservaSQL)) {
                stmtReserva.setLong(1, idProducto);
                stmtReserva.executeUpdate();
            }

            try (PreparedStatement stmtReview = conexionBD.prepareStatement(deleteReviewSQL)) {
                stmtReview.setLong(1, idProducto);
                stmtReview.executeUpdate();
            }


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
    
    @Override
    public Producto getProductoByTitulo(String titulo) {
        String selectSQL = "SELECT * FROM Producto WHERE titulo = ?";
        
        try (PreparedStatement preparedStmt = conexionBD.prepareStatement(selectSQL)) {
            preparedStmt.setString(1, titulo);
            
            try (ResultSet rs = preparedStmt.executeQuery()) {
                if (rs.next()) {
                    ProductoDTO dto = mapearResultSetAProductoDTO(rs);
                    return crearProductoDesdeDTO(dto);
                }
            }
        } catch (SQLException e) {
            if (logger != null)
                logger.log(Level.SEVERE, "Error al obtener el producto con título: " + titulo, e);
        }
        return null;
    }

    @Override
    public ArrayList<Pelicula> getPeliculas() {
        ArrayList<Pelicula> peliculas = new ArrayList<>();
        String selectSQL = "SELECT * FROM Producto WHERE tipo_producto = 'PELICULA'";
        
        try (Statement stmt = conexionBD.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            while (rs.next()) {
                ProductoDTO dto = mapearResultSetAProductoDTO(rs);
                Producto producto = crearProductoDesdeDTO(dto);
                
                if (producto instanceof Pelicula) {
                    peliculas.add((Pelicula) producto);
                }
            }
        } catch (SQLException e) {
            if (logger != null)
                logger.log(Level.SEVERE, "Error al obtener todas las películas: ", e);
        }
        return peliculas;
    }

    @Override
    public ArrayList<Videojuego> getVideojuegos() {
        ArrayList<Videojuego> videojuegos = new ArrayList<>();
        String selectSQL = "SELECT * FROM Producto WHERE tipo_producto = 'VIDEOJUEGO'";
        
        try (Statement stmt = conexionBD.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            while (rs.next()) {
                ProductoDTO dto = mapearResultSetAProductoDTO(rs);
                Producto producto = crearProductoDesdeDTO(dto);
                
                if (producto instanceof Videojuego) {
                    videojuegos.add((Videojuego) producto);
                }
            }
        } catch (SQLException e) {
            if (logger != null)
                logger.log(Level.SEVERE, "Error al obtener todos los videojuegos: ", e);
        }
        return videojuegos;
    }
}