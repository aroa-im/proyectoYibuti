package domain;

import java.time.LocalDate;
import java.util.ArrayList;

public class Videojuego extends Producto{
	private GeneroVideoJuego genero;
	private TipoConsola tipo;
	private String autor;
	
	public Videojuego(String titulo, String sinopsis, float precio, int rating, ArrayList<Review> comentarios,
			GeneroVideoJuego genero, TipoConsola tipo, String autor) {
		super(titulo, sinopsis, precio, rating, comentarios);
		this.genero = genero;
		this.tipo = tipo;
		this.autor = autor;
	}
	
	public Videojuego() {
		super();
		this.genero = null;
		this.tipo = null;
		this.autor = "";
	}

	public GeneroVideoJuego getGenero() {
		return genero;
	}

	public void setGenero(GeneroVideoJuego genero) {
		this.genero = genero;
	}

	public TipoConsola getTipo() {
		return tipo;
	}

	public void setTipo(TipoConsola tipo) {
		this.tipo = tipo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	@Override
	public String toString() {
		return "Videojuego [genero=" + genero + ", tipo=" + tipo + ", autor=" + autor + ", Titulo=" + getTitulo()
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
