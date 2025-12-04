package gui;

import gui.components.Header;

import main.main;
import domain.Cliente;
import domain.Pelicula;
import domain.Seccion;
import domain.Usuario;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.table.DefaultTableModel;

public class VentanaHistorialUsuario extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private Usuario usuario = main.getUsuario();
	
	public VentanaHistorialUsuario() {
		if (!(usuario instanceof Cliente)) {
			return;
		}
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(640, 480);
		setTitle("Videoclub - Historial");
		setLocationRelativeTo(null);
		
		addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
				dispose();
            }
		});
		// Panel header
		JPanel header = new Header(Seccion.PELICULA, usuario, this);
		add(header, BorderLayout.NORTH);
		

		
	ArrayList<Pelicula> historialPeliculas = obtenerHistorialPeliculas();
		
		//  modelo de tabla
		DefaultTableModel modeloTablaHistorial = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 2;
			}
		};
		
		modeloTablaHistorial.addColumn("Pelicula");
		modeloTablaHistorial.addColumn("Descripcion");
		modeloTablaHistorial.addColumn("Review");
		

		for (Pelicula pelicula : historialPeliculas) {
			Object[] fila = new Object[3];

			fila[0] = pelicula.getFoto();

			
			fila[1] = pelicula.getSinopsis();
			fila[2] = "Review " + pelicula.getTitulo();
			modeloTablaHistorial.addRow(fila);
		}
		
		
		

	}
	

	private ArrayList<Pelicula> obtenerHistorialPeliculas() {

		
		ArrayList<Pelicula> historial = new ArrayList<>();

		return historial;
	}
	
	public static void main(String[] args) {
		new VentanaHistorialUsuario();
	}
}
