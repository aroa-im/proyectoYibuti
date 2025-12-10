package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import domain.Admin;
import domain.Cliente;
import domain.Producto;
import domain.Review;
import domain.LogAccion;
import domain.Usuario;
import main.main;

public class UsuarioDAO implements UsuarioDAOInterface {

	private Connection conexionBD;
	private Logger logger = null;

	public UsuarioDAO() {
		conexionBD = main.getConexionBD();
		logger = main.getLogger();
	}
	
	public UsuarioDAO(Connection conexionBD, Logger logger) {
		this.conexionBD = conexionBD;
		this.logger = logger;
	}
//	@Override
//	public boolean addUsuario(Usuario usuario) {
//	    PreparedStatement preparedStmt = null;
//	    PreparedStatement preparedStmt2 = null;
//	    
//	    try {
//	        String insertSQL = "INSERT INTO Usuario (dni, nombre, email, contrasena) VALUES (?, ?, ?, ?)";
//	        preparedStmt = conexionBD.prepareStatement(insertSQL);
//	        
//	        preparedStmt.setString(1, usuario.getDni());
//	        preparedStmt.setString(2, usuario.getNombre());
//	        preparedStmt.setString(3, usuario.getEmail());
//	        preparedStmt.setString(4, usuario.getContrasena());
//	        
//	        int filas = preparedStmt.executeUpdate();
//	        
//	        // Si no se insertó en Usuario, fallar inmediatamente
//	        if (filas == 0) {
//	            if (logger != null)
//	                logger.log(Level.WARNING, "No se pudo insertar el usuario en la tabla Usuario");
//	            return false;
//	        }
//	 
//	        int filas2 = 0;
//	        
//	        if (usuario instanceof Cliente) {
//	            
//	            String insertSQL2 = "INSERT INTO Cliente (dni, amonestaciones) VALUES (?, ?)";
//	            preparedStmt2 = conexionBD.prepareStatement(insertSQL2);
//	            
//	            preparedStmt2.setString(1, usuario.getDni());
//	            preparedStmt2.setInt(2, ((Cliente) usuario).getAmonestaciones());
//	            
//	            filas2 = preparedStmt2.executeUpdate();
//	            
//	        } else if (usuario instanceof Admin) {
//	           
//	            String insertSQL2 = "INSERT INTO Admin (dni) VALUES (?)";
//	            preparedStmt2 = conexionBD.prepareStatement(insertSQL2);
//	            
//	            preparedStmt2.setString(1, usuario.getDni());
//	            
//	            filas2 = preparedStmt2.executeUpdate();
//	        } else {// Tipo de usuario desconocido
//	            if (logger != null)
//	                logger.log(Level.WARNING, "Tipo de usuario desconocido: " + usuario.getClass().getName());
//	            return false;
//	        }
//	        
//	        // Verificar que se insertó correctamente en la tabla secundaria
//	        if (filas2 == 0) {
//	            if (logger != null)
//	                logger.log(Level.WARNING, "No se pudo insertar en la tabla Cliente/Admin");
//	            return false;
//	        }
//	        
//	        if (logger != null)
//	            logger.log(Level.INFO, "Usuario añadido correctamente: " + usuario.getDni());
//	        
//	        return true;
//	        
//	    } catch (SQLException e) {
//	        if (logger != null)
//	            logger.log(Level.SEVERE, "Error al añadir el usuario: ", e);
//	        
//	        
//	        try {
//	            String deleteSQL = "DELETE FROM Usuario WHERE dni = ?";
//	            PreparedStatement rollbackStmt = conexionBD.prepareStatement(deleteSQL);
//	            rollbackStmt.setString(1, usuario.getDni());
//	            rollbackStmt.executeUpdate();
//	            rollbackStmt.close();
//	            
//	            if (logger != null)
//	                logger.log(Level.INFO, "Rollback ejecutado: Usuario eliminado tras fallo");
//	                
//	        } catch (SQLException rollbackEx) {
//	            if (logger != null)
//	                logger.log(Level.SEVERE, "Error en rollback: ", rollbackEx);
//	        }
//	        
//	        return false;
//	        
//	    } finally {
//	       
//	        try {
//	            if (preparedStmt != null) preparedStmt.close();
//	            if (preparedStmt2 != null) preparedStmt2.close();
//	        } catch (SQLException e) {
//	            if (logger != null)
//	                logger.log(Level.WARNING, "Error al cerrar PreparedStatements: ", e);
//	        }
//	    }
//	}
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

	        // Insertar en la tabla específica (Cliente o Admin)
	        PreparedStatement preparedStmt2 = null;
	        if (usuario instanceof Cliente) {
	            String insertSQL2 = "INSERT INTO Cliente (dni, amonestaciones) VALUES (?,?)";
	            preparedStmt2 = conexionBD.prepareStatement(insertSQL2);
	            preparedStmt2.setString(1, usuario.getDni());
	            preparedStmt2.setInt(2, ((Cliente) usuario).getAmonestaciones());
	        } else {
	            String insertSQL2 = "INSERT INTO Admin (dni) VALUES (?)";
	            preparedStmt2 = conexionBD.prepareStatement(insertSQL2);
	            preparedStmt2.setString(1, usuario.getDni());
	        }

	        int filas2 = preparedStmt2.executeUpdate();
	        preparedStmt2.close();
	        
	        return (filas > 0 && filas2 > 0);
	        
	    } catch (SQLException e) {
	        if (logger != null)
	            logger.log(Level.SEVERE, "Error al añadir el usuario: ", e);
	        return false;
	    }
	}
	@Override
	public boolean deleteUsuario(String dni) {
		String deleteSQL = "DELETE FROM Usuario WHERE dni = ?";
		try {
			PreparedStatement preparedStmt = conexionBD.prepareStatement(deleteSQL);
			preparedStmt.setString(1, dni);
			
			preparedStmt.executeUpdate();
			preparedStmt.close();
			return true;
			
		} catch (SQLException e) {
			if (logger != null)
				logger.log(Level.SEVERE, "Error al eliminar el usuario: ", e);
			return false;
		}
	}
	
	@Override
	public boolean isUsuarioCorrecto(String dni, String password) { // Se usa para el inicio de sesiones
		UsuarioDTO usuario = null;

		String insertSQL = "SELECT nombre FROM Usuario WHERE dni = ? AND contrasena = ?";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = conexionBD.prepareStatement(insertSQL);
			preparedStmt.setString(1, dni);
			preparedStmt.setString(2, password);

			try (ResultSet rs = preparedStmt.executeQuery()) {

				while (rs.next()) {
					String nombre = rs.getString("nombre");

					usuario = new UsuarioDTO();
					usuario.setDni(dni);
					usuario.setNombre(nombre);
				}
				return (usuario != null) ? true : false;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

		} catch (SQLException e) {
			if (logger != null) {
				logger.log(Level.SEVERE, "Error al recuperar el usuario: ", e);
			}
			return false;
		}
	}

	@Override
	public UsuarioDTO getUsuario(String dni) {
		UsuarioDTO usuario = null;

		String insertSQL = "SELECT nombre, email FROM Usuario WHERE dni = ?";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = conexionBD.prepareStatement(insertSQL);
			preparedStmt.setString(1, dni);

			try (ResultSet rs = preparedStmt.executeQuery()) {

				while (rs.next()) {
					usuario = new UsuarioDTO();
					String nombre = rs.getString("nombre");
					String email = rs.getString("email");

					usuario.setDni(dni);
					usuario.setNombre(nombre);
					usuario.setEmail(email);
				}
			}

		} catch (SQLException e) {
			if (logger != null)
				logger.log(Level.SEVERE, "Error al recuperar el usuario: ", e);
			return usuario;
		}

		if (usuario != null) {
			getDatosAdicionalesUsuario(usuario);
		}
		return usuario;
	}

	@Override
	public ArrayList<Usuario> getUsuarios() {
	    ArrayList<Usuario> result = new ArrayList<>();
	    String selectSQL = 
	        "SELECT Usuario.dni, Usuario.nombre,  Usuario.email, Usuario.contrasena, Cliente.amonestaciones, CASE " +
			"WHEN Admin.dni IS NOT NULL THEN 'ADMIN' " +
			"WHEN Cliente.dni IS NOT NULL THEN 'CLIENTE' " +
			"ELSE 'DESCONOCIDO'  END AS tipo_usuario " +
	        "FROM Usuario " +
	        "LEFT JOIN Cliente ON Usuario.dni = Cliente.dni " +
	        "LEFT JOIN Admin ON Usuario.dni = Admin.dni;";
	    
	    try (PreparedStatement preparedStmt = conexionBD.prepareStatement(selectSQL)) {
	        ResultSet rs = preparedStmt.executeQuery();
	        
	        while (rs.next()) {
	            String dni = rs.getString("dni");
	            String nombre = rs.getString("nombre");
	            String email = rs.getString("email");
	            String contrasena = rs.getString("contrasena");
	            String tipoUsuario = rs.getString("tipo_usuario");
	            
	            if ("ADMIN".equals(tipoUsuario)) {
	                
	                ArrayList<LogAccion> logAcciones = getLogAccionesByAdminDni(dni);
	                
	                Admin admin = new Admin(
	                    dni, nombre, email, contrasena,
	                    new ArrayList<>(), logAcciones
	                );
	                result.add(admin);
	                
	            } else if ("CLIENTE".equals(tipoUsuario)) {
	                
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
	                result.add(cliente);
	            }
	        }
	        
	    } catch (SQLException e) {
	        if (logger != null)
	            logger.log(Level.SEVERE, "Error al recuperar los usuarios: ", e);
	    }
	    
	    return result;
	}

	@Override
	public void getDatosAdicionalesUsuario(UsuarioDTO usuario) {
		String insertSQL = "SELECT COUNT(1) AS contador, amonestaciones FROM Cliente WHERE dni = ?";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = conexionBD.prepareStatement(insertSQL);
			preparedStmt.setString(1, usuario.getDni());

			try (ResultSet rs = preparedStmt.executeQuery()) {
				while (rs.next()) {
					int contador = rs.getInt("contador");
					if (contador > 0) {
						int amonestaciones = rs.getInt("amonestaciones");
						usuario.setAmonestaciones(amonestaciones);
						usuario.setAdmin(false);
					} else {
						usuario.setAdmin(true);
					}
				}
			}

		} catch (SQLException e) {
			if (logger != null)
				logger.log(Level.SEVERE, "Error al obtener datos adicionales del usuario: ", e);
			return;
		}
	}
	
	@Override
	public boolean updateUsuario(Usuario usuario) {
		try {
			String insertSQL = "UPDATE Usuario SET nombre = ?, email = ? WHERE dni = ?";
			PreparedStatement preparedStmt = conexionBD.prepareStatement(insertSQL);
			preparedStmt.setString(1, usuario.getNombre());
			preparedStmt.setString(2, usuario.getEmail());
			preparedStmt.setString(3, usuario.getDni());

			int filas = preparedStmt.executeUpdate();

			return (filas > 0) ? true : false;
		} catch (SQLException e) {
			if (logger != null)
				logger.log(Level.SEVERE, "Error al actualizar los datos del usuario: ", e);
			return false;
		}
	}

	@Override
	public boolean updatePassword(UsuarioDTO usuario, String password) {
		try {
			String insertSQL = "UPDATE Usuario SET contrasena = ? WHERE dni = ?";
			PreparedStatement preparedStmt = conexionBD.prepareStatement(insertSQL);
			preparedStmt.setString(1, password);
			preparedStmt.setString(2, usuario.getDni());

			int filas = preparedStmt.executeUpdate();

			return (filas > 0) ? true : false;
		} catch (SQLException e) {
			if (logger != null)
				logger.log(Level.SEVERE, "Error al cambiar la contraseña: ", e);
			return false;
		}
	}

	@Override
	public ArrayList<LogAccion> getLogAccionesByAdminDni(String dniAdmin) {
		ArrayList<LogAccion> logAcciones = null;
		String insertSQL = "SELECT * FROM LogAccion WHERE dni_admin = ?";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = conexionBD.prepareStatement(insertSQL);
			preparedStmt.setString(1, dniAdmin);

			try (ResultSet rs = preparedStmt.executeQuery()) {
				logAcciones = new ArrayList<LogAccion>();

				while (rs.next()) {
					int id = rs.getInt("id");
					LocalDateTime fecha = LocalDateTime.parse(rs.getString("fecha"));
					String descripcion = rs.getString("descripcion");

					LogAccion logAccion = new LogAccion(id, fecha, descripcion, dniAdmin);
					logAcciones.add(logAccion);
				}
			}

		} catch (SQLException e) {
			if (logger != null)
				logger.log(Level.SEVERE, "Error al obtener LogAcciones del admin: ", e);
		}

		return logAcciones;
	}

	@Override
	public boolean addLogAccion(LogAccion logAccion) {
		try {
			String insertSQL = "INSERT INTO LogAccion VALUES(NULL, ?, ?, ?)";
			PreparedStatement preparedStmt = conexionBD.prepareStatement(insertSQL);
			preparedStmt.setString(1, logAccion.getFecha().toString());
			preparedStmt.setString(2, logAccion.getDescripcion());
			preparedStmt.setString(3, logAccion.getDniAdmin());

			int filas = preparedStmt.executeUpdate();

			preparedStmt.close();
			return (filas > 0) ? true : false;
		} catch (SQLException e) {
			if (logger != null)
				logger.log(Level.SEVERE, "Error al añadir el usuario: ", e);
			return false;
		}
	}

	@Override
	public void borrarRegistros() {
		try {
			Statement stmt = conexionBD.createStatement();
			String instruccion = ("DELETE FROM Usuario;"
					+ "DELETE FROM Cliente;"
					+ "DELETE FROM Admin;"
					+ "DELETE FROM LogAccion;");

			stmt.executeUpdate(instruccion);
			stmt.close();
		} catch (SQLException e) {
			if (logger != null)
				logger.log(Level.SEVERE, "Error al borrar los registros: ", e);
		}
	}

	@Override
	public boolean updateAmonestaciones(UsuarioDTO usuario, int nuevasAmonestaciones) {
		String updateSQL = "UPDATE Cliente SET amonestaciones = ? WHERE dni = ?";
		try {
			PreparedStatement preparedStmt = conexionBD.prepareStatement(updateSQL);
			preparedStmt.setInt(1, nuevasAmonestaciones);
			preparedStmt.setString(2, usuario.getDni());
			preparedStmt.executeUpdate();
			preparedStmt.close();
			return true;
		} catch (SQLException e) {
			if (logger != null)
				logger.log(Level.SEVERE, "Error al añadir la amonestación: ", e);
			return false;
		}
	}
}