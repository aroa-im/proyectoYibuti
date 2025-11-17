package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import domain.Admin;
import domain.Cliente;
import domain.Usuario;
import domain.UsuarioSimulacion;

public class VentanaAdministracionUsuarios extends JFrame {

    private static final long serialVersionUID = 1L;
    private Usuario adminActual;
    private JFrame ventanaPrevia;
    
    public VentanaAdministracionUsuarios(JFrame ventanaPrevia, Usuario adminActual) {
        this.ventanaPrevia = ventanaPrevia;
        this.adminActual = adminActual;
        
        // Verificar que sea admin
        if (!(adminActual instanceof Admin)) {
            JOptionPane.showMessageDialog(null, 
                "No tienes permisos de administrador", 
                "Acceso denegado", 
                JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setTitle(adminActual.getNombre() + ": Administración de usuarios");
        setLocationRelativeTo(null);
        
        JPanel labelPanel = new JPanel();
        JLabel titleLabel = new JLabel("Administrar Usuarios");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        labelPanel.add(titleLabel);
        
        // Obtener usuarios de la simulación
        ArrayList<Usuario> usuarios = UsuarioSimulacion.obtenerTodosLosUsuarios();
        
        String[][] tablaUsuarios = new String[usuarios.size()][4];
                
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuarioIterado = usuarios.get(i);
            String amonestaciones = "";
            String tipoUsuario = "";
            
            if (usuarioIterado instanceof Cliente) {
                amonestaciones = Integer.toString(((Cliente) usuarioIterado).getAmonestaciones());
                tipoUsuario = "CLIENTE";
            } else {
                amonestaciones = "N/A";
                tipoUsuario = "ADMIN";
            }
            
            String[] usuarioString = {
                usuarioIterado.getDni(), 
                usuarioIterado.getNombre(), 
                amonestaciones, 
                tipoUsuario
            };
            tablaUsuarios[i] = usuarioString;
        }
        
        DefaultTableModel modeloTablaHistorial = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        modeloTablaHistorial.addColumn("DNI");
        modeloTablaHistorial.addColumn("Nombre");
//        modeloTablaHistorial.addColumn("Reseñas");
        modeloTablaHistorial.addColumn("Tipo de usuario");
        
        for (int j = 0; j < tablaUsuarios.length; j++) {
            modeloTablaHistorial.addRow(tablaUsuarios[j]);
        }
        
        JTable historial = new JTable(modeloTablaHistorial);
        historial.setRowHeight(30);
        historial.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(historial);
        
        JPanel buttonPanel = new JPanel();
        JButton cambiarTipoButton = new JButton("Cambiar tipo");
        JButton eliminarUsuarioButton = new JButton("Eliminar usuario");
        JButton volverButton = new JButton("Volver");
        
        buttonPanel.add(cambiarTipoButton);
        buttonPanel.add(eliminarUsuarioButton);
        buttonPanel.add(volverButton);
        
  
        
        // Acción: Cambiar tipo de usuario
        cambiarTipoButton.addActionListener(e -> {
            if (historial.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, 
                    "Por favor, selecciona un usuario", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            } else {
                Usuario usuarioSeleccionado = usuarios.get(historial.getSelectedRow());
                
                // No permitir cambiar el tipo del propio admin
                if (usuarioSeleccionado.getDni().equals(adminActual.getDni())) {
                    JOptionPane.showMessageDialog(null, 
                        "No puedes cambiar tu propio tipo de usuario", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (usuarioSeleccionado instanceof Admin) {
                    int opcion = JOptionPane.showConfirmDialog(
                        null, 
                        "¿Seguro que quieres convertir este administrador en cliente?", 
                        "Cambiar tipo de " + usuarioSeleccionado.getNombre(), 
                        JOptionPane.YES_NO_OPTION
                    );
                    
                    if (opcion == JOptionPane.YES_OPTION) {
                        UsuarioSimulacion.cambiarAdminACliente(usuarioSeleccionado.getDni());
                        JOptionPane.showMessageDialog(null, 
                            "Usuario puesto a Cliente correctamente", 
                            "Éxito", 
                            JOptionPane.INFORMATION_MESSAGE);
                        
                        // Recargar ventana
                        dispose();
                        new VentanaAdministracionUsuarios(ventanaPrevia, adminActual);
                    }
                } else {
                    int opcion = JOptionPane.showConfirmDialog(
                        null, 
                        "¿Seguro que quieres convertir este cliente en administrador?", 
                        "Cambiar tipo de " + usuarioSeleccionado.getNombre(), 
                        JOptionPane.YES_NO_OPTION
                    );
                    
                    if (opcion == JOptionPane.YES_OPTION) {
                        UsuarioSimulacion.cambiarClienteAAdmin(usuarioSeleccionado.getDni());
                        JOptionPane.showMessageDialog(null, 
                            "Usuario convertido a Admin correctamente", 
                            "Éxito", 
                            JOptionPane.INFORMATION_MESSAGE);
                        
                        // Recargar ventana
                        dispose();
                        new VentanaAdministracionUsuarios(ventanaPrevia, adminActual);
                    }
                }
            }
        });
        
        
        eliminarUsuarioButton.addActionListener(e -> {
            if (historial.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, 
                    "Por favor, selecciona un usuario", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            } else {
                Usuario usuarioSeleccionado = usuarios.get(historial.getSelectedRow());
                
                // No permitir eliminar al propio admin
                if (usuarioSeleccionado.getDni().equals(adminActual.getDni())) {
                    JOptionPane.showMessageDialog(null, 
                        "No puedes eliminarte a ti mismo", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                int opcion = JOptionPane.showConfirmDialog(
                    null, 
                    "¿Seguro que quieres eliminar a " + usuarioSeleccionado.getNombre() + "?", 
                    "Confirmar eliminación", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );
                
                if (opcion == JOptionPane.YES_OPTION) {
                    UsuarioSimulacion.eliminarUsuario(usuarioSeleccionado.getDni());
                    JOptionPane.showMessageDialog(null, 
                        "Usuario eliminado correctamente", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Recargar ventana
                    dispose();
                    new VentanaAdministracionUsuarios(ventanaPrevia, adminActual);
                }
            }
        });
        
        // Acción: Volver
        volverButton.addActionListener(e -> {
            if (ventanaPrevia != null) {
                ventanaPrevia.setVisible(true);
            }
            dispose();
        });
        
        add(labelPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
    
    public static void main(String[] args) {
        // Para pruebas
        Admin admin = new Admin("12345678A", "Admin Test", "admin@test.com", "1234", null, null);
        new VentanaAdministracionUsuarios(null, admin);
    }
}