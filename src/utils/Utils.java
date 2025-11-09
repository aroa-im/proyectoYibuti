package utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Utils {
	
	/*
	 * Método que recibe una dirección de una imagen dentro de resources/
	 * y la carga en una variable de tipo ImageIcon,
	 * recibiendo como parámetros sus dimensiones.
	 */
	
	public static ImageIcon loadImage(String imageName, int length, int height) {
		ImageIcon result;
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(new File("resources/" + imageName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		result = new ImageIcon(bi.getScaledInstance(length, height, Image.SCALE_DEFAULT));
		return result;
	}

}
