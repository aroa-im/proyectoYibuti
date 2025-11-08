package domain;

import java.util.ArrayList;

public class Admin extends Usuario {
	private ArrayList<String> horasDisponibles;
	private ArrayList<String> logAcciones;
	
	public Admin(String dni, String nombre, String email, String contrasena, ArrayList<String> horasDisponibles,
			ArrayList<String> logAcciones) {
		super(dni, nombre, email, contrasena);
		this.horasDisponibles = horasDisponibles;
		this.logAcciones = logAcciones;
	}
	
	public Admin() {
		super();
		this.horasDisponibles = new ArrayList<String>();
		this.logAcciones = new ArrayList<String>();
	}

	public ArrayList<String> getHorasDisponibles() {
		return horasDisponibles;
	}

	public void setHorasDisponibles(ArrayList<String> horasDisponibles) {
		this.horasDisponibles = horasDisponibles;
	}

	public ArrayList<String> getLogAcciones() {
		return logAcciones;
	}

	public void setLogAcciones(ArrayList<String> logAcciones) {
		this.logAcciones = logAcciones;
	}

	@Override
	public String toString() {
		return "Admin [horasDisponibles=" + horasDisponibles + ", logAcciones=" + logAcciones +  ", getDni()=" + getDni()
		+ ", getNombre()=" + getNombre() + ", getEmail()=" + getEmail() + ", getContrasena()=" + getContrasena() + "]";
	}
	
	
}
