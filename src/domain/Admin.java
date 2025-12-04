package domain;

import java.util.ArrayList;

import db.UsuarioDTO;
import main.main;

public class Admin extends Usuario {
	private ArrayList<String> horasDisponibles;
	private ArrayList<LogAccion> logAcciones;
	
	public Admin(String dni, String nombre, String email, String contrasena, ArrayList<String> horasDisponibles,
			ArrayList<LogAccion> logAcciones) {
		super(dni, nombre, email, contrasena);
		this.horasDisponibles = horasDisponibles;
		this.logAcciones = logAcciones;
	}
	
	public Admin() {
		super();
		this.horasDisponibles = new ArrayList<String>();
		this.logAcciones = new ArrayList<LogAccion>();
	}

	public ArrayList<String> getHorasDisponibles() {
		return horasDisponibles;
	}

	public void setHorasDisponibles(ArrayList<String> horasDisponibles) {
		this.horasDisponibles = horasDisponibles;
	}
	
	public Admin(UsuarioDTO usuarioDTO) {
		super(usuarioDTO);
		this.logAcciones = main.getUsuarioDAO().getLogAccionesByAdminDni(usuarioDTO.getDni());
	}

	public ArrayList<LogAccion> getLogAcciones() {
		return logAcciones;
	}

	public void setLogAcciones(ArrayList<LogAccion> logAcciones) {
		this.logAcciones = logAcciones;
	}

	@Override
	public String toString() {
		return "Admin [horasDisponibles=" + horasDisponibles + ", logAcciones=" + logAcciones +  ", Dni=" + getDni()
		+ ", Nombre=" + getNombre() + ", Email=" + getEmail() + ", Contrasena=" + getContrasena() + "]";
	}
	
}
