package domain;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Producto implements Alquilable{
	private String titulo;
	private String sinopsis;
	private float precio;
	private int rating;
	private ArrayList<Review> comentarios;
	
	public Producto(String titulo, String sinopsis, float precio, int rating, ArrayList<Review> comentarios) {
		super();
		this.titulo = titulo;
		this.sinopsis = sinopsis;
		this.precio = precio;
		this.rating = rating;
		this.comentarios = comentarios;
	}
	
	public Producto() {
		super();
		this.titulo = "";
		this.sinopsis = "";
		this.precio = 0;
		this.rating = 0;
		this.comentarios = new ArrayList<Review>();
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getSinopsis() {
		return sinopsis;
	}

	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public ArrayList<Review> getComentarios() {
		return comentarios;
	}

	public void setComentarios(ArrayList<Review> comentarios) {
		this.comentarios = comentarios;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(comentarios, precio, rating, sinopsis, titulo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		return Objects.equals(comentarios, other.comentarios)
				&& Float.floatToIntBits(precio) == Float.floatToIntBits(other.precio) && rating == other.rating
				&& Objects.equals(sinopsis, other.sinopsis) && Objects.equals(titulo, other.titulo);
	}

	@Override
	public String toString() {
		return "Producto [titulo=" + titulo + ", sinopsis=" + sinopsis + ", precio=" + precio + ", rating=" + rating
				+ ", comentarios=" + comentarios + "]";
	}
	
}
