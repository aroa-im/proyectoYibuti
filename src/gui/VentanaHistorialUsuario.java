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

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setTitle("Historial de " + clienteLogueado.getNombre());
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel de cabecera
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(50, 50, 50));
        JLabel lblHeader = new JLabel("HISTORIAL DE " + clienteLogueado.getNombre().toUpperCase());
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(lblHeader);
        add(headerPanel, BorderLayout.NORTH);

        String[] columnas = { "Imagen", "Producto", "Review" };

        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Obtener historial del cliente
        ArrayList<Producto> historial = clienteLogueado.getHistorial();

        if (historial != null && !historial.isEmpty()) {
            for (Producto p : historial) {
                ImageIcon foto = p.getFoto();
                
                String tipoProducto = (p instanceof Pelicula) ? "Película" : "Videojuego";
                String descripcion = "<html><b>" + p.getTitulo() + "</b><br/>" 
                                   + "<i>" + tipoProducto + "</i><br/>"
                                   + "Precio alquiler: " + p.getPrecio() + "€</html>";

                String estadoReview = "Sin valorar";
                ArrayList<Review> misReviews = clienteLogueado.getListaReviews();

                if (misReviews != null) {
                    for (Review r : misReviews) {
                        if (r != null && r.getProducto() != null && r.getProducto().getId() == p.getId()) {
                            estadoReview = "<html><b>Valorado:</b> " + r.getRating() + "/10<br/>"
                                         + r.getComentario() + "</html>";
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
        tabla.getTableHeader().setReorderingAllowed(false);


        tabla.getColumnModel().getColumn(0).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(350);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(400);


        tabla.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                
                JLabel label = new JLabel();
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setOpaque(true);

                label.setBackground(new Color(255, 200, 200));
                
                if (value instanceof ImageIcon) {
                    label.setIcon((ImageIcon) value);
                } else {
                    label.setText("Sin imagen");
                }
                
                if (isSelected) {
                    label.setBackground(table.getSelectionBackground());
                }
                
                return label;
            }
        });


        tabla.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, 
                        isSelected, hasFocus, row, column);
                
                label.setOpaque(true);
                label.setVerticalAlignment(JLabel.TOP);
                label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                if (!isSelected) {
                    label.setBackground(new Color(255, 255, 200));
                }
                
                return label;
            }
        });

        tabla.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, 
                        isSelected, hasFocus, row, column);
                
                label.setOpaque(true);
                label.setVerticalAlignment(JLabel.TOP);
                label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                
                // Color de fondo verde clarito
                if (!isSelected) {
                    label.setBackground(new Color(200, 255, 200));
                }
                
                return label;
            }
        });


        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        setVisible(true);
    }
}