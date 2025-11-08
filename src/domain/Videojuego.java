package domain;

public class Videojuego {
	private String titulo;
	private String descripcion;
	private float precio;
	private Genero genero;
	
	public Videojuego(String titulo, String descripcion, float precio, Genero genero) {
		super();
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.precio = precio;
		this.genero = genero;
	}
	
	public Videojuego() {
		super();
		this.titulo = "";
		this.descripcion = "";
		this.precio = 0;
		this.genero = Genero.TERROR;
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

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	@Override
	public String toString() {
		return "Videojuego [titulo=" + titulo + ", descripcion=" + descripcion + ", precio=" + precio + ", genero="
				+ genero + "]";
	}
	
}
