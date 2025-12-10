package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import db.UsuarioDTO;
import main.main;
import domain.Admin;
import domain.Cliente;
import domain.LogAccion;
import domain.Usuario;

public class VentanaAdministracionUsuarios extends JFrame {

	private static final long serialVersionUID = 1L;
    private Usuario usuario= main.getUsuario();
    
    public VentanaAdministracionUsuarios() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(640, 500);
        setTitle(usuario.getNombre() + ": Administración de usuarios");
        setLocationRelativeTo(null);
        
        JPanel labelPanel = new JPanel();
        JLabel titleLabel = new JLabel("Administrar Usuarios");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        labelPanel.add(titleLabel);
        
        
        ArrayList<Usuario> usuarios = main.getUsuarioDAO().getUsuarios();
        
        String[][] tablaUsuarios = new String[usuarios.size()][4];
                
        for (int i = 0; i < usuarios.size(); i++) {
			
			Usuario usuarioIterado = usuarios.get(i);
			String amonestaciones = "";
			String tipoUsuario = "";
			if (usuarioIterado instanceof Cliente) {
				amonestaciones = Integer.toString(((Cliente) usuarioIterado).getAmonestaciones());
				tipoUsuario = "CLIENTE";
			} else {
				amonestaciones = "Es admin";
				tipoUsuario = "ADMIN";
			}
			
			String[] usuarioString = {usuarioIterado.getDni(), usuarioIterado.getNombre(), amonestaciones, tipoUsuario};
			tablaUsuarios[i] = usuarioString;
		};
        
		DefaultTableModel modeloTablaHistorial = new DefaultTableModel();
		modeloTablaHistorial.addColumn("DNI");
		modeloTablaHistorial.addColumn("Nombre");
		modeloTablaHistorial.addColumn("Amonestaciones");
		modeloTablaHistorial.addColumn("Tipo de usuario");
		
		for (int j = 0; j < tablaUsuarios.length; j++) {
			modeloTablaHistorial.addRow(tablaUsuarios[j]);
		}
		
        
        JTable historial = new JTable(modeloTablaHistorial);
        historial.setRowHeight(30);
        historial.setRowSelectionAllowed(false);
        JScrollPane scrollPane = new JScrollPane(historial);
        
        JPanel buttonPanel = new JPanel();
        JButton amonestacionesButton = new JButton("Editar amonestaciones");
//		JButton cambiarTipoButton = new JButton("Cambiar tipo");
		JButton eliminarUsuarioButton = new JButton("Eliminar usuario");
        
        buttonPanel.add(amonestacionesButton);
//        buttonPanel.add(cambiarTipoButton);
        buttonPanel.add(eliminarUsuarioButton);

        
  
        amonestacionesButton.addActionListener(e -> {
			if (historial.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor, selecciona un usuario/a", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				Usuario usuarioSeleccionado = usuarios.get(historial.getSelectedRow());
				if (usuarioSeleccionado instanceof Admin) {
					JOptionPane.showMessageDialog(null, "Este usuario es un administrador", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					String nuevoValor = (String) JOptionPane.showInputDialog(null, "Introduce el nuevo valor de amonestaciones:", "Editar amonestaciones de " + usuarioSeleccionado.getNombre(), JOptionPane.QUESTION_MESSAGE, null, null, ((Cliente) usuarioSeleccionado).getAmonestaciones());
					if(Integer.parseInt(nuevoValor) < 0) {
						JOptionPane.showMessageDialog(null, "Error, las amonestaciones tienen que ser positivas", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					((Cliente) usuarios.get(historial.getSelectedRow())).setAmonestaciones(Integer.parseInt(nuevoValor));
					UsuarioDTO usuarioDTO = new UsuarioDTO();
					usuarioDTO.setDni(usuarioSeleccionado.getDni());
					if (main.getUsuarioDAO().updateAmonestaciones(usuarioDTO, Integer.parseInt(nuevoValor))) {
						main.getUsuarioDAO().addLogAccion(new LogAccion(0, LocalDateTime.now(), "Amonestaciones de DNI: " + usuarioSeleccionado.getDni() + " modificadas a " + nuevoValor, usuario.getDni()));
						JOptionPane.showMessageDialog(null, "Amonestaciones cambiadas correctamente", "Amonestaciones cambiadas", JOptionPane.INFORMATION_MESSAGE);
						dispose();
						new VentanaAdministracionUsuarios();
					} else {
						JOptionPane.showMessageDialog(null, "Error al cambiar amonestaciones", "Error", JOptionPane.ERROR_MESSAGE);
						dispose();
						new VentanaAdministracionUsuarios();
					}
				}
			}
		});
		
//        // Acción: Cambiar tipo de usuario
//        cambiarTipoButton.addActionListener(e -> {
//			if (historial.getSelectedRow() == -1) {
//				JOptionPane.showMessageDialog(null, "Por favor, selecciona un usuario/a", "Error", JOptionPane.ERROR_MESSAGE);
//			} else {
//				Usuario usuarioSeleccionado = usuarios.get(historial.getSelectedRow());
//				if (usuarioSeleccionado instanceof Admin) {
//					int opcion = JOptionPane.showConfirmDialog(null, "Seguro que quieres descender este usuario a Cliente?", "Cambiar tipo de Usuario de " + usuarioSeleccionado.getNombre(), JOptionPane.YES_NO_OPTION);
//					if (opcion == 0) {
//						if (main.getUsuarioDAO().deleteUsuario(usuarioSeleccionado.getDni())) {
//							main.getUsuarioDAO().addLogAccion(new LogAccion(0, LocalDateTime.now(), "Usuario eliminado con DNI: " + usuarioSeleccionado.getDni(), usuario.getDni()));
//							Cliente nuevoCliente = new Cliente(usuarioSeleccionado.getDni(), usuarioSeleccionado.getNombre(), usuarioSeleccionado.getEmail(), usuarioSeleccionado.getContrasena(), new ArrayList<>(),new ArrayList<>(), 0);
//							if (main.getUsuarioDAO().addUsuario(nuevoCliente)) {
//								main.getUsuarioDAO().addLogAccion(new LogAccion(0, LocalDateTime.now(), "Usuario añadido con DNI: " + usuarioSeleccionado.getDni(), usuario.getDni()));
//								JOptionPane.showMessageDialog(null, "Usuario descendido a Cliente", "Tipo modificado", JOptionPane.INFORMATION_MESSAGE);
//							} else {
//								JOptionPane.showMessageDialog(null, "Error al cambiar el tipo", "Error", JOptionPane.ERROR_MESSAGE);
//							}
//						} else {
//							JOptionPane.showMessageDialog(null, "Error al cambiar el tipo", "Error", JOptionPane.ERROR_MESSAGE);
//						}
//					}
//				} else {
//					int opcion = JOptionPane.showConfirmDialog(null, "Seguro que quieres ascender este usuario a Admin?",  "Cambiar tipo de Usuario de " + usuarioSeleccionado.getNombre(), JOptionPane.YES_NO_OPTION);
//					if (opcion == 0) {
//						if (main.getUsuarioDAO().deleteUsuario(usuarioSeleccionado.getDni())) {
//							main.getUsuarioDAO().addLogAccion(new LogAccion(0, LocalDateTime.now(), "Usuario eliminado con DNI: " + usuarioSeleccionado.getDni(), usuario.getDni()));
//							Admin nuevoAdmin = new Admin(usuarioSeleccionado.getDni(), usuarioSeleccionado.getNombre(), usuarioSeleccionado.getEmail(), usuarioSeleccionado.getContrasena()	, new ArrayList<>(), new ArrayList<>());
//							if (main.getUsuarioDAO().addUsuario(nuevoAdmin)) {
//								main.getUsuarioDAO().addLogAccion(new LogAccion(0, LocalDateTime.now(), "Usuario añadido con DNI: " + usuarioSeleccionado.getDni(), usuario.getDni()));
//								JOptionPane.showMessageDialog(null, "Usuario ascendido a Admin", "Tipo modificado", JOptionPane.INFORMATION_MESSAGE);
//							} else {
//								JOptionPane.showMessageDialog(null, "Error cambiar el tipo", "Error", JOptionPane.ERROR_MESSAGE);
//							}
//						} else {
//							JOptionPane.showMessageDialog(null, "Error cambiar el tipo", "Error", JOptionPane.ERROR_MESSAGE);
//						}
//					}
//				}
//			}
//		});
        
        
        eliminarUsuarioButton.addActionListener(e -> {
			if (historial.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor, selecciona un usuario/a", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				Usuario usuarioSeleccionado = usuarios.get(historial.getSelectedRow());
				int opcion = JOptionPane.showConfirmDialog(null, "Seguro que quieres eliminar este usuario?",  "Eliminar a " + usuarioSeleccionado.getNombre(), JOptionPane.YES_NO_OPTION);
				if (opcion == 0) {
					if (main.getUsuarioDAO().deleteUsuario(usuarioSeleccionado.getDni())) {
						JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente", "Usuario eliminado", JOptionPane.INFORMATION_MESSAGE);
						main.getUsuarioDAO().addLogAccion(new LogAccion(0, LocalDateTime.now(), "Usuario eliminado con DNI: " + usuarioSeleccionado.getDni(), usuario.getDni()));
					} else {
						JOptionPane.showMessageDialog(null, "Error al eliminar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
        

        
        add(labelPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
    
    public static void main(String[] args) {
        new VentanaAdministracionUsuarios();
    }
}