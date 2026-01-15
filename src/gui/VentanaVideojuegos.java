package gui;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import domain.Admin;
import domain.Videojuego;
import domain.Usuario;
import domain.Seccion;
import domain.TipoConsola;
import main.main;
import gui.components.Header;
import utils.Utils;

public class VentanaVideojuegos extends JFrame {

	private static final long serialVersionUID = 1L;
	private Usuario usuario = main.getUsuario();
	private final ArrayList<Videojuego> listaVideojuegos = main.getProductoDAO().getVideojuegos();
	private ArrayList<Videojuego> listaVideojuegosRenderizada = new ArrayList<Videojuego>(listaVideojuegos);
	
	public VentanaVideojuegos() {
		// Inicializar DAO y cargar videojuegos
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
		panelContenido.add(subPanelContenido1, BorderLayout.NORTH);

		TipoConsola[] arrayVideojuegos = new TipoConsola[4];
		int contador = 0;

		for (TipoConsola tipoConsola : TipoConsola.values()) {
			arrayVideojuegos[contador] = tipoConsola;
			contador++;
		}

		JComboBox<Object> ordenar = new JComboBox<>();
		subPanelContenido1.add(ordenar, BorderLayout.EAST);

		ordenar.addItem("ORDENAR");
		for (TipoConsola tipo : TipoConsola.values()) {
			ordenar.addItem(tipo);
		}

		ordenar.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			JLabel label = new JLabel();
			if (value == null) {
				label.setText(""); // texto que verá el usuario
			} else if (value instanceof String) {
				label.setText((String) value);
			} else if (value instanceof TipoConsola) {
				label.setText(((TipoConsola) value).toString());
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
		ordenar.setSelectedIndex(0);

		// Buscador (Centro)
		JTextField buscador = new JTextField("Buscador");
		buscador.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscador.setText("");
			}
		});
		subPanelContenido1.add(buscador, BorderLayout.CENTER);

		if (usuario instanceof Admin) {
			JPanel panelAddVideojuego = createPanelAddVideojuego();
			subPanelContenido1.add(panelAddVideojuego, BorderLayout.WEST);
		}

		JPanel subPanelContenido2 = new JPanel(new GridLayout(0, 4));

		int contadorVideojuegos = 0;
		for (Videojuego videojuego : listaVideojuegosRenderizada) {
			JPanel panelcentrarVideojuego = crearPanelVideojuego(videojuego);
			subPanelContenido2.add(panelcentrarVideojuego);
			if (contadorVideojuegos >= 60)
				break;
			contadorVideojuegos++;
		}

		JScrollPane scrollBar = new JScrollPane(subPanelContenido2);
		panelContenido.add(scrollBar, BorderLayout.CENTER);

		add(panelContenido, BorderLayout.CENTER);

		buscador.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					List<Videojuego> listaFiltrada = listaVideojuegos.stream().filter(videojuego -> videojuego
							.getTitulo().toLowerCase().contains(buscador.getText().toLowerCase())).toList();
					listaVideojuegosRenderizada = new ArrayList<Videojuego>(listaFiltrada);

					// Llamar a recargar el panel
					recargarPanelContenido(subPanelContenido2);
				}
			}
		});

		ordenar.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				Object item = e.getItem();
				ordenarVideojuegos(item, subPanelContenido2);
			}
		});

		setVisible(true);
	}

	private JPanel crearPanelVideojuego(Videojuego videojuego) {
		JPanel panelCentrarVideojuego = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JPanel panelVideojuego = new JPanel();
		panelVideojuego.setLayout(new BoxLayout(panelVideojuego, BoxLayout.Y_AXIS));

		ImageIcon imagenVideojuego;
		if (videojuego.getFoto() != null) {
			imagenVideojuego = videojuego.getFoto();
		} else {
			imagenVideojuego = Utils.loadImage("noImagen.jpg", 115, 160);
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

	private void recargarPanelContenido(JPanel subPanelContenido2) {
		subPanelContenido2.removeAll(); // Eliminar todos los componentes actuales.

		int contadorVideojuegos = 0;
		for (Videojuego videojuego : listaVideojuegosRenderizada) {
			JPanel panelCentrarVideojuego = crearPanelVideojuego(videojuego);
			subPanelContenido2.add(panelCentrarVideojuego);
			if (contadorVideojuegos >= 60)
				break;
			contadorVideojuegos++;
		}
		subPanelContenido2.revalidate(); // Informar al layout que actualice la UI.
		subPanelContenido2.repaint(); // Redibujar el panel.
	}

	private void ordenarVideojuegos(Object item, JPanel subPanelContenido2) {
		if (item instanceof String && item.equals("ORDENAR")) {
			listaVideojuegosRenderizada = new ArrayList<>(listaVideojuegos);
		} else if (item instanceof TipoConsola tipo) {
			listaVideojuegosRenderizada = new ArrayList<>(
					listaVideojuegos.stream().filter(v -> v.getTipo() == tipo).toList());
		}
		recargarPanelContenido(subPanelContenido2);
	}

	public static void main(String[] args) {
		new VentanaVideojuegos();
	}
}