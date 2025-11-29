package db;

import java.util.ArrayList;

import domain.LogAccion;
import domain.Usuario;

public interface UsuarioDAOInterface {
	boolean addUsuario(Usuario usuario);
	boolean deleteUsuario(String dni);
	boolean isUsuarioCorrecto(String dni, String password);
	UsuarioDTO getUsuario(String dni);
	ArrayList<Usuario> getUsuarios();
	boolean updateUsuario(Usuario usuario);
	boolean updatePassword(UsuarioDTO usuario, String password);
	boolean addLogAccion(LogAccion logAccion);	
	ArrayList<LogAccion> getLogAccionesByAdminDni(String dniAdmin);
	void borrarRegistros();
	void getDatosAdicionalesUsuario(UsuarioDTO usuario);
	boolean updateAmonestaciones(UsuarioDTO usuario, int nuevasAmonestaciones);
}
