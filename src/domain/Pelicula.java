package domain;

public class Pelicula {
	private String titulo;
	private String sinopsis;
	private float precio;
	private TipoPelicula tipo;
	private GeneroPelicula genero;
	private String director;
	private Double rating;
	
	public Pelicula(String titulo, String sinopsis, float precio, TipoPelicula tipo, GeneroPelicula genero, String director, Double rating) {
		super();
		this.titulo = titulo;
		this.sinopsis = sinopsis;
		this.precio = precio;
		this.tipo = tipo;
		this.genero = genero;
		this.director= director;
		this.rating=rating;
	}
	
	public Pelicula() {
		super();
		this.titulo = "";
		this.sinopsis = "";
		this.precio = 0;
		this.tipo = TipoPelicula.DVD;
		this.genero = GeneroPelicula.TERROR;
		this.director = "";
		this.rating= 0.0;
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
	
	public String getAutor() {
		return autor;
	}

	public void setDirector(String director) {
		this.director = director;
	}
	
	public Double getRating() {
		return rating;
	}
	public void set(Double rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "Pelicula [titulo=" + titulo + ", sinopsis=" + sinopsis + ", precio=" + precio + ", tipo=" + tipo + ", genero=" + genero + ", director = " + director + ", rating = " + rating +"]";
	}
	
}
