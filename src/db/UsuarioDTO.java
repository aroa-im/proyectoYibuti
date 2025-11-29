package db;

import java.time.LocalDate;

public class UsuarioDTO {
	private String dni;
	private String nombre;
	private String email;
	private LocalDate fechaCreacion;
	private int amonestaciones;
	private boolean admin;

	public UsuarioDTO() {
		super();
		this.dni = "";
		this.nombre = "";
		this.email = "";
		this.fechaCreacion = LocalDate.now();
		this.amonestaciones = 0;
		this.admin = false;
	}
	
	public UsuarioDTO(String dni, String nombre, String email, LocalDate fechaCreacion, int amonestaciones, boolean admin) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.email = email;
		this.fechaCreacion = fechaCreacion;
		this.amonestaciones = amonestaciones;
		this.admin = admin;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public int getAmonestaciones() {
		return amonestaciones;
	}

	public void setAmonestaciones(int amonestaciones) {
		this.amonestaciones = amonestaciones;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "UsuarioDTO [dni=" + dni + ", nombre=" + nombre + ", email=" + email + ", fechaCreacion=" + fechaCreacion
				+ ", amonestaciones=" + amonestaciones + ", admin=" + admin + "]";
	}	
}
