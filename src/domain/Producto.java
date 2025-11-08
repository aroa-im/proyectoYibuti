package domain;

import java.util.ArrayList;
import java.util.Objects;

import javax.swing.ImageIcon;

public abstract class Producto implements Alquilable{
	private String titulo;
	private String sinopsis;
	private float precio;
	private int rating;
	private ArrayList<Review> comentarios;
	private ImageIcon foto;
	
	public Producto(String titulo, String sinopsis, float precio, int rating, ArrayList<Review> comentarios, ImageIcon foto) {
		super();
		this.titulo = titulo;
		this.sinopsis = sinopsis;
		this.precio = precio;
		this.rating = rating;
		this.comentarios = comentarios;
		this.foto= foto;
	}
	
	public Producto() {
		super();
		this.titulo = "";
		this.sinopsis = "";
		this.precio = 0;
		this.rating = 0;
		this.comentarios = new ArrayList<Review>();
		this.foto= null;
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
	
	public ImageIcon getFoto() {
		return foto;
	}

	public void setFoto(ImageIcon foto) {
		this.foto = foto;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(comentarios, precio, rating, sinopsis, titulo, foto);
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
				&& Objects.equals(sinopsis, other.sinopsis) && Objects.equals(titulo, other.titulo) 
				&& Objects.equals(foto, other.foto);
	}

	@Override
	public String toString() {
		return "Producto [titulo=" + titulo + ", sinopsis=" + sinopsis + ", precio=" + precio + ", rating=" + rating
				+ ", comentarios=" + comentarios + ", foto = " + foto +"]";
	}
	
}
