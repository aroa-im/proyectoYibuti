package gui.components;

import domain.Admin;
import domain.Cliente;
import domain.Seccion;
import domain.Usuario;
import gui.VentanaPortada;
import utils.Utils;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Header extends JPanel {

	private static final long serialVersionUID = -1102784202811827191L;
	private static boolean tieneImagen = true; 

	public Header(Seccion seccion, Usuario usuario,JFrame ventana) {
		setLayout(new BorderLayout());
//      setBackground(Color.GRAY);
        
        // Primer panel (izquierdo)
        JPanel panelIzquierdo = new JPanel(new GridBagLayout());
//      panelIzquierdo.setBackground(Color.PINK);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 15); // Margen entre componentes (icono y texto)
        gbc.anchor = GridBagConstraints.CENTER; // Centrar verticalmente y horizontalmente        

        // Icono de la sección        
        String nombreIconoSeccion = obtenerNombreImagenSeccion(seccion);
        
        ImageIcon iconoSeccion = tieneImagen ? Utils.loadImage(nombreIconoSeccion,48,48) : new ImageIcon();
        JLabel iconLabel = new JLabel(iconoSeccion);
        
        // Añadir mouse listener para el ícono
        iconLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                navegarHacia(seccion, ventana, usuario);
            }
        });
        
        // Texto al lado del icono
        JLabel textLabel = new JLabel("Videoclub");
        
        // Añadir mouse listener para el texto
        textLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                VentanaPortada ventanaPortada = new VentanaPortada(usuario);
                ventanaPortada.setVisible(true);
                // Aquí puedes agregar la lógica que necesites
                ventana.dispose();
            }
        });
        
        if (tieneImagen) {
            panelIzquierdo.add(iconLabel, gbc);
        } else {
        	gbc.insets = new Insets(0, 15, 0, 15);
        }
        gbc.gridx = 1; // Segunda columna
        panelIzquierdo.add(textLabel, gbc);
        
        // Segundo panel (derecho)
        JPanel panelDerecho = new JPanel();
//      panelDerecho.setBackground(Color.DARK_GRAY);
        
        String nombreIconoUsuario = obtenerNombreImagenUsuario(usuario);        
        ImageIcon icon2 = Utils.loadImage(nombreIconoUsuario, 48, 48);
        JLabel iconLabel2 = new JLabel(icon2);
        
		iconLabel2.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
				switch(nombreIconoUsuario) {
					case "noUser.png":
						//new VentanaIniciarSesion(ventana);
						ventana.setVisible(false);
						break;
					default:
//						new VentanaInformacionUsuario(ventana);
						break;
				}	
            }
		});
        panelDerecho.add(iconLabel2);
               
        // Agregar los paneles izquierdo y derecho al Header
        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.EAST);
	}
	
	private String obtenerNombreImagenSeccion(Seccion seccion) {
		if (seccion == null) {
			tieneImagen = false;
			return "";
		};
		
		switch (seccion) {
		case PELICULA:
			return "peliculas.png";
		case VIDEOJUEGO:
			return "videojuegos.png";
		default:
			return "videoclub.png";
		}
	}
	
	private String obtenerNombreImagenUsuario(Usuario usuario) {
		if (usuario instanceof Cliente) {
        	return "user.png";
		} else if (usuario instanceof Admin) {
			return "adminUser.png";
		} else {
			return "noUser.png";
		}
	}
	
	private void navegarHacia(Seccion seccion, JFrame ventana, Usuario usuario) {
		JFrame nuevaVentana = null;
		switch (seccion) {
		case PELICULA:
			//nuevaVentana = new VentanaLibros();
			
			break;
		case VIDEOJUEGO:
			//nuevaVentana = new VentanaVideojuegos();
			break;
		}
		nuevaVentana.setVisible(true);
		ventana.dispose();
	}

}
