package domain;

import java.time.LocalDateTime;

public class LogAccion {

	private int id;
	private LocalDateTime fecha;
	private String descripcion;
	private String dniAdmin;
	
	public LogAccion() {
		super();
		this.id = 0;
		this.fecha = LocalDateTime.now();
		this.descripcion = "";
		this.dniAdmin = "";
	}
	
	public LogAccion(int id, LocalDateTime fecha, String descripcion, String dniAdmin) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.descripcion = descripcion;
		this.dniAdmin = dniAdmin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDniAdmin() {
		return dniAdmin;
	}

	public void setDniAdmin(String dniAdmin) {
		this.dniAdmin = dniAdmin;
	}

	@Override
	public String toString() {
		return "LogAccion [id=" + id + ", fecha=" + fecha + ", descripcion=" + descripcion + ", dniAdmin=" + dniAdmin
				+ "]";
	}
}
