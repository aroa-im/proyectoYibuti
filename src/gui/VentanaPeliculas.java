package gui;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import db.ProductoDAO;
import domain.Admin;
import domain.Pelicula;
import domain.Seccion;
import domain.TipoPelicula;
import domain.Usuario;
import main.main;
import gui.components.Header;
import utils.Utils;

public class VentanaPeliculas extends JFrame {
	private static final long serialVersionUID = 1L;
	private Usuario usuario = main.getUsuario();
	private ProductoDAO productoDAO;
	private ArrayList<Pelicula> peliculas;
	
	public VentanaPeliculas() {

		this.productoDAO = new ProductoDAO();
		this.peliculas = productoDAO.getPeliculas();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		if (usuario == null) {
			setTitle("Videoclub - No logueado");
		} else {
			setTitle("Videoclub - logueado" + usuario.getClass().toString());
		}

		setSize(1200, 800);
		setLocationRelativeTo(null);

		// Panel superior que contendrá el header
		JPanel panelSuperior = new Header(Seccion.PELICULA, usuario, this);
		add(panelSuperior, BorderLayout.NORTH);

		// Panel inferior
		JPanel panelContenido = new JPanel(new BorderLayout());

		JPanel subPanelContenido1 = new JPanel(new BorderLayout());
		panelContenido.add(subPanelContenido1, BorderLayout.NORTH);

		TipoPelicula[] array = new TipoPelicula[TipoPelicula.values().length];
		int contador = 0;

		for (TipoPelicula metodo : TipoPelicula.values()) {
			array[contador] = metodo;
			contador++;
		}

		JComboBox ordenar = new JComboBox(array);
		ordenar.insertItemAt("Ordenar", 0);
		ordenar.setSelectedIndex(0);
		subPanelContenido1.add(ordenar, BorderLayout.EAST);
		ordenar.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				if (ordenar.getItemAt(0).equals("Ordenar")) {
					ordenar.removeItemAt(0);
				}
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				// TODO Auto-generated method stub
			}
		});

		JTextField buscador = new JTextField("Buscador");
		buscador.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscador.setText("");
			}
		});
		buscador.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println(buscador.getText());
				}
			}
		});

		subPanelContenido1.add(buscador, BorderLayout.CENTER);

		// Añadir pelicula
		if (usuario instanceof Admin) {
			JPanel panelAddPelicula = createPanelAddPelicula();
			subPanelContenido1.add(panelAddPelicula, BorderLayout.WEST);
		}

		JPanel subPanelContenido2 = new JPanel(new GridLayout(0, 4));
		
		for (Pelicula pelicula : peliculas) {
			JPanel panelCentrarPelicula = crearPanePeliculaCentrada(pelicula);
			subPanelContenido2.add(panelCentrarPelicula);
		}

		JScrollPane scrollBar = new JScrollPane(subPanelContenido2);
		panelContenido.add(scrollBar, BorderLayout.CENTER);

		add(panelContenido, BorderLayout.CENTER);

		setVisible(true);
	}
	
	private JPanel crearPanePeliculaCentrada(Pelicula pelicula) {
		JPanel panelCentrarPelicula = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		JPanel panelPelicula = new JPanel();
		panelPelicula.setLayout(new BoxLayout(panelPelicula, BoxLayout.Y_AXIS));
		
		// Cargar imagen de la película
		ImageIcon imagenPelicula = pelicula.getFoto();
		if (imagenPelicula == null) {
			imagenPelicula = Utils.loadImage("books/noImagen.jpg", 115, 160);
		}
		
		JLabel iconLabel = new JLabel(imagenPelicula);
		panelPelicula.add(iconLabel);
		
		JLabel tituloPelicula = new JLabel(pelicula.getTitulo());
		panelPelicula.add(tituloPelicula);
		
		panelCentrarPelicula.add(panelPelicula);
		
		// MouseListener para abrir la ventana de información
		panelPelicula.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				new VentanaInformacionRecurso(pelicula);
				
				
			}
		});
		
		return panelCentrarPelicula;
	}

	private JPanel createPanelAddPelicula() {
		JPanel panelAddPelicula = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, -5, 0, 5); 
		gbc.anchor = GridBagConstraints.CENTER; 

		ImageIcon iconoAddPelicula = Utils.loadImage("add.png", 24, 24);
		JLabel iconLabel = new JLabel(iconoAddPelicula);

		JLabel textLabel = new JLabel("Añadir pelicula");

		panelAddPelicula.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Panel clickeado");
			}
		});

		panelAddPelicula.add(iconLabel, gbc);
		gbc.gridx = 1; 
		panelAddPelicula.add(textLabel, gbc);
		return panelAddPelicula;
	}

	public static void main(String[] args) {
		VentanaPeliculas ventana2 = new VentanaPeliculas();
	}
}