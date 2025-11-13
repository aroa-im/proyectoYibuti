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

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import domain.Admin;
import domain.Cliente;
import domain.Seccion;
import domain.TipoPelicula;
import domain.Usuario;
import gui.components.Header;
import utils.Utils;

public class VentanaPeliculas extends JFrame {
	private static final long serialVersionUID = 1L;

	public VentanaPeliculas(Usuario usuario) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		if (usuario == null) {
			setTitle("Videoclub - No logueado");
		} else {
			setTitle("Videoclub - logueado" + usuario.getClass().toString());
		}

//		setExtendedState(MAXIMIZED_BOTH);
		setSize(1200, 800);
		setLocationRelativeTo(null);

		// Panel superior que contendrá el header
		JPanel panelSuperior = new Header(Seccion.PELICULA, usuario, this);
		add(panelSuperior, BorderLayout.NORTH);

		// Panel inferior
		JPanel panelContenido = new JPanel(new BorderLayout());

		JPanel subPanelContenido1 = new JPanel(new BorderLayout());
		panelContenido.add(subPanelContenido1, BorderLayout.NORTH);

		TipoPelicula[] array = new TipoPelicula[2];
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
				;
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
					// recargar pagina con la lista filtrada
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
		// subPanelContenido2.setBackground(Color.orange);
		for (int i = 1; i < 200; i++) {
			JPanel panelCentrarPelicula = crearPanePeliculaCentrada(i);
			subPanelContenido2.add(panelCentrarPelicula);
		}

		JScrollPane scrollBar = new JScrollPane(subPanelContenido2);
		panelContenido.add(scrollBar, BorderLayout.CENTER);

		add(panelContenido, BorderLayout.CENTER);

		setVisible(true);
	}
	
	private JPanel crearPanePeliculaCentrada(int i) {
		JPanel panelCentrarPelicula = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		JPanel panelPelicula = new JPanel();
		panelPelicula.setLayout(new BoxLayout(panelPelicula,BoxLayout.Y_AXIS));
		
		JLabel tituloLibro = new JLabel("Título "+ i);
		panelPelicula.add(tituloLibro);
		
		panelCentrarPelicula.add(panelPelicula);
		
		panelPelicula.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent e) {
				JLabel labelTitulo = (JLabel) panelPelicula.getComponent(1);
				String titulo = labelTitulo.getText();
				System.out.println(titulo);
				super.mouseClicked(e);
			}
		});
		return panelCentrarPelicula;
	}

	private JPanel createPanelAddPelicula() {
		JPanel panelAddPelicula = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, -5, 0, 5); // Margen entre componentes (icono y texto)
	    gbc.anchor = GridBagConstraints.CENTER; // Centrar verticalmente y horizontalmente

		ImageIcon iconoAddPelicula = Utils.loadImage("peliculas.png",36,36);
	    JLabel iconLabel = new JLabel(iconoAddPelicula);

	    JLabel textLabel = new JLabel("Añadir pelicula");

	    // Añadir mouse listener para el panel
	    panelAddPelicula.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
	        	System.out.println("Panel clickeado");
	            // Aquí puedes agregar la lógica que necesites
        	}
	    });

	    panelAddPelicula.add(iconLabel, gbc);
	    gbc.gridx = 1; // Segunda columna
	    panelAddPelicula.add(textLabel, gbc);
	    return panelAddPelicula;
	}

	public static void main(String[] args) {
		VentanaPeliculas ventana = new VentanaPeliculas(null);
//		VentanaPeliculas ventana2 = new VentanaPeliculas(new Cliente());
//		VentanaPeliculas ventana3 = new VentanaPeliculas(new Admin());

	}
}
