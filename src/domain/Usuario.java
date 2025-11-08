package domain;

import java.util.Objects;

public abstract class Usuario {
	private String dni;
	private String nombre;
	private String email;
	private String contrasena;
	
	public Usuario(String dni, String nombre, String email, String contrasena) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.email = email;
		this.contrasena = contrasena;
	}
	
	public Usuario() {
		super();
		this.dni = "";
		this.nombre = "";
		this.email = "";
		this.contrasena = "";
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

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(dni, other.dni);
	}

	@Override
	public String toString() {
		return "Usuario [dni=" + dni + ", nombre=" + nombre + ", email=" + email + ", contrasena=" + contrasena + "]";
	}
	
}
