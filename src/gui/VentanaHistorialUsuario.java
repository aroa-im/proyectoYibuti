package gui;

import gui.components.Header;
import gui.renderers.*;
import domain.Cliente;
import domain.Seccion;
import domain.Usuario;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class VentanaHistorialUsuario extends JFrame{
	
	private static final long serialVersionUID = 1L;
	//private Usuario usuario = main.getUsuario();
	
	public VentanaHistorialUsuario(Usuario usuario) {
		if (!(usuario instanceof Cliente)) {
			return ;
		}
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(640,480);
		setTitle("Videoclub - Historial");
		setLocationRelativeTo(null);
		
		//panel arriba
		JPanel header = new Header(Seccion.PELICULA, usuario, this);
		add(header,BorderLayout.NORTH);
		
		//Panel contenedor
		String [][] tablaHistorial = new String [200][3];
				
		for (int i = 0; i < 200; i++) {
			int numPelicula = i+1;
			String[] pelicula = {numPelicula + ".png" , "Descripcion pelicula " + numPelicula, "Review pelicula " + numPelicula};
			tablaHistorial[i] = pelicula;
		};
		
		//String[] datos = {"Libro","Descripcion","review"};
		DefaultTableModel modeloTablaHistorial = new DefaultTableModel();
		modeloTablaHistorial.addColumn("Pelicula");
		modeloTablaHistorial.addColumn("Descripcion");
		modeloTablaHistorial.addColumn("review");
		
		for (int j = 0; j < tablaHistorial.length; j++) {
			modeloTablaHistorial.addRow(tablaHistorial[j]);
		}
		
		JTable historial = new JTable(modeloTablaHistorial);
		historial.setRowHeight(76);
		historial.setRowSelectionAllowed(false);
		JScrollPane scrollPane = new JScrollPane(historial);
		add(scrollPane);
		
		historial.getColumnModel().getColumn(0).setCellRenderer(new ImageCellRenderer());
		historial.getColumnModel().getColumn(0).setCellEditor(new ImageCellEditor());
		historial.getColumnModel().getColumn(2).setCellRenderer(new ButtonCellRenderer());
		historial.getColumnModel().getColumn(2).setCellEditor(new ButtonCellEditor());
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new VentanaHistorialUsuario(new Cliente());
	}

}
