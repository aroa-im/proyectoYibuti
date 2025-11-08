package domain;

public class Videojuego {
	private String titulo;
	private String descripcion;
	private float precio;
	private GeneroPelicula genero;
	private TipoConsola tipo;
	private String autor;
	
	public Videojuego(String titulo, String descripcion, float precio, GeneroPelicula genero, TipoConsola tipo, String autor) {
		super();
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.precio = precio;
		this.genero = genero;
		this.tipo = tipo;
		this.autor = autor;
	}
	
	public Videojuego() {
		super();
		this.titulo = "";
		this.descripcion = "";
		this.precio = 0;
		this.genero = GeneroPelicula.TERROR;
		this.tipo = TipoConsola.PC;
		this.autor = "";
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public GeneroPelicula getGenero() {
		return genero;
	}

	public void setGenero(GeneroPelicula genero) {
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
		return "Videojuego [titulo=" + titulo + ", descripcion=" + descripcion + ", precio=" + precio + ", genero="
				+ genero + ", tipo=" + tipo +", autor =" + autor +"]";
	}
	
}
