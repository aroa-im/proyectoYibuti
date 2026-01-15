package db;

import java.util.ArrayList;
import java.util.List;

import domain.Alquiler;
import domain.Cliente;
import domain.Producto;

public interface AlquilerDAOInterface {
	boolean insertar(Alquiler alquiler);
	Alquiler obtenerPorId(int id);
	List<Alquiler> obtenerTodos();
	List<Alquiler> obtenerPorCliente(String dniCliente);
	List<Alquiler> obtenerActivosPorCliente(String dniCliente);
	List<Alquiler> obtenerPorProducto(int idProducto);
	boolean actualizar(Alquiler alquiler);
	boolean marcarComoDevuelto(int id);
	boolean productoEstaAlquilado(int idProducto);
	List<Alquiler> obtenerAlquileresVencidos();
	boolean eliminar(int id);
	Cliente obtenerClienteBasico(String dni);
	void borrarRegistros();
	ArrayList<Producto> getProductosAlquiladosByUsuario(String dniCliente);
}
