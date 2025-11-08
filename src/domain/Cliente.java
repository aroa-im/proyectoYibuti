package domain;

import java.util.ArrayList;

public class Cliente extends Usuario{
	private ArrayList<Producto> historial;
	private int amonestaciones;
	
	public Cliente(String dni, String nombre, String email, String contrasena, ArrayList<Producto> historial,
			int amonestaciones) {
		super(dni, nombre, email, contrasena);
		this.historial = historial;
		this.amonestaciones = amonestaciones;
	}
	
	public Cliente() {
		super();
		this.historial = new ArrayList<Producto>();
		this.amonestaciones = 0;
	}

	public ArrayList<Producto> getHistorial() {
		return historial;
	}

	public void setHistorial(ArrayList<Producto> historial) {
		this.historial = historial;
	}

	public int getAmonestaciones() {
		return amonestaciones;
	}

	public void setAmonestaciones(int amonestaciones) {
		this.amonestaciones = amonestaciones;
	}

	@Override
	public String toString() {
		return "Cliente [historial=" + historial + ", amonestaciones=" + amonestaciones + ", Dni=" + getDni()
		+ ", Nombre=" + getNombre() + ", Email=" + getEmail() + ", Contrasena=" + getContrasena() + "]";
	}
	
}
