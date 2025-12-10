package domain;

import java.util.ArrayList;
import java.util.Objects;

import javax.swing.ImageIcon;

import db.ProductoDTO;
import main.main;

public abstract class Producto{
	private long id;
	private String titulo;
	private String sinopsis;
	private double precio;
	private int rating;
	
	//Campos no relacionados con la bbdd
	private ArrayList<Review> reviews;
	private ImageIcon foto;
	
	public Producto(long id,String titulo, String sinopsis, double precio, int rating, ArrayList<Review> comentarios, ImageIcon foto) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.sinopsis = sinopsis;
		this.precio = precio;
		this.rating = rating;
		this.reviews = comentarios;
		this.foto= foto;
	}
	
	public Producto() {
		super();
		this.id= 0;
		this.titulo = "";
		this.sinopsis = "";
		this.precio = 0;
		this.rating = 0;
		this.reviews = new ArrayList<Review>();
		this.foto= null;
	}
	public Producto(ProductoDTO productoDTO) {
		super();
		this.id= productoDTO.getId();
		this.titulo = productoDTO.getTitulo();
		this.sinopsis =productoDTO.getSinopsis();
		this.precio =productoDTO.getPrecio();
		this.rating = productoDTO.getRating();
		this.reviews = main.getReviewDAO().getReviewsProductoById(productoDTO.getId());
		this.foto= null;
	}

	public long getId() {
	    return id;
	}

	public void setId(long id) {
	    this.id = id;
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

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	
	public ArrayList<Review> getReviews() {
		return reviews;
	}

	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}

	public ImageIcon getFoto() {
		return foto;
	}

	public void setFoto(ImageIcon foto) {
		this.foto = foto;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(reviews, precio, rating, sinopsis, titulo, foto);
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
		return Objects.equals(reviews, other.reviews)
				&& Double.doubleToLongBits(precio) == Double.doubleToLongBits(other.precio) && rating == other.rating
				&& Objects.equals(sinopsis, other.sinopsis) && Objects.equals(titulo, other.titulo) 
				&& Objects.equals(foto, other.foto);
	}

	@Override
	public String toString() {
		return "Producto [titulo=" + titulo + ", sinopsis=" + sinopsis + ", precio=" + precio + ", rating=" + rating
				+ ", comentarios=" + reviews + ", foto = " + foto +"]";
	}
	
}
