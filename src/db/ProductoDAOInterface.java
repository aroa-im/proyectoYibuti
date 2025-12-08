package db;

import java.util.ArrayList;

import domain.Cliente;
import domain.Pelicula;
import domain.Producto;
import domain.Videojuego;

public interface ProductoDAOInterface {
	boolean addProducto(Producto producto);
	ProductoDTO getProducto(long id);
	ArrayList<Producto> getProductos();
	boolean añadirReserva(long id, int diasDevolucion, Cliente cliente);
	ArrayList<ProductoDTO> getHistorialByCliente(String dniCliente);
	boolean productoUsadoByDniCliente(String dniCliente, Long id);
	boolean updateProducto(ProductoDTO producto, long idAntiguo);
	boolean deleteProductoById(long idProducto);
	void borrarRegistros();
	Producto getProductoByTitulo(String titulo);
	ArrayList<Pelicula> getPeliculas();
	ArrayList<Videojuego> getVideojuegos();

}
