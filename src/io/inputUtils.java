/*package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;

import domain.Libro;
import domain.Pelicula;
import domain.Review;
import utils.Utils;

public class inputUtils {
	public static ArrayList<Pelicula> cargarPeliculas() {
		ArrayList<Pelicula> listaPeliculas = new ArrayList<Pelicula>();
		File f = new File("resources/data/peliculas.csv");
		
		try {
			Scanner sc = new Scanner(f);
			
			if (sc.hasNextLine()) {
	            sc.nextLine(); // Esta línea se usa para saltarse la primera línea (encabezados)
	        }
			
			int indiceImagen = 1;
			while (sc.hasNextLine() && indiceImagen<61) {
				
				String linea = sc.nextLine();
				String[] datos = linea.split(";");
				
				try {
//					long id = Long.parseLong(datos[0]);
					String titulo = datos[0];
					double precio = Double.parseDouble(datos[1]);
					 String sinopsis = datos[2];
	                String autor = datos[3];
	                int duracion = Integer.parseInt(datos[4]);
	                String tipo = datos[5];
	                String genero = datos[6];

	                ImageIcon foto = Utils.loadImage("peliculas/" + indiceImagen + ".jpg", 98, 151);
	                ArrayList<Review> reviews = new ArrayList<>();

	                Pelicula pelicula = new Pelicula(titulo, sinopsis, duracion, indiceImagen, reviews, null, null, autor, duracion, foto);
	                listaPeliculas.add(pelicula);
	                indiceImagen++;

				} catch (Exception e) {
				    System.err.println("Error al convertir un número en la línea: " + linea);
				    e.printStackTrace();
				}
				
//				String titulo, String autor, int numeroDePaginas, Image foto, int id, String sinopsis,
//				ArrayList<Review> reviews, Genero genero, int rating
				
			}

			sc.close();
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: Archivo no encontrado");
		}
		return listaPeliculas;
	}	
}
*/

package io;

import domain.GeneroPelicula;
import domain.GeneroVideoJuego;
import domain.Pelicula;
import domain.Review;
import domain.TipoConsola;
import domain.TipoPelicula;
import domain.Videojuego;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.ImageIcon;

import utils.Utils;

public class inputUtils {
	public static ArrayList<Videojuego> cargarVideojuego() {

	    ArrayList<Videojuego> listaVideojuegos = new ArrayList<>();
	    File f = new File("resources/data/videojuegos.csv");

	    try {
	        Scanner sc = new Scanner(f);

	        // Saltar encabezado
	        if (sc.hasNextLine()) sc.nextLine();

	        while (sc.hasNextLine()) {

	            String linea = sc.nextLine();
	            String[] datos = linea.split(";");

	            try {
	                long id = Long.parseLong(datos[0]);
	                String titulo = datos[1];
	                String sinopsis = datos[2];
	                float precio = Float.parseFloat(datos[3]);

	                // Rating entre 0 y 5 (opcional ajustarlo)
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
}
    	
    
