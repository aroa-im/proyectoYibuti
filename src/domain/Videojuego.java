package domain;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Videojuego extends Producto{
	private GeneroVideoJuego genero;
	private TipoConsola tipo;
	private String autor;
	
	public Videojuego(long id, String titulo, String sinopsis, double precio, int rating, ArrayList<Review> comentarios,
			GeneroVideoJuego genero, TipoConsola tipo, String autor, ImageIcon foto) {
		super(id,titulo, sinopsis, precio, rating, comentarios, foto);
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
		
		StringBuilder sb = new StringBuilder();

	    sb.append("Videojuego [Titulo=").append(getTitulo())
	      .append(", Sinopsis=").append(getSinopsis())
	      .append(", Precio=").append(getPrecio())
	      .append(", Rating=").append(getRating())
	      .append(", Genero=").append(genero)
	      .append(", Tipo de Consola=").append(tipo)
	      .append(", Autor=").append(autor);

	    if (getReviews() != null && !getReviews().isEmpty()) {
	        sb.append(", Comentarios=").append(getReviews().size()).append(" reseñas disponibles");
	    } else {
	        sb.append(", Comentarios=Sin reseñas");
	    }
	    
	    sb.append("]");
	    
	    return sb.toString();
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
