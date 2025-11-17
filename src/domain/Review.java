package domain;

import java.util.Objects;

public class Review {
	private Producto producto;
	private Cliente cliente;
	private String comentario;
	private int rating;
	
	public Review(Producto producto, Cliente cliente, String comentario, int rating) {
		super();
		this.producto = producto;
		this.cliente = cliente;
		this.comentario = comentario;
		this.rating = rating;
	}
	
	public Review() {
		super();
		this.producto = null;
		this.cliente = new Cliente();
		this.comentario = "";
		this.rating = 0;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
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
	public int hashCode() {
		return Objects.hash(cliente, comentario, producto, rating);
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
				&& Objects.equals(producto, other.producto) && rating == other.rating;
	}

	@Override
	public String toString() {
	    String nombreCliente = (cliente != null && cliente.getNombre() != null) ? cliente.getNombre() : "Anónimo";
	    return String.format("- Usuario: %s  |  Comentario: %s  |  Rating: %d/10", nombreCliente, comentario, rating);
	}
}
