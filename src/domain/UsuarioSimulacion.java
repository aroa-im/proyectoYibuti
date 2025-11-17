package domain;

import java.util.ArrayList;
import java.util.HashMap;

public class UsuarioSimulacion {
	private static HashMap<String, Usuario> usuarios = new HashMap<>();
	static {
        // Usuario Admin fijo
        Admin admin = new Admin("a", "admin", "admin", "admin", 
                               new ArrayList<>(), new ArrayList<>());
        usuarios.put("a", admin);
        
        // Usuario Cliente fijo
        Cliente cliente = new Cliente("c", "cliente", "cliente", "cliente", 
                                     new ArrayList<>(), 0);
        usuarios.put("c", cliente);
    }
	 public static Usuario validarUsuario(String dni, String contrasena) {
	        Usuario usuario = usuarios.get(dni);
	        if (usuario != null && usuario.getContrasena().equals(contrasena)) {
	            return usuario;
	        }
	        return null;
	 }
	 public static boolean registrarUsuario(String dni, String nombre, String email, String contrasena) {
	        if (usuarios.containsKey(dni)) {
	            return false; // DNI ya existe
	        }
	        Cliente nuevoCliente = new Cliente(dni, nombre, email, contrasena, new ArrayList<>(), 0);
	        usuarios.put(dni, nuevoCliente);
	        return true;
	 }
	 
	 public static boolean existeDNI(String dni) {
	        return usuarios.containsKey(dni);
	 }
	 
	 public static ArrayList<Usuario> obtenerTodosLosUsuarios() {
		    return new ArrayList<>(usuarios.values());
	 }
	 
	 public static boolean actualizarNombre(String dni, String nuevoNombre) {
		    Usuario usuario = usuarios.get(dni);
		    if (usuario != null) {
		        usuario.setNombre(nuevoNombre);
		        return true;
		    }
		    return false;
	}
	 public static boolean actualizarEmail(String dni, String nuevoEmail) {
		    Usuario usuario = usuarios.get(dni);
		    if (usuario != null) {
		        usuario.setEmail(nuevoEmail);
		        return true;
		    }
		    return false;
		}
	
	 public static boolean actualizarContrasena(String dni, String nuevaContrasena) {
		    Usuario usuario = usuarios.get(dni);
		    if (usuario != null) {
		        usuario.setContrasena(nuevaContrasena);
		        return true;
		    }
		    return false;
		}
	 public static boolean cambiarAdminACliente(String dni) {
		    Usuario usuario = usuarios.get(dni);
		    if (usuario != null && usuario instanceof Admin) {
		        // Crear nuevo cliente con los datos del admin
		        Cliente nuevoCliente = new Cliente(
		            usuario.getDni(),
		            usuario.getNombre(),
		            usuario.getEmail(),
		            usuario.getContrasena(),
		            new ArrayList<>(),
		            0
		        );
		        
		        // Reemplazar admin por cliente
		        usuarios.put(dni, nuevoCliente);
		        return true;
		    }
		    return false;
		}
	 public static boolean cambiarClienteAAdmin(String dni) {
		    Usuario usuario = usuarios.get(dni);
		    if (usuario != null && usuario instanceof Cliente) {
		        // Crear nuevo admin con los datos del cliente
		        Admin nuevoAdmin = new Admin(
		            usuario.getDni(),
		            usuario.getNombre(),
		            usuario.getEmail(),
		            usuario.getContrasena(),
		            new ArrayList<>(),
		            new ArrayList<>()
		        );
		        
		        // Reemplazar cliente por admin
		        usuarios.put(dni, nuevoAdmin);
		        return true;
		    }
		    return false;
		}
	 public static boolean eliminarUsuario(String dni) {
		    if (usuarios.containsKey(dni)) {
		        usuarios.remove(dni);
		        return true;
		    }
		    return false;
		}
}
