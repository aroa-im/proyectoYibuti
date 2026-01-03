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
import domain.Videojuego;
import domain.Usuario;
import domain.Seccion;
import domain.TipoConsola;
import io.InputUtils;
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
			setTitle("Videojuegos - No logueado");
		} else {
			setTitle("Videojuegos - Logueado: " + usuario.getNombre());
		}

		setSize(1200, 800);
		setLocationRelativeTo(null);

		// Panel superior - Header
		JPanel panelSuperior = new Header(Seccion.VIDEOJUEGO, usuario, this);
		add(panelSuperior, BorderLayout.NORTH);

		// Panel de contenido principal
		JPanel panelContenido = new JPanel(new BorderLayout());

		// SubPanel 1: Barra de búsqueda, ComboBox y botón añadir
		JPanel subPanelContenido1 = new JPanel(new BorderLayout());

		// Buscador (Centro)
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
					System.out.println("Buscar: " + buscador.getText());
					// Aquí puedes implementar la lógica de búsqueda
				}
			}
		});
		subPanelContenido1.add(buscador, BorderLayout.CENTER);

		// ComboBox de consolas (Derecha)
		TipoConsola[] arrayConsolas = TipoConsola.values();
		JComboBox<TipoConsola> ordenar = new JComboBox<>(arrayConsolas);
		ordenar.insertItemAt(null, 0);
		ordenar.setSelectedIndex(0);
		
		// Renderizar "Ordenar" en la primera posición
		ordenar.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			JLabel label = new JLabel();
			if (value == null) {
				label.setText("Ordenar");
			} else {
				label.setText(value.toString());
			}
			if (isSelected) {
				label.setBackground(list.getSelectionBackground());
				label.setForeground(list.getSelectionForeground());
				label.setOpaque(true);
			}
			return label;
		});
		
		ordenar.addPopupMenuListener(new PopupMenuListener() {
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				if (ordenar.getSelectedIndex() == 0) {
					ordenar.removeItemAt(0);
					if (ordenar.getItemCount() > 0) {
						ordenar.setSelectedIndex(0);
					}
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
		subPanelContenido1.add(ordenar, BorderLayout.EAST);

		// Panel añadir videojuego (Izquierda - solo si es Admin)
		if (usuario instanceof Admin) {
			JPanel panelAddVideojuego = createPanelAddVideojuego();
			subPanelContenido1.add(panelAddVideojuego, BorderLayout.WEST);
		}

		panelContenido.add(subPanelContenido1, BorderLayout.NORTH);

		// SubPanel 2: Grid de videojuegos
		JPanel subPanelContenido2 = new JPanel(new GridLayout(0, 4));

		// Cargar videojuegos
		for (Videojuego videojuego : videojuegos) {
			JPanel panelCentrarVideojuego = crearPaneVideojuego(videojuego);
			subPanelContenido2.add(panelCentrarVideojuego);
		}

		JScrollPane scrollBar = new JScrollPane(subPanelContenido2);
		panelContenido.add(scrollBar, BorderLayout.CENTER);

		add(panelContenido, BorderLayout.CENTER);

		setVisible(true);
	}

	private JPanel crearPaneVideojuego(Videojuego videojuego) {
		JPanel panelCentrarVideojuego = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JPanel panelVideojuego = new JPanel();
		panelVideojuego.setLayout(new BoxLayout(panelVideojuego, BoxLayout.Y_AXIS));

		// Debug: ver qué foto trae el videojuego desde la BD
		ImageIcon imagenVideojuego = videojuego.getFoto();
		System.out.println("ID: " + videojuego.getId() + " - Foto desde BD: " + (imagenVideojuego != null ? "OK" : "NULL"));
		
		// Si no tiene foto, intentar cargar manualmente
		if (imagenVideojuego == null) {
			String ruta = "images/videojuegos/" + videojuego.getId() + ".jpg";
			System.out.println("Intentando cargar: " + ruta);
			imagenVideojuego = Utils.loadImage(ruta, 115, 160);
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
		gbc.insets = new Insets(0, 5, 0, 5);
		gbc.anchor = GridBagConstraints.CENTER;

		ImageIcon iconoAddVideojuego = Utils.loadImage("add.png", 24, 24);
		JLabel iconLabel = new JLabel(iconoAddVideojuego);

		JLabel textLabel = new JLabel("Añadir videojuego");

		panelAddVideojuego.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Añadir videojuego clickeado");
				// Aquí puedes abrir la ventana para añadir un videojuego
				// new VentanaAnadirVideojuego(VentanaVideojuegos.this);
			}
		});

		panelAddVideojuego.add(iconLabel, gbc);
		gbc.gridx = 1;
		panelAddVideojuego.add(textLabel, gbc);

		return panelAddVideojuego;
	}

	public static void main(String[] args) {
		new VentanaVideojuegos();
	}
}