package utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import domain.Pelicula;
import domain.TipoConsola;
import domain.TipoPelicula;
import domain.Videojuego;

public class Utils {
	// Imagen por defecto para cuando no se encuentra la imagen real
    private static final ImageIcon IMAGEN_DEFAULT = new ImageIcon(
            Utils.class.getResource("/images/noImagen.jpg")); // Coloca aquí tu default

    public static ImageIcon loadImage(String imageName, int length, int height) {
        try {
            InputStream is = Utils.class.getResourceAsStream("/images/" + imageName);
            if (is == null) {
                // Retorna la imagen default escalada
                return new ImageIcon(IMAGEN_DEFAULT.getImage().getScaledInstance(length, height, Image.SCALE_SMOOTH));
            }

            BufferedImage bi = ImageIO.read(is);
            if (bi == null) {
                // Si por alguna razón ImageIO falla, usar default
                return new ImageIcon(IMAGEN_DEFAULT.getImage().getScaledInstance(length, height, Image.SCALE_SMOOTH));
            }

            // Escalar imagen real
            return new ImageIcon(bi.getScaledInstance(length, height, Image.SCALE_SMOOTH));

        } catch (IOException e) {
            // En caso de error, usar default
            return new ImageIcon(IMAGEN_DEFAULT.getImage().getScaledInstance(length, height, Image.SCALE_SMOOTH));
        }
    }

	public static ArrayList<Videojuego> sortArrayTipoConsola(
			ArrayList<Videojuego> lista,
			TipoConsola ordenar,
			int n) {

		if (n <= 1) return lista;

		for (int i = 0; i < n - 1; i++) {

			TipoConsola actual = lista.get(i).getTipo();
			TipoConsola siguiente = lista.get(i + 1).getTipo();

			if (actual.compareTo(siguiente) > 0) {
				Videojuego temp = lista.get(i);
				lista.set(i, lista.get(i + 1));
				lista.set(i + 1, temp);
			}
		}

		return sortArrayTipoConsola(lista, ordenar, n - 1);
	}
	
	public static ArrayList<Pelicula> sortArrayTipoPelicula(
			ArrayList<Pelicula> lista,
			TipoConsola ordenar,
			int n) {

		if (n <= 1) return lista;

		for (int i = 0; i < n - 1; i++) {

			TipoPelicula actual = lista.get(i).getTipo();
			TipoPelicula siguiente = lista.get(i + 1).getTipo();

			if (actual.compareTo(siguiente) > 0) {
				Pelicula temp = lista.get(i);
				lista.set(i, lista.get(i + 1));
				lista.set(i + 1, temp);
			}
		}

		return sortArrayTipoPelicula(lista, ordenar, n - 1);
	}

}
