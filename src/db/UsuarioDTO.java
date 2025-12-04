package db;

public class UsuarioDTO {
	private String dni;
	private String nombre;
	private String email;
	private int amonestaciones;
	private boolean admin;

	public UsuarioDTO() {
		super();
		this.dni = "";
		this.nombre = "";
		this.email = "";
		this.amonestaciones = 0;
		this.admin = false;
	}
	
	public UsuarioDTO(String dni, String nombre, String email, int amonestaciones, boolean admin) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.email = email;
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
		return "UsuarioDTO [dni=" + dni + ", nombre=" + nombre + ", email=" + email
				+ ", amonestaciones=" + amonestaciones + ", admin=" + admin + "]";
	}	
}
