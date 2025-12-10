package domain;

import java.time.LocalDate;

public class Alquiler {
    private int id;
    private Cliente cliente;
    private Producto producto;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;     
    private int devuelto;
    
	public Alquiler(int id, Cliente cliente, Producto producto, LocalDate fechaInicio, LocalDate fechaFin,
			int devuelto) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.producto = producto;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.devuelto = devuelto;
	}
    
	public Alquiler() {
		super();
		this.id = 0;
		this.cliente = new Cliente();
		this.producto = null;
		this.fechaInicio = LocalDate.now();
		this.fechaFin = LocalDate.now();
		this.devuelto = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public int isDevuelto() {
		return devuelto;
	}

	public void setDevuelto(int devuelto) {
		this.devuelto = devuelto;
	}

	@Override
	public String toString() {
		return "Alquiler [id=" + id + ", cliente=" + cliente + ", producto=" + producto + ", fechaInicio=" + fechaInicio
				+ ", fechaFin=" + fechaFin + ", devuelto=" + devuelto + "]";
	}
}