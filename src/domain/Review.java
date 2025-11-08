package domain;

import java.util.Objects;

public class Review {
	private Pelicula pelicula;
	private Videojuego videojuego;
	private Cliente cliente;
	private String comentario;
	private int rating;
	
	public Review(Pelicula pelicula, Videojuego videojuego, Cliente cliente, String comentario, int rating) {
		super();
		this.pelicula = pelicula;
		this.videojuego = videojuego;
		this.cliente = cliente;
		this.comentario = comentario;
		this.rating = rating;
	}
	
	public Review() {
		super();
		this.pelicula = new Pelicula();
		this.videojuego = new Videojuego();
		this.cliente = new Cliente();
		this.comentario = "";
		this.rating = 0;
	}
	
	public Pelicula getPelicula() {
		return pelicula;
	}
	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}
	public Videojuego getVideojuego() {
		return videojuego;
	}
	public void setVideojuego(Videojuego videojuego) {
		this.videojuego = videojuego;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Review other = (Review) obj;
		return Objects.equals(cliente, other.cliente) && Objects.equals(comentario, other.comentario)
				&& Objects.equals(pelicula, other.pelicula) && rating == other.rating
				&& Objects.equals(videojuego, other.videojuego);
	}

	@Override
	public String toString() {
		return "Review [pelicula=" + pelicula + ", videojuego=" + videojuego + ", cliente=" + cliente + ", comentario="
				+ comentario + ", rating=" + rating + "]";
	}
	
}
