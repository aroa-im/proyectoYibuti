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
import domain.Pelicula;
import domain.Seccion;
import domain.TipoConsola;
import domain.TipoPelicula;
import domain.Usuario;
import main.main;
import gui.components.Header;
import utils.Utils;

public class VentanaPeliculas extends JFrame {

	private static final long serialVersionUID = 1L;
	private Usuario usuario = main.getUsuario();
	private final ArrayList<Pelicula> listaPeliculas = main.getProductoDAO().getPeliculas();
	private ArrayList<Pelicula> listaPeliculasRenderizada = new ArrayList<Pelicula>(listaPeliculas);
	
	public VentanaPeliculas() {
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
		TipoPelicula[] arrayPeliculas = new TipoPelicula[3];
		int contador = 0;

		for (TipoPelicula tipoPelicula : TipoPelicula.values()) {
			arrayPeliculas[contador] = tipoPelicula;
			contador++;
		}

		JComboBox<Object> ordenar = new JComboBox<>();
		subPanelContenido1.add(ordenar, BorderLayout.EAST);

		ordenar.addItem("ORDENAR");
		for (TipoPelicula tipo : TipoPelicula.values()) {
			ordenar.addItem(tipo);
		}

		ordenar.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			JLabel label = new JLabel();
			if (value == null) {
				label.setText(""); // texto que verá el usuario
			} else if (value instanceof String) {
				label.setText((String) value);
			} else if (value instanceof TipoPelicula) {
				label.setText(((TipoPelicula) value).toString());
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

		// Buscador (centro)
		JTextField buscador = new JTextField("Buscador");
		buscador.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscador.setText("");
			}
		});

		subPanelContenido1.add(buscador, BorderLayout.CENTER);

		if (usuario instanceof Admin) {
			JPanel panelAddPelicula = createPanelAddPelicula();
			subPanelContenido1.add(panelAddPelicula, BorderLayout.WEST);
		}

		JPanel subPanelContenido2 = new JPanel(new GridLayout(0, 4));

		int contadorPeliculas = 0;
		for (Pelicula pelicula : listaPeliculasRenderizada) {
			JPanel panelcentrarPelicula = crearPanelPelicula(pelicula);
			subPanelContenido2.add(panelcentrarPelicula);
			if (contadorPeliculas >= 60)
				break;
			contadorPeliculas++;
		}

		JScrollPane scrollBar = new JScrollPane(subPanelContenido2);
		panelContenido.add(scrollBar, BorderLayout.CENTER);

		add(panelContenido, BorderLayout.CENTER);

		buscador.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					List<Pelicula> listaFiltrada = listaPeliculas.stream().filter(
							pelicula -> pelicula.getTitulo().toLowerCase().contains(buscador.getText().toLowerCase()))
							.toList();
					listaPeliculasRenderizada = new ArrayList<Pelicula>(listaFiltrada);

					// Llamar a recargar el panel
					recargarPanelContenido(subPanelContenido2);
				}
			}
		});
		subPanelContenido1.add(buscador, BorderLayout.CENTER);
		
		ordenar.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				Object item = e.getItem();
				ordenarPeliculas(item, subPanelContenido2);
			}
		});
		
		setVisible(true);
	}

	private JPanel crearPanelPelicula(Pelicula pelicula) {
		JPanel panelCentrar = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel panelPeli = new JPanel();
		panelPeli.setLayout(new BoxLayout(panelPeli, BoxLayout.Y_AXIS));

		// Cargar imagen de la película
		ImageIcon imagenPelicula;
		if (pelicula.getFoto() != null) {
			imagenPelicula = pelicula.getFoto();
		} else {
			imagenPelicula = Utils.loadImage("noImagen.jpg", 115, 160);
		}
		
		JLabel imagenLabel = new JLabel(imagenPelicula);
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

		ImageIcon iconoAddPelicula = Utils.loadImage("add.png", 24, 24);
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

	private void recargarPanelContenido(JPanel subPanelContenido2) {
		subPanelContenido2.removeAll(); // Eliminar todos los componentes actuales.

		int contadorPeliculas = 0;
		for (Pelicula pelicula : listaPeliculasRenderizada) {
			JPanel panelCentrarPelicula = crearPanelPelicula(pelicula);
			subPanelContenido2.add(panelCentrarPelicula);
			if (contadorPeliculas >= 60)
				break;
			contadorPeliculas++;
		}

		subPanelContenido2.revalidate(); // Informar al layout que actualice la UI.
		subPanelContenido2.repaint(); // Redibujar el panel.
	}

	private void ordenarPeliculas(Object item, JPanel subPanelContenido2) {
		if (item instanceof String && item.equals("ORDENAR")) {
			listaPeliculasRenderizada = new ArrayList<>(listaPeliculas);
		} else if (item instanceof TipoPelicula tipo) {
			listaPeliculasRenderizada = new ArrayList<>(
					listaPeliculas.stream().filter(v -> v.getTipo() == tipo).toList());
		}
		recargarPanelContenido(subPanelContenido2);
	}

	public static void main(String[] args) {
		new VentanaPeliculas();
	}
}