package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Admin;
import domain.Cliente;
import domain.Usuario;
import utils.Utils;

public class VentanaPortada extends JFrame{
	private JFrame currentWindow = this;
	private static final long serialVersionUID = -7861052196761464371L;

	public VentanaPortada(Usuario usuario) {
		
		setTitle("Videoclub - Portada");
		setSize(1200, 800); 
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel top = new JPanel();
		top.setLayout(new BorderLayout());
		JPanel topUsuario = new JPanel();
		JPanel topTitulo = new JPanel();
		JLabel YibutiLabel = new JLabel("Videoclub");
		
		// PROCESO DE ABRIR LAS IMAGENES Y ASIGNARLAS A SUS LABELS:
		// Imagen del usuario
		ImageIcon usuarioIcono = null; // Cambia el tamaño
		if (usuario instanceof Admin) {
			usuarioIcono = Utils.loadImage("adminUser.png", 80, 80);
		} else if (usuario instanceof Cliente){
			usuarioIcono = Utils.loadImage("user.png", 80, 80);
		}else {
			usuarioIcono = Utils.loadImage("noUser.png", 80, 80);
		}
		
		JLabel usuarioLabel = new JLabel();
		usuarioLabel.setIcon(usuarioIcono);
		
		usuarioLabel.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        if (usuario == null) {
		            new VentanaIniciarSesion(currentWindow);
		            setVisible(false);
		        }
		    }
		});
		
		// Imagen de la pelicula
		ImageIcon peliculaIcono = Utils.loadImage("peliculas.png", 200, 200); // Cambia el tamaño
		JLabel peliculaLabel = new JLabel();
		peliculaLabel.setIcon(peliculaIcono);
		
		//Imagen del videojuego
		ImageIcon videojuegoIcono = Utils.loadImage("videojuegos.png", 200, 200); // Cambia el tamaño
		JLabel videojuegoLabel = new JLabel();
		videojuegoLabel.setIcon(videojuegoIcono);
		
		// PANEL SUPERIOR
		YibutiLabel.setFont(new Font("Verdana", Font.PLAIN, 72));
		
		topUsuario.setLayout(new FlowLayout(FlowLayout.RIGHT));
		topUsuario.add(usuarioLabel);
		topTitulo.add(YibutiLabel);
		topTitulo.setBorder(new EmptyBorder(0, 500, 0, 450));
		
		top.add(topTitulo, BorderLayout.CENTER);
		top.add(topUsuario, BorderLayout.EAST);
		add(top, BorderLayout.NORTH);
		
		// PANEL CENTRAL
		Font buttonFont = new Font("Verdana", Font.BOLD, 15);
		
		JPanel mid = new JPanel();
		JButton peliculasButton = new JButton("Peliculas");
		JButton videojuegosButton = new JButton("Videojuegos");
		
		peliculasButton.addActionListener(e -> {
		    new VentanaPeliculas(usuario);
		    dispose();
		});
		
		videojuegosButton.addActionListener(e -> {
		    new VentanaVideojuegos(usuario);
		    dispose();
		});
		
		JPanel peliculasButtonPanel = new JPanel();
		JPanel videojuegosButtonPanel = new JPanel();

		// Asignación a GridLayout para poder poner los iconos encima de los botones
		peliculasButtonPanel.setLayout(new GridLayout(2, 1));
		videojuegosButtonPanel.setLayout(new GridLayout(2, 1));
		
		peliculasButton.setFont(buttonFont);
		videojuegosButton.setFont(buttonFont);
		
		// Paneles para poder ajustar los botones al tamaño de las imagenes
		JPanel peliculasAlignPanel = new JPanel();
		peliculasAlignPanel.add(peliculasButton);
		
		JPanel videojuegosAlignPanel = new JPanel();
		videojuegosAlignPanel.add(videojuegosButton);
		
		peliculasButtonPanel.add(peliculaLabel);
		peliculasButtonPanel.add(peliculasAlignPanel);
		
		videojuegosButtonPanel.add(videojuegoLabel);
		videojuegosButtonPanel.add(videojuegosAlignPanel);
		
		mid.add(peliculasButtonPanel);
		mid.add(videojuegosButtonPanel);
		
		// Bordes para añadir espaciado entre paneles
		peliculasButtonPanel.setBorder(new EmptyBorder(0, 50, 0, 50));
		videojuegosButtonPanel.setBorder(new EmptyBorder(0, 50, 0, 50));
		top.setBorder(new EmptyBorder(50, 50, 0, 0));
		mid.setBorder(new EmptyBorder(150, 0, 0, 0));
		add(mid, BorderLayout.CENTER);

		setVisible(true);
	}
	
	public static void main(String[] args) {
		new VentanaPortada(null);
//		new VentanaPortada(new Cliente());
//		new VentanaPortada(new Admin());
	}
}
