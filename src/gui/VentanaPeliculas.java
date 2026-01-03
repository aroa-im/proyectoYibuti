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
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import db.ProductoDAO;
import domain.Admin;
import domain.Pelicula;
import domain.Seccion;
import domain.TipoPelicula;
import domain.Usuario;
import io.InputUtils;
import main.main;
import gui.components.Header;
import utils.Utils;

public class VentanaPeliculas extends JFrame {

	private static final long serialVersionUID = 1L;
	private Usuario usuario = main.getUsuario();
	private ProductoDAO productoDAO;
	private ArrayList<Pelicula> peliculas;

	public VentanaPeliculas() {
		// Inicializar DAO y películas
		this.productoDAO = new ProductoDAO();
		this.peliculas = productoDAO.getPeliculas();

		// Configuración básica de la ventana
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		if (usuario == null) {
			setTitle("Peliculas - No logueado");
		} else {
			setTitle("Peliculas - Logueado: " + usuario.getNombre());
		}
		setSize(1200, 800);
		setLocationRelativeTo(null);

		// Panel superior con Header
		JPanel panelSuperior = new Header(Seccion.PELICULA, usuario, this);
		add(panelSuperior, BorderLayout.NORTH);

		// Panel de contenido principal
		JPanel panelContenido = new JPanel(new BorderLayout());

		// Subpanel superior con controles (buscador, ordenar, añadir)
		JPanel subPanelContenido1 = new JPanel(new BorderLayout());
		panelContenido.add(subPanelContenido1, BorderLayout.NORTH);

		// Buscador (centro)
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
					System.out.println("Buscando: " + buscador.getText());
					// Aquí puedes implementar la lógica de búsqueda
				}
			}
		});
		subPanelContenido1.add(buscador, BorderLayout.CENTER);

		// ComboBox de ordenar (derecha)
		TipoPelicula[] arrayPeliculas = TipoPelicula.values();
		JComboBox<Object> ordenar = new JComboBox<>(arrayPeliculas);
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
				// No es necesario implementar
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				// No es necesario implementar
			}
		});

		// Panel para añadir película (solo si es Admin)
		if (usuario instanceof Admin) {
			JPanel panelAddPelicula = createPanelAddPelicula();
			subPanelContenido1.add(panelAddPelicula, BorderLayout.WEST);
		}

		// Panel Grid con las películas
		JPanel panelGrid = new JPanel(new GridLayout(0, 4));
		for (Pelicula pelicula : peliculas) {
			JPanel panel = crearPanePelicula(pelicula);
			panelGrid.add(panel);
		}

		// ScrollPane para el grid de películas
		JScrollPane scroll = new JScrollPane(panelGrid);
		panelContenido.add(scroll, BorderLayout.CENTER);

		// Añadir panel de contenido a la ventana
		add(panelContenido, BorderLayout.CENTER);

		setVisible(true);
	}

	private JPanel crearPanePelicula(Pelicula pelicula) {
		JPanel panelCentrar = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel panelPeli = new JPanel();
		panelPeli.setLayout(new BoxLayout(panelPeli, BoxLayout.Y_AXIS));

		// Cargar imagen de la película
		ImageIcon imagen = Utils.loadImage("peliculas/" + pelicula.getId() + ".jpg", 115, 160);

		JLabel imagenLabel = new JLabel(imagen);
		JLabel titulo = new JLabel(pelicula.getTitulo());

		panelPeli.add(imagenLabel);
		panelPeli.add(titulo);
		panelCentrar.add(panelPeli);

		// MouseListener para abrir la ventana de información
		panelPeli.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				new VentanaInformacionRecurso(pelicula);
			}
		});

		return panelCentrar;
	}

	private JPanel createPanelAddPelicula() {
		JPanel panelAddPelicula = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 5, 0, 5);
		gbc.anchor = GridBagConstraints.CENTER;

		ImageIcon iconoAddPelicula = Utils.loadImage("images/add.png", 24, 24);
		JLabel iconLabel = new JLabel(iconoAddPelicula);
		JLabel textLabel = new JLabel("Añadir película");

		panelAddPelicula.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Añadir película clickeado");
				// Aquí puedes abrir una ventana para añadir películas
			}
		});

		gbc.gridx = 0;
		panelAddPelicula.add(iconLabel, gbc);
		gbc.gridx = 1;
		panelAddPelicula.add(textLabel, gbc);

		return panelAddPelicula;
	}

	public static void main(String[] args) {
		new VentanaPeliculas();
	}
}