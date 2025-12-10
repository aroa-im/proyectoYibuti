package gui;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.util.ArrayList;

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
	private ArrayList<Videojuego> videojuegos;

	public VentanaVideojuegos() {

		this.videojuegos = InputUtils.cargarVideojuegos();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Videoclub - Videojuegos");
		setSize(1200, 800);
		setLocationRelativeTo(null);

		JPanel panelSuperior = new Header(Seccion.VIDEOJUEGO, usuario, this);
		add(panelSuperior, BorderLayout.NORTH);

		JPanel panelContenido = new JPanel(new BorderLayout());
		JPanel panelGrid = new JPanel(new GridLayout(0, 4));

		for (Videojuego videojuego : videojuegos) {
			JPanel panel = crearPaneVideojuego(videojuego);
			panelGrid.add(panel);
		}

		JScrollPane scroll = new JScrollPane(panelGrid);
		panelContenido.add(scroll, BorderLayout.CENTER);

		add(panelContenido);
		setVisible(true);
	}

	private JPanel crearPaneVideojuego(Videojuego videojuego) {

		JPanel panelCentrar = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel panelJuego = new JPanel();
		panelJuego.setLayout(new BoxLayout(panelJuego, BoxLayout.Y_AXIS));


		ImageIcon imagen = Utils.loadImage("videojuegos/" + videojuego.getId() + ".jpg", 115, 160);
		if (imagen == null)
			imagen = Utils.loadImage("noImagen.jpg", 115, 160);

		JLabel imagenLabel = new JLabel(imagen);
		JLabel titulo = new JLabel(videojuego.getTitulo());

		panelJuego.add(imagenLabel);
		panelJuego.add(titulo);

		panelCentrar.add(panelJuego);

		panelJuego.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				new VentanaInformacionRecurso(videojuego);
			}
		});

		return panelCentrar;
	}

	public static void main(String[] args) {
		new VentanaVideojuegos();
	}
}