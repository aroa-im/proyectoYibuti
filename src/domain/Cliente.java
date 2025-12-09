package domain;

import java.util.ArrayList;

import db.UsuarioDTO;
import main.main;

public class Cliente extends Usuario{
	private ArrayList<Producto> historial;
	private ArrayList<Review> listaReviews;
	private int amonestaciones;
	
	public Cliente(String dni, String nombre, String email, String contrasena, ArrayList<Producto> historial,ArrayList<Review> listaReviews,
			int amonestaciones) {
		super(dni, nombre, email, contrasena);
		this.historial = historial;
		this.amonestaciones = amonestaciones;
	}
	
	public Cliente() {
		super();
		this.historial = new ArrayList<Producto>();
		this.listaReviews = new ArrayList<Review>();
		this.amonestaciones = 0;
	}
	public Cliente(UsuarioDTO usuarioDTO) {
		super(usuarioDTO);
		this.historial = new ArrayList<>();
		this.listaReviews = main.getReviewDAO().getReviewsByUsuarioDni(usuarioDTO.getDni());
		this.amonestaciones = usuarioDTO.getAmonestaciones();
	}
	public ArrayList<Producto> getHistorial() {
		return historial;
	}

	public void setHistorial(ArrayList<Producto> historial) {
		this.historial = historial;
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
		return "Cliente [historial=" + historial + ", listaReviews=" + listaReviews + ", amonestaciones="
				+ amonestaciones + "]";
	}

	
	
}

