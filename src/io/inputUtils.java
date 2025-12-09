
package io;

import domain.Cliente;
import domain.GeneroPelicula;
import domain.GeneroVideoJuego;
import domain.Pelicula;
import domain.Producto;
import domain.Review;
import domain.TipoConsola;
import domain.TipoPelicula;
import domain.Videojuego;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.ImageIcon;

import utils.Utils;

public class inputUtils {
	public static ArrayList<Videojuego> cargarVideojuego() {

	    ArrayList<Videojuego> listaVideojuegos = new ArrayList<>();
	    File f = new File("resources/data/videojuegos.csv");

	    try {
	        Scanner sc = new Scanner(f);

	      
	        if (sc.hasNextLine()) sc.nextLine();

	        while (sc.hasNextLine()) {

	            String linea = sc.nextLine();
	            String[] datos = linea.split(";");

	            try {
	                long id = Long.parseLong(datos[0]);
	                String titulo = datos[1];
	                String sinopsis = datos[2];
	                float precio = Float.parseFloat(datos[3]);

	                int rating = Integer.parseInt(datos[4]);

	                GeneroVideoJuego genero = GeneroVideoJuego.valueOf(datos[5]);
	                TipoConsola tipo = TipoConsola.valueOf(datos[6]);
	                String autor = datos[7];

	                ImageIcon foto = Utils.loadImage("videojuegos/" + id + ".jpg", 98, 151);

	                ArrayList<Review> comentarios = new ArrayList<>();

	                Videojuego videojuego = new Videojuego(
	                        id,
	                        titulo,
	                        sinopsis,
	                        precio,
	                        rating,
	                        comentarios,
	                        genero,
	                        tipo,
	                        autor,
	                        foto
	                );

	                listaVideojuegos.add(videojuego);

	            } catch (Exception e) {
	                System.err.println("Error en línea: " + linea);
	                e.printStackTrace();
	            }
	        }

	        sc.close();

	    } catch (FileNotFoundException e) {
	        System.err.println("ERROR: Archivo de videojuegos no encontrado");
	    }

	    return listaVideojuegos;
	}
	
	public static ArrayList<Pelicula> cargarPeliculas() {
	    ArrayList<Pelicula> listaPeliculas = new ArrayList<>();
	    File f = new File("resources/data/peliculas.csv");

	    try (Scanner sc = new Scanner(f)) {

	        if (sc.hasNextLine()) sc.nextLine();

	        while (sc.hasNextLine()) {
	            String linea = sc.nextLine();
	            String[] datos = linea.split(";");

	            try {

	                long id = Long.parseLong(datos[0]);
	                String titulo = datos[1];
	                double precio = Double.parseDouble(datos[2]);
	                String sinopsis = datos[3];
	                String director = datos[4];       
	                int duracion = Integer.parseInt(datos[5]);
	                GeneroPelicula genero = GeneroPelicula.valueOf(datos[6].toUpperCase());
	                TipoPelicula tipo = TipoPelicula.valueOf(datos[7].toUpperCase());

	                int rating = 0;

	                ImageIcon foto = Utils.loadImage("peliculas/" + id + ".jpg", 98, 151);


	                ArrayList<Review> reviews = new ArrayList<>();

	                Pelicula pelicula = new Pelicula(
	                        id,
	                        titulo,
	                        sinopsis,
	                        precio,
	                        rating,
	                        reviews,
	                        tipo,
	                        genero,
	                        director,
	                        duracion,
	                        foto
	                );

	                listaPeliculas.add(pelicula);

	            } catch (NumberFormatException e) {
	                System.err.println("Error de número en línea: " + linea);
	            } catch (IllegalArgumentException e) {
	                System.err.println("Error de enum en línea: " + linea);
	            } catch (Exception e) {
	                System.err.println("Error inesperado en línea: " + linea);
	                e.printStackTrace();
	            }
	        }

	    } catch (FileNotFoundException e) {
	        System.err.println("ERROR: Archivo de películas no encontrado");
	    }

	    return listaPeliculas;
	}


	public static ArrayList<Review> cargarReviews(List<Producto> productos, List<Cliente> clientes) {
	    ArrayList<Review> listaReviews = new ArrayList<>();
	    File f = new File("resources/data/reviews.csv");
	
	    try (Scanner sc = new Scanner(f)) {
	        if (sc.hasNextLine()) sc.nextLine();
	
	        while (sc.hasNextLine()) {
	            String linea = sc.nextLine();
	            String[] datos = linea.split(";");
	
	            try {
	                long id = Long.parseLong(datos[0]);
	                long idProducto = Long.parseLong(datos[1]);
	                String dniCliente = datos[2];
	                String comentario = datos[3];
	                int rating = Integer.parseInt(datos[4]);
	
	                
	                Producto producto = productos.stream()
	                    .filter(p -> p.getId() == idProducto)
	                    .findFirst()
	                    .orElse(null);
	
	                Cliente cliente = clientes.stream()
	                    .filter(c -> c.getDni().equals(dniCliente))
	                    .findFirst()
	                    .orElse(null);
	
	                Review review = new Review(id, producto, cliente, comentario, rating);
	                listaReviews.add(review);
	
	            } catch (Exception e) {
	                System.err.println("Error en línea: " + linea);
	                e.printStackTrace();
	            }
	        }
	    } catch (FileNotFoundException e) {
	        System.err.println("ERROR: Archivo de reviews no encontrado");
	    }
	
	    return listaReviews;
	}
	
	public static Producto buscarProductoPorId(ArrayList<Producto> productos, String id) {
	    for (Producto producto : productos) {
	        if (String.valueOf(producto.getId()).equals(id)) {
	            return producto;
	        }
	    }
	    return null;
	}


}
    	
    
