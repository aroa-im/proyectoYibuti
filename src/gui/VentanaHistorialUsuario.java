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
import domain.TipoPelicula;
import domain.GeneroPelicula;
import domain.Usuario;
import domain.Videojuego;
import domain.GeneroVideoJuego;
import domain.TipoConsola;

// Importa aquí tus propios componentes cuando los integres
// import gui.components.Header;
// import gui.renderers.*;

public class VentanaHistorialUsuario extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private Usuario usuario;

	// Constructor modificado para recibir el usuario
	public VentanaHistorialUsuario(Usuario usuario) {
		this.usuario = usuario;

		// 1. Validación de seguridad (sin return silencioso)
		if (usuario == null || !(usuario instanceof Cliente)) {
			System.out.println("MODO DISEÑO: Cargando usuario de prueba...");
			// Esto es solo para que no falle si intentas abrir la ventana sin login desde el main de pruebas
			this.usuario = new Cliente(); 
		}

		Cliente clienteLogueado = (Cliente) this.usuario;

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(900, 600);
		setTitle("Historial de " + clienteLogueado.getNombre());
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		
		// 2. HEADER (Sustituye esto por tu new Header(...) cuando integres)
		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(new Color(50, 50, 50));
		JLabel lblHeader = new JLabel("HISTORIAL DE " + clienteLogueado.getNombre().toUpperCase());
		lblHeader.setForeground(Color.WHITE);
		lblHeader.setFont(new Font("Arial", Font.BOLD, 20));
		headerPanel.add(lblHeader);
		add(headerPanel, BorderLayout.NORTH);
		
		// 3. MODELO DE TABLA
		// Columnas: Carátula (Imagen) | Información (Titulo/Sinopsis) | Estado Review (Texto/Boton)
		String[] columnas = {"Producto", "Detalles del Alquiler", "Tu Opinión"};
		
		DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
			// Hacemos que las celdas no sean editables (salvo que quieras botones funcionales)
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; 
			}
		};

		// 4. LLENADO DE DATOS (Usando el historial del Cliente)
		ArrayList<Producto> historial = clienteLogueado.getHistorial();

		if (historial != null) {
		    for (Producto p : historial) {
		        // A. Obtenemos la imagen
		        ImageIcon foto = p.getFoto();
		        
		        // B. Construimos la descripción
		        String tipoProducto = (p instanceof Pelicula) ? "Película" : "Videojuego";
		        String descripcion = "<html><b>" + p.getTitulo() + "</b><br/>" + 
		                             "<i>" + tipoProducto + "</i><br/>" + 
		                             "Precio alquiler: " + p.getPrecio() + "€</html>";
		        
		        // --- AQUÍ EMPIEZA LA SOLUCIÓN 1 ---
		        // Borra lo que tenías antes sobre las reviews y pon este bloque:
		        
		        String estadoReview = "Sin valorar";
		        ArrayList<Review> misReviews = clienteLogueado.getListaReviews();
		        
		        // El "if (misReviews != null)" es la clave para que no falle
		        if (misReviews != null) {
		            for (Review r : misReviews) {
		                // Comprobamos que la review y el producto no sean nulos para evitar otro crash
		                if (r != null && r.getProducto() != null && r.getProducto().getId() == p.getId()) {
		                    estadoReview = "Valorado: " + r.getRating() + "/10";
		                    break;
		                }
		            }
		        }
		        // --- AQUÍ TERMINA LA SOLUCIÓN 1 ---
		        
		        // Añadimos la fila
		        Object[] fila = { foto, descripcion, estadoReview };
		        modelo.addRow(fila);
		    }
		}
		// 5. CONFIGURACIÓN DE LA JTABLE
		JTable tabla = new JTable(modelo);
		tabla.setRowHeight(120); // Altura suficiente para la carátula
		tabla.setFont(new Font("Arial", Font.PLAIN, 14));
		tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
		
		// Renderizador de Imágenes para la columna 0
		tabla.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				JLabel label = new JLabel();
				label.setHorizontalAlignment(JLabel.CENTER);
				if (value != null && value instanceof ImageIcon) {
					// Opcional: Redimensionar imagen si es muy grande
					label.setIcon((ImageIcon) value);
				} else {
					label.setText("Sin Foto");
				}
				if (isSelected) label.setBackground(table.getSelectionBackground());
				return label;
			}
		});

		// Renderizador HTML para la columna 1 (Texto) y 2
		tabla.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				// Usamos el render por defecto pero permitimos HTML
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // Un poco de margen
				return c;
			}
		});

		// Añadimos scroll
		JScrollPane scroll = new JScrollPane(tabla);
		add(scroll, BorderLayout.CENTER);
		
		// 6. IMPRESCINDIBLE PARA VER LA VENTANA
		setVisible(true);
	}
	
	// MAIN PARA PROBAR (SIMULACIÓN)
	public static void main(String[] args) {
		// 1. Crear Productos Mock
		Pelicula p1 = new Pelicula();
		p1.setId(1);
		p1.setTitulo("El Padrino");
		p1.setPrecio(3.5);
		p1.setDirector("Coppola");
		// p1.setFoto(new ImageIcon("ruta/a/tu/imagen.jpg")); // Descomenta si tienes imagen
		
		Videojuego v1 = new Videojuego();
		v1.setId(2);
		v1.setTitulo("Super Mario");
		v1.setPrecio(5.0);
		v1.setGenero(GeneroVideoJuego.ACCION);
		
		// 2. Crear Historial y Reviews
		ArrayList<Producto> historial = new ArrayList<>();
		historial.add(p1);
		historial.add(v1);
		
		ArrayList<Review> reviews = new ArrayList<>();
		Review r1 = new Review(1L, p1, null, "Obra maestra", 10);
		reviews.add(r1);
		
		// 3. Crear Cliente con datos
		Cliente clientePrueba = new Cliente("12345678Z", "Usuario Test", "test@email.com", "1234", historial, reviews, 0);
		// Asignamos el cliente a la review para cerrar el círculo (opcional)
		r1.setCliente(clientePrueba); 

		// 4. Lanzar ventana
		new VentanaHistorialUsuario(clientePrueba);
	}
}