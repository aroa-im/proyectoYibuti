package gui.renderers;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import utils.Utils;

public class ImageCellRenderer extends JLabel implements TableCellRenderer {
    
    private static final long serialVersionUID = 1L;
    
    public ImageCellRenderer() {
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        if (value instanceof ImageIcon) {
            // Si el valor ya es un ImageIcon, usarlo directamente
            setIcon((ImageIcon) value);
            setText(null);
        } else if (value instanceof String) {
            // Si es un String con el nombre de archivo, cargarlo desde resources
            String imageName = (String) value;
            try {
                // Cargar la imagen con dimensiones apropiadas para la tabla
                ImageIcon icon = Utils.loadImage("peliculas/" + imageName, 60, 70);
                if (icon != null) {
                    setIcon(icon);
                    setText(null);
                } else {
                    // Imagen no encontrada
                    setIcon(null);
                    setText("Sin imagen");
                }
            } catch (Exception e) {
                setIcon(null);
                setText("Error");
                e.printStackTrace();
            }
        } else {
            // Valor nulo o tipo inesperado
            setIcon(null);
            setText("N/A");
        }
        
        // Colores para selección
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }
        
        return this;
    }
}
