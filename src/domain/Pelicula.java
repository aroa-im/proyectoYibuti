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
	    
		//Con ayuda de IA hicimos este toString() para que no nos diese el stackOverflow en la clase VentanaAñadirReview
	    StringBuilder sb = new StringBuilder();
	    
	   
	    sb.append("Pelicula [tipo=").append(tipo)
	      .append(", genero=").append(genero)
	      .append(", director=").append(director)
	      .append(", Duración=").append(duracion)
	      .append(", Titulo=").append(getTitulo())
	      .append(", Sinopsis=").append(getSinopsis())
	      .append(", Precio=").append(getPrecio())
	      .append(", Rating=").append(getRating());

	    
	    if (getComentarios() != null && !getComentarios().isEmpty()) {
	        sb.append(", Comentarios=").append(getComentarios().size()).append(" reseñas disponibles");
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
