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
	
}
