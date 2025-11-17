package gui;

import gui.components.Header;
import gui.renderers.*;
import domain.Cliente;
import domain.Pelicula;
import domain.Seccion;
import domain.Usuario;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class VentanaHistorialUsuario extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public VentanaHistorialUsuario(Usuario usuario) {
		if (!(usuario instanceof Cliente)) {
			return;
		}
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(640, 480);
		setTitle("Videoclub - Historial");
		setLocationRelativeTo(null);
		
		// Panel header
		JPanel header = new Header(Seccion.PELICULA, usuario, this);
		add(header, BorderLayout.NORTH);
		

		Cliente cliente = (Cliente) usuario;
		ArrayList<Pelicula> historialPeliculas = obtenerHistorialPeliculas(cliente);
		
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
		
		// Crear tabla
		JTable historial = new JTable(modeloTablaHistorial);
		historial.setRowHeight(76);
		historial.setRowSelectionAllowed(false);
		JScrollPane scrollPane = new JScrollPane(historial);
		add(scrollPane, BorderLayout.CENTER);
		
		// Configurar renderers y editores
		historial.getColumnModel().getColumn(0).setCellRenderer(new ImageCellRenderer());
		historial.getColumnModel().getColumn(0).setCellEditor(new ImageCellEditor());
		historial.getColumnModel().getColumn(2).setCellRenderer(new ButtonCellRenderer());
		historial.getColumnModel().getColumn(2).setCellEditor(new ButtonCellEditor());
		
		setVisible(true);
	}
	

	private ArrayList<Pelicula> obtenerHistorialPeliculas(Cliente cliente) {

		
		ArrayList<Pelicula> historial = new ArrayList<>();

		return historial;
	}
	
	public static void main(String[] args) {
		new VentanaHistorialUsuario(new Cliente());
	}
}