package gui;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.util.ArrayList;

import domain.Pelicula;
import domain.Usuario;
import domain.Seccion;
import io.InputUtils;
import main.main;
import gui.components.Header;
import utils.Utils;

public class VentanaPeliculas extends JFrame {

	private static final long serialVersionUID = 1L;
	private Usuario usuario = main.getUsuario();
	private ArrayList<Pelicula> peliculas;

	public VentanaPeliculas() {

		this.peliculas = InputUtils.cargarPeliculas();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Videoclub - Películas");
		setSize(1200, 800);
		setLocationRelativeTo(null);

		JPanel panelSuperior = new Header(Seccion.PELICULA, usuario, this);
		add(panelSuperior, BorderLayout.NORTH);

		JPanel panelContenido = new JPanel(new BorderLayout());
		JPanel panelGrid = new JPanel(new GridLayout(0, 4));

		for (Pelicula pelicula : peliculas) {
			JPanel panel = crearPanePelicula(pelicula);
			panelGrid.add(panel);
		}

		JScrollPane scroll = new JScrollPane(panelGrid);
		panelContenido.add(scroll, BorderLayout.CENTER);

		add(panelContenido);
		setVisible(true);
	}

	private JPanel crearPanePelicula(Pelicula pelicula) {

		JPanel panelCentrar = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel panelPeli = new JPanel();
		panelPeli.setLayout(new BoxLayout(panelPeli, BoxLayout.Y_AXIS));

		ImageIcon imagen = Utils.loadImage("peliculas/" + pelicula.getId() + ".jpg", 115, 160);
		if (imagen == null)
			imagen = Utils.loadImage("noImagen.jpg", 115, 160);

		JLabel imagenLabel = new JLabel(imagen);
		JLabel titulo = new JLabel(pelicula.getTitulo());

		panelPeli.add(imagenLabel);
		panelPeli.add(titulo);

		panelCentrar.add(panelPeli);

		panelPeli.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				new VentanaInformacionRecurso(pelicula);
			}
		});

		return panelCentrar;
	}

	public static void main(String[] args) {
		new VentanaPeliculas();
	}
}