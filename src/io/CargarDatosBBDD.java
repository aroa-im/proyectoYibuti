package io;

import java.util.ArrayList;
import db.ProductoDAO;
import domain.Pelicula;
import domain.Videojuego;
import main.main;

public class CargarDatosBBDD {
    
    public CargarDatosBBDD() {
         // Cargar películas
        ArrayList<Pelicula> listaPeliculas = InputUtils.cargarPeliculas();
        addPeliculas(listaPeliculas);
        
        // Cargar videojuegos
        ArrayList<Videojuego> listaVideojuegos = InputUtils.cargarVideojuegos();
        addVideojuegos(listaVideojuegos);
        
        // Verificar datos cargados
        verificarDatos();
    }
    
    private void addPeliculas(ArrayList<Pelicula> listaPeliculas) {
        System.out.println("Total películas en CSV: " + listaPeliculas.size());
        
        ProductoDAO productoDAO = (ProductoDAO) main.getProductoDAO();
        int insertadas = 0;
        int fallidas = 0;
        
        for (Pelicula pelicula : listaPeliculas) {
            boolean exito = productoDAO.addProducto(pelicula);
            if (exito) {
                insertadas++;
            } else {
                fallidas++;
                System.out.println("Error al insertar: " + pelicula.getTitulo());
            }
        }
        System.out.println("  - Insertadas: " + insertadas);
        System.out.println("  - Fallidas: " + fallidas);
    }
    
    private void addVideojuegos(ArrayList<Videojuego> listaVideojuegos) {
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
            } else {
                fallidos++;
                System.out.println("Error al insertar: " + videojuego.getTitulo());
            }
        }
        System.out.println("  - Insertados: " + insertados);
        System.out.println("  - Fallidos: " + fallidos);
    }
    
    private void verificarDatos() {
        ProductoDAO productoDAO = (ProductoDAO) main.getProductoDAO();
        
        ArrayList<Pelicula> peliculas = productoDAO.getPeliculas();
        ArrayList<Videojuego> videojuegos = productoDAO.getVideojuegos();
        System.out.println("Total productos en BD: " + (peliculas.size() + videojuegos.size()));
    }   
}
