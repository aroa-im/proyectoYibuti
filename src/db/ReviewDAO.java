package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import domain.Cliente;
import domain.Producto;
import domain.Review;
import main.main;

public class ReviewDAO implements ReviewDAOInterface { 
	// Nota: Las Reviews no necesitan DTOs ya que todos los atributos de Review son visibles en la aplicación

	private Connection conexionBD;
	private Logger logger;
	
	public ReviewDAO() {
		this.conexionBD = main.getConexionBD();
		this.logger = main.getLogger();
	}
	
	public ReviewDAO(Connection conexionBD, Logger logger) {
		this.conexionBD = conexionBD;
		this.logger = logger;
	}

	@Override
	public boolean addReview(Review review) {
		try {
            String insertSQL = "INSERT INTO Review(comentario, rating, id_producto, dni_cliente,fecha_creacion) VALUES (?, ?, ?, ?,?)";
            PreparedStatement preparedStmt = conexionBD.prepareStatement(insertSQL);
            preparedStmt.setString(1, review.getComentario());
            preparedStmt.setInt(2, review.getRating());
            preparedStmt.setLong(3, review.getProducto().getId());
            preparedStmt.setString(4, review.getCliente().getDni());
            preparedStmt.setString(5, java.time.LocalDateTime.now().toString());
            
           int filas= preparedStmt.executeUpdate();

            preparedStmt.close();
            return filas > 0;
        } catch (SQLException e) {
            if (logger != null)
                logger.log(Level.SEVERE, "Error al añadir el usuario: ", e);
            return false;
        }
	}
	@Override
	public ArrayList<Review> getReviewsByUsuarioDni(String dniCliente) {
		ArrayList<Review> result = null;
		
		String selectSQL = "SELECT * FROM Review WHERE dni_cliente = ?";
        PreparedStatement preparedStmt;
		try {
			preparedStmt = conexionBD.prepareStatement(selectSQL);
			preparedStmt.setString(1, dniCliente);

	        try (ResultSet rs = preparedStmt.executeQuery()) {
                
	        	result = new ArrayList<>();
                while (rs.next()) {
                   Review review = new Review();
                   
                   review.setId(rs.getLong("id"));
                   review.setRating(rs.getInt("rating"));
                   review.setComentario(rs.getString("comentario"));
                   
                   // Asociar producto 
                   long idProducto = rs.getLong("id_producto");
                   try {
                       if (main.getProductoDAO() != null) {
                           for (Producto p : main.getProductoDAO().getProductos()) {
                               if (p != null && p.getId() == idProducto) {
                                   review.setProducto(p);
                                   break;
                               }
                           }
                       }
                   } catch (Exception e) {
                       
                       if (logger != null) logger.log(Level.WARNING, "No se pudo asociar producto a review: " + idProducto, e);
                   }

                   // Asociar cliente
                   Cliente cliente = new Cliente();
                   cliente.setDni(dniCliente);
                   review.setCliente(cliente);
                   
                   result.add(review);
                }
                
    			preparedStmt.close();
            }
	        	        
		} catch (SQLException e) {
			if (logger != null) {
				logger.log(Level.SEVERE, "Error al recuperar reviews por usuario: ", e);
				return result;
			}
		} 
		
		return result;	
	}

	@Override
	public ArrayList<Review> getReviewsProductoById(Long idproducto) {
		ArrayList<Review> result = null;
		
		String selectSQL = "SELECT * FROM Review WHERE id_producto = ?";
        PreparedStatement preparedStmt;
		try {
			preparedStmt = conexionBD.prepareStatement(selectSQL);
			preparedStmt.setLong(1, idproducto);

	        try (ResultSet rs = preparedStmt.executeQuery()) {
                
	        	result = new ArrayList<>();
                while (rs.next()) {
                   Review review = new Review();
                  
                   review.setId(rs.getLong("id"));
                   review.setRating(rs.getInt("rating"));
                   review.setComentario(rs.getString("comentario"));
                   
                   // Asociar producto 
                   try {
                       if (main.getProductoDAO() != null) {
                           for (Producto p : main.getProductoDAO().getProductos()) {
                               if (p != null && p.getId() == idproducto) {
                                   review.setProducto(p);
                                   break;
                               }
                           }
                       }
                   } catch (Exception e) {
                       if (logger != null) logger.log(Level.WARNING, "No se pudo asociar producto a review (byProduct): " + idproducto, e);
                   }

                   // Asociar cliente 
                   String dni = rs.getString("dni_cliente");
                   Cliente cliente = new Cliente();
                   if (dni != null) cliente.setDni(dni);
                   review.setCliente(cliente);
                   
                   result.add(review);
                }
                
    			preparedStmt.close();
            }
	        	        
		} catch (SQLException e) {
			if (logger != null) {
				logger.log(Level.SEVERE, "Error al recuperar reviews por producto: ", e);
				return result;
			}
		} 
		
		return result;	
	}
	
	@Override
	public void borrarRegistros() {
		try {
			Statement stmt = conexionBD.createStatement();
			String instruccion = "DELETE FROM Review;";
			
			stmt.executeUpdate(instruccion);
			stmt.close();
		} catch (SQLException e) {
			if (logger != null)
                logger.log(Level.SEVERE, "Error al borrar los registros: ", e);
		}
	}


	@Override
	public long generarIdUnico() {
	    long nuevoId = 1;
	    String sql = "SELECT MAX(id) AS max_id FROM Review";
	    try (Statement stmt = conexionBD.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {
	        if (rs.next()) {
	            long maxId = rs.getLong("max_id");
	            if (!rs.wasNull()) {
	                nuevoId = maxId + 1;
	            }
	        }
	    } catch (SQLException e) {
	        if (logger != null)
	            logger.log(Level.SEVERE, "Error al generar id único: ", e);
	    }
	    return nuevoId;
	}
}




