package utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Utils {
	
	
	public static ImageIcon loadImage(String imageName, int length, int height) {
        ImageIcon result = null;
        try {
            InputStream is = Utils.class.getResourceAsStream("/images/" + imageName);
            if (is == null) {
                throw new IOException("No se encontró el archivo de imagen: " + imageName);
            }

            BufferedImage bi = ImageIO.read(is);
            result = new ImageIcon(bi.getScaledInstance(length, height, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
	}

}
