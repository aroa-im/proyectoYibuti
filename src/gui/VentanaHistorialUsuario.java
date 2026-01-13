package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import domain.Cliente;
import domain.Pelicula;
import domain.Producto;
import domain.Review;

import domain.Usuario;

public class VentanaHistorialUsuario extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private Usuario usuario;

	
	public VentanaHistorialUsuario(Usuario usuario) {
		this.usuario = usuario;

		
		if (usuario == null || !(usuario instanceof Cliente)) {
			System.out.println("MODO DISEÑO: Cargando usuario de prueba...");
			
			this.usuario = new Cliente(); 
		}

		Cliente clienteLogueado = (Cliente) this.usuario;

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(900, 600);
		setTitle("Historial de " + clienteLogueado.getNombre());
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		
		
		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(new Color(50, 50, 50));
		JLabel lblHeader = new JLabel("HISTORIAL DE " + clienteLogueado.getNombre().toUpperCase());
		lblHeader.setForeground(Color.WHITE);
		lblHeader.setFont(new Font("Arial", Font.BOLD, 20));
		headerPanel.add(lblHeader);
		add(headerPanel, BorderLayout.NORTH);
		
		
		String[] columnas = {"Producto", "Detalles del Alquiler", "Tu Opinión"};
		
		DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; 
			}
		};

		
		ArrayList<Producto> historial = clienteLogueado.getHistorial();

		if (historial != null) {
		    for (Producto p : historial) {
		        
		        ImageIcon foto = p.getFoto();
		        
		        
		        String tipoProducto = (p instanceof Pelicula) ? "Película" : "Videojuego";
		        String descripcion = "<html><b>" + p.getTitulo() + "</b><br/>" + 
		                             "<i>" + tipoProducto + "</i><br/>" + 
		                             "Precio alquiler: " + p.getPrecio() + "€</html>";
		        
		        
		        
		        String estadoReview = "Sin valorar";
		        ArrayList<Review> misReviews = clienteLogueado.getListaReviews();
		        
		        
		        if (misReviews != null) {
		            for (Review r : misReviews) {
		                
		                if (r != null && r.getProducto() != null && r.getProducto().getId() == p.getId()) {
		                    estadoReview = "Valorado: " + r.getRating() + "/10";
		                    break;
		                }
		            }
		        }
		        
		        
		        
		        Object[] fila = { foto, descripcion, estadoReview };
		        modelo.addRow(fila);
		    }
		}
		
		JTable tabla = new JTable(modelo);
		tabla.setRowHeight(120);
		tabla.setFont(new Font("Arial", Font.PLAIN, 14));
		tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
		
		tabla.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				JLabel label = new JLabel();
				label.setHorizontalAlignment(JLabel.CENTER);
				if (value != null && value instanceof ImageIcon) {

					label.setIcon((ImageIcon) value);
				} else {
					label.setText("Sin Foto");
				}
				if (isSelected) label.setBackground(table.getSelectionBackground());
				return label;
			}
		});

		
		tabla.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); 
				return c;
			}
		});

		
		JScrollPane scroll = new JScrollPane(tabla);
		add(scroll, BorderLayout.CENTER);
		
		
		setVisible(true);
	}
	

}