//package io;
//
//import java.util.ArrayList;
//
//import domain.Pelicula;
//import domain.Review;
//import domain.Usuario;
//import domain.Videojuego;
//import main.main;
//
//public class CargarDatosBBDD {
////	public CargarDatosBBDD() {
////		ArrayList<Pelicula> listaPeliculas = InputUtils.cargarPeliculas();
////		addPeliculas(listaPeliculas);
////		
////		ArrayList<Videojuego> listaVideojuegos = InputUtils.cargarVideojuegos();
////		addVideojuegos(listaVideojuegos);
////			
////	}
////	private void addPeliculas(ArrayList<Pelicula> listaPeliculas) {
////		for (Pelicula pelicula : listaPeliculas) {
////			main.getProductoDAO()).addPeliculas(pelicula);
////		}		
////	}
//}

package io;

import java.util.ArrayList;
import db.ProductoDAO;
import domain.Pelicula;
import domain.Videojuego;
import main.main;

public class CargarDatosBBDD {
    
    public CargarDatosBBDD() {
        System.out.println("=== INICIANDO CARGA DE DATOS CSV A BASE DE DATOS ===\n");
        
        // Cargar películas
        ArrayList<Pelicula> listaPeliculas = InputUtils.cargarPeliculas();
        addPeliculas(listaPeliculas);
        
        // Cargar videojuegos
        ArrayList<Videojuego> listaVideojuegos = InputUtils.cargarVideojuegos();
        addVideojuegos(listaVideojuegos);
        
        // Verificar datos cargados
        verificarDatos();
        
        System.out.println("\n=== CARGA COMPLETADA ===");
    }
    
    private void addPeliculas(ArrayList<Pelicula> listaPeliculas) {
        System.out.println("--- CARGANDO PELÍCULAS ---");
        System.out.println("Total películas en CSV: " + listaPeliculas.size());
        
        ProductoDAO productoDAO = (ProductoDAO) main.getProductoDAO();
        int insertadas = 0;
        int fallidas = 0;
        
        for (Pelicula pelicula : listaPeliculas) {
            boolean exito = productoDAO.addProducto(pelicula);
            if (exito) {
                insertadas++;
                System.out.println("Película insertada: " + pelicula.getTitulo() + " (ID: " + pelicula.getId() + ")");
            } else {
                fallidas++;
                System.out.println("Error al insertar: " + pelicula.getTitulo());
            }
        }
        
        System.out.println("\nResumen Películas:");
        System.out.println("  - Insertadas: " + insertadas);
        System.out.println("  - Fallidas: " + fallidas);
        System.out.println();
    }
    
    private void addVideojuegos(ArrayList<Videojuego> listaVideojuegos) {
        System.out.println("--- CARGANDO VIDEOJUEGOS ---");
        System.out.println("Total videojuegos en CSV: " + listaVideojuegos.size());
        
        ProductoDAO productoDAO = (ProductoDAO) main.getProductoDAO();
        int insertados = 0;
        int fallidos = 0;
        
        for (Videojuego videojuego : listaVideojuegos) {
            // Crear nuevo videojuego con ID ajustado
            long nuevoId = videojuego.getId() + 60;
            Videojuego videojuegoAjustado = new Videojuego(
                nuevoId,
                videojuego.getTitulo(),
                videojuego.getSinopsis(),
                videojuego.getPrecio(),
                videojuego.getRating(),
                videojuego.getReviews(),
                videojuego.getGenero(),
                videojuego.getTipo(),
                videojuego.getAutor(),
                videojuego.getFoto()
            );
            
            boolean exito = productoDAO.addProducto(videojuegoAjustado);
            if (exito) {
                insertados++;
                System.out.println("Videojuego insertado: " + videojuego.getTitulo() + " (ID: " + videojuego.getId() + ")");
            } else {
                fallidos++;
                System.out.println("Error al insertar: " + videojuego.getTitulo());
            }
        }
        
        System.out.println("\nResumen Videojuegos:");
        System.out.println("  - Insertados: " + insertados);
        System.out.println("  - Fallidos: " + fallidos);
        System.out.println();
    }
    
    private void verificarDatos() {
        System.out.println("--- VERIFICANDO DATOS EN BD ---");
        ProductoDAO productoDAO = (ProductoDAO) main.getProductoDAO();
        
        ArrayList<Pelicula> peliculas = productoDAO.getPeliculas();
        ArrayList<Videojuego> videojuegos = productoDAO.getVideojuegos();
        
        System.out.println("Películas en BD: " + peliculas.size());
        System.out.println("Videojuegos en BD: " + videojuegos.size());
        System.out.println("Total productos en BD: " + (peliculas.size() + videojuegos.size()));
    }
    
    /**
     * Método main para ejecutar la carga de datos
     */
    public static void main(String[] args) {
        // IMPORTANTE: Ejecutar solo una vez
        new CargarDatosBBDD();
    }
}
