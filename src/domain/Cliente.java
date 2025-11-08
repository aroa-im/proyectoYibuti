package domain;

import java.util.ArrayList;

public class Cliente extends Usuario{
	
//	private ArrayList<Pelicula> historialPeliculas;
//	private ArrayList<Videojuego> historialVideojuegos;
	private ArrayList<Review> listaReviews;
	private int amonestaciones;
	
	public Cliente(String dni, String nombre, String email, String contrasena, ArrayList<Review> listaReviews,
			int amonestaciones) {
		super(dni, nombre, email, contrasena);
		this.listaReviews = listaReviews;
		this.amonestaciones = amonestaciones;
	}
	
	public Cliente() {
		super();
		this.listaReviews = new ArrayList<Review>();
		this.amonestaciones = 0;
	}

	public ArrayList<Review> getListaReviews() {
		return listaReviews;
	}

	public void setListaReviews(ArrayList<Review> listaReviews) {
		this.listaReviews = listaReviews;
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
