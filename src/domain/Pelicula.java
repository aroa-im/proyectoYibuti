package domain;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Pelicula extends Producto{
	private TipoPelicula tipo;
	private GeneroPelicula genero;
	private String director;
	private int duracion;
	
	public Pelicula(String titulo, String sinopsis, float precio, int rating, ArrayList<Review> comentarios,
			TipoPelicula tipo, GeneroPelicula genero, String director, int duracion, ImageIcon foto) {
		super(titulo, sinopsis, precio, rating, comentarios, foto);
		this.tipo = tipo;
		this.genero = genero;
		this.director = director;
		this.duracion = duracion;
	}
	
	public Pelicula() {
		super();
		this.tipo = null;
		this.genero = null;
		this.director = "";
		this.duracion = 0;
	}

	public TipoPelicula getTipo() {
		return tipo;
	}

	public void setTipo(TipoPelicula tipo) {
		this.tipo = tipo;
	}

	public GeneroPelicula getGenero() {
		return genero;
	}

	public void setGenero(GeneroPelicula genero) {
		this.genero = genero;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}
	
	public int getDuracion() {
		return duracion;
	}
	
	public void setDuracion(int duracion) {
		this.duracion= duracion;
	}
	
	

	@Override
	public String toString() {
		return "Pelicula [tipo=" + tipo + ", genero=" + genero + ", director=" + director + ", Duración=" + duracion + ", Titulo=" + getTitulo()
		+ ", Sinopsis=" + getSinopsis() + ", Precio=" + getPrecio() + ", Rating=" + getRating()
		+ ", Comentarios=" + getComentarios() + "]";
	}

	@Override
	public boolean crearAlquiler(Cliente cliente, Producto producto, LocalDate fechaDevolucion) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean avisoAlquilerTarde(Cliente cliente, Producto producto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean terminarAlquiler(Cliente cliente, LocalDate fechaDevolucion) {
		// TODO Auto-generated method stub
		return false;
	}

}
