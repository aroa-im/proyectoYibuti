package domain;

import java.time.LocalDate;

public interface Alquilable {
	public boolean crearAlquiler(Cliente cliente, Producto producto, LocalDate fechaDevolucion);
	public boolean avisoAlquilerTarde(Cliente cliente, Producto producto);
	public boolean terminarAlquiler(Cliente cliente, LocalDate fechaDevolucion);
}
