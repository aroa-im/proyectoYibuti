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

import javax.swing.ImageIcon;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import utils.Utils;

public class InputUtils {

    private static Scanner getScannerFromResource(String path) {
        InputStream input = InputUtils.class.getClassLoader().getResourceAsStream(path);

        if (input == null) {
            System.err.println("ERROR: No se encontró el archivo: " + path);
            return null;
        }

        return new Scanner(input);
    }
    
    public static ArrayList<Videojuego> cargarVideojuegos() {

        ArrayList<Videojuego> lista = new ArrayList<>();

        Scanner sc = getScannerFromResource("data/videojuegos.csv");
        if (sc == null) return lista;

        if (sc.hasNextLine()) sc.nextLine(); // Saltar cabecera

        while (sc.hasNextLine()) {
            String linea = sc.nextLine();
            String[] datos = linea.split(";");

            try {
                long id = Long.parseLong(datos[0]);
                String titulo = datos[1];
                String sinopsis = datos[2];
                float precio = Float.parseFloat(datos[3]);
                String autor = datos[4]; 
                TipoConsola tipo = TipoConsola.valueOf(datos[5].toUpperCase());  
                GeneroVideoJuego genero = GeneroVideoJuego.valueOf(datos[6].toUpperCase());  
                
                int rating = 0;  

                // RUTA CORRECTA DE IMÁGENES
                ImageIcon foto = Utils.loadImage("videojuegos/" + id + ".jpg", 98, 151);

                Videojuego v = new Videojuego(
                        id, titulo, sinopsis, precio, rating,
                        new ArrayList<>(), genero, tipo, autor, foto
                );

                lista.add(v);

            } catch (Exception e) {
                System.err.println("Error leyendo videojuego: " + linea);
                System.err.println("Detalle del error: " + e.getMessage());
                e.printStackTrace();
            }
        }

        sc.close();
        return lista;
    }
  
    public static ArrayList<Pelicula> cargarPeliculas() {

        ArrayList<Pelicula> lista = new ArrayList<>();

        Scanner sc = getScannerFromResource("data/peliculas.csv");
        if (sc == null) return lista;

        if (sc.hasNextLine()) sc.nextLine(); 

        while (sc.hasNextLine()) {

            String linea = sc.nextLine();
            String[] datos = linea.split(";");

            try {
                long id = Long.parseLong(datos[0]);
                String titulo = datos[1];
                String sinopsis = datos[2];        
                double precio = Double.parseDouble(datos[3]);  
                String director = datos[4];
                int duracion = Integer.parseInt(datos[5]);
                TipoPelicula tipo = TipoPelicula.valueOf(datos[6].toUpperCase());  
                GeneroPelicula genero = GeneroPelicula.valueOf(datos[7].toUpperCase());  

                ImageIcon foto = Utils.loadImage("peliculas/" + id + ".jpg", 98, 151);

                Pelicula p = new Pelicula(
                        id, titulo, sinopsis, precio, 0,
                        new ArrayList<>(), tipo, genero, director, duracion, foto
                );

                lista.add(p);

            } catch (Exception e) {
                System.err.println("Error leyendo película: " + linea);
                System.err.println("Detalle del error: " + e.getMessage());
                e.printStackTrace();
            }
        }

        sc.close();
        return lista;
    }
    

    // REVIEWS
    public static ArrayList<Review> cargarReviews(List<Producto> productos, List<Cliente> clientes) {

        ArrayList<Review> lista = new ArrayList<>();

        Scanner sc = getScannerFromResource("data/reviews.csv");
        if (sc == null) return lista;

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

                lista.add(new Review(id, producto, cliente, comentario, rating));

            } catch (Exception e) {
                System.err.println("Error leyendo review: " + linea);
            }
        }

        sc.close();
        return lista;
    }
}