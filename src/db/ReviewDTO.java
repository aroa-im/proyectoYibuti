package db;

public class ReviewDTO {
	private int id;
	private String comentario;
	private int rating;
	private long idProducto;
	private String dniCliente;
	
	public ReviewDTO() {
		super();
		this.id = 0;
		this.comentario = "";
		this.rating = 0;
		this.idProducto = 0l;
		this.dniCliente = "";
	}
	
	public ReviewDTO(int id, String comentario, int rating, long idProducto, String dniCliente) {
		super();
		this.id = id;
		this.comentario = comentario;
		this.rating = rating;
		this.idProducto = idProducto;
		this.dniCliente = dniCliente;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
	
	public long getIdProducto() {
		return idProducto;
	}
	
	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}
	
	public String getDniCliente() {
		return dniCliente;
	}
	
	public void setDniCliente(String dniCliente) {
		this.dniCliente = dniCliente;
	}
	
	@Override
	public String toString() {
		return "ReviewDTO [id=" + id + ", comentario=" + comentario + ", rating=" + rating + ", idProducto=" + idProducto
				+ ", dniCliente=" + dniCliente + "]";
	}
}
