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
import domain.Seccion;
import domain.TipoConsola;
import domain.Usuario;
import domain.Videojuego;
import main.main;
import gui.components.Header;
import utils.Utils;

public class VentanaVideojuegos extends JFrame {
	private static final long serialVersionUID = 1L;
	private Usuario usuario = main.getUsuario();
	private ProductoDAO productoDAO;
	private ArrayList<Videojuego> videojuegos;
	
	public VentanaVideojuegos() {
		// Inicializar DAO y cargar videojuegos
		this.productoDAO = new ProductoDAO();
		this.videojuegos = productoDAO.getVideojuegos();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		if (usuario == null) {
			setTitle("Videoclub - No logueado");
		} else {
			setTitle("Videoclub - logueado" + usuario.getClass().toString());
		}

		setSize(1200, 800);
		setLocationRelativeTo(null);

		// Panel superior que contendrá el header
		JPanel panelSuperior = new Header(Seccion.VIDEOJUEGO, usuario, this);
		add(panelSuperior, BorderLayout.NORTH);

		// Panel inferior
		JPanel panelContenido = new JPanel(new BorderLayout());

		JPanel subPanelContenido1 = new JPanel(new BorderLayout());
		panelContenido.add(subPanelContenido1, BorderLayout.NORTH);

		// Cambio principal: usar TipoConsola en lugar de TipoPelicula
		TipoConsola[] array = new TipoConsola[TipoConsola.values().length];
		int contador = 0;

		for (TipoConsola consola : TipoConsola.values()) {
			array[contador] = consola;
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

		// Añadir videojuego
		if (usuario instanceof Admin) {
			JPanel panelAddVideojuego = createPanelAddVideojuego();
			subPanelContenido1.add(panelAddVideojuego, BorderLayout.WEST);
		}

		JPanel subPanelContenido2 = new JPanel(new GridLayout(0, 4));
		
		// Cargar videojuegos desde la base de datos
		for (Videojuego videojuego : videojuegos) {
			JPanel panelCentrarVideojuego = crearPaneVideojuegoCentrada(videojuego);
			subPanelContenido2.add(panelCentrarVideojuego);
		}

		JScrollPane scrollBar = new JScrollPane(subPanelContenido2);
		panelContenido.add(scrollBar, BorderLayout.CENTER);

		add(panelContenido, BorderLayout.CENTER);

		setVisible(true);
	}
	
	private JPanel crearPaneVideojuegoCentrada(Videojuego videojuego) {
		JPanel panelCentrarVideojuego = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		JPanel panelVideojuego = new JPanel();
		panelVideojuego.setLayout(new BoxLayout(panelVideojuego, BoxLayout.Y_AXIS));
		
		// Cargar imagen del videojuego
		ImageIcon imagenVideojuego = videojuego.getFoto();
		if (imagenVideojuego == null) {
			imagenVideojuego = Utils.loadImage("books/noImagen.jpg", 115, 160);
		}
		
		JLabel iconLabel = new JLabel(imagenVideojuego);
		panelVideojuego.add(iconLabel);
		
		JLabel tituloVideojuego = new JLabel(videojuego.getTitulo());
		panelVideojuego.add(tituloVideojuego);
		
		panelCentrarVideojuego.add(panelVideojuego);
		
		panelVideojuego.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();

				new VentanaInformacionRecurso(videojuego);
			}
		});
		
		return panelCentrarVideojuego;
	}

	private JPanel createPanelAddVideojuego() {
		JPanel panelAddVideojuego = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, -5, 0, 5);
		gbc.anchor = GridBagConstraints.CENTER;

		ImageIcon iconoAddVideojuego = Utils.loadImage("add.png", 24, 24);
		JLabel iconLabel = new JLabel(iconoAddVideojuego);

		JLabel textLabel = new JLabel("Añadir videojuego");

		panelAddVideojuego.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Panel clickeado");
			}
		});

		panelAddVideojuego.add(iconLabel, gbc);
		gbc.gridx = 1;
		panelAddVideojuego.add(textLabel, gbc);
		return panelAddVideojuego;
	}

	public static void main(String[] args) {
		VentanaVideojuegos ventana = new VentanaVideojuegos();
	}
}