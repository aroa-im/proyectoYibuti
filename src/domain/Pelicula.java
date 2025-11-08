package domain;

public class Pelicula {
	private String titulo;
	private String sinopsis;
	private float precio;
	private TipoPelicula tipo;
	private Genero genero;
	
	public Pelicula(String titulo, String sinopsis, float precio, TipoPelicula tipo, Genero genero) {
		super();
		this.titulo = titulo;
		this.sinopsis = sinopsis;
		this.precio = precio;
		this.tipo = tipo;
		this.genero = genero;
	}
	
	public Pelicula() {
		super();
		this.titulo = "";
		this.sinopsis = "";
		this.precio = 0;
		this.tipo = TipoPelicula.DVD;
		this.genero = Genero.TERROR;
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
	
	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	@Override
	public String toString() {
		return "Pelicula [titulo=" + titulo + ", sinopsis=" + sinopsis + ", precio=" + precio + ", tipo=" + tipo + ", genero=" + genero + "]";
	}
	
}
