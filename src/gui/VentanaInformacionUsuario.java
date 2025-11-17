package gui;

import domain.Admin;
import domain.Cliente;
import domain.Usuario;
import domain.UsuarioSimulacion;
import main.main;
import utils.Utils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;


public class VentanaInformacionUsuario extends JFrame {

    private static final long serialVersionUID = 5069329725320750186L;
    private static boolean editarNombre = false;
    private static int contadorClicksNombre = 0;
    private static boolean editarEmail = false;
    private static int contadorClicksEmail = 0;
    private static boolean editarPassword = false;
    private static int contadorClicksPassword = 0;
    
    private Usuario usuario;
    private JFrame ventanaPrevia;

    public VentanaInformacionUsuario(JFrame ventanaPrevia, Usuario usuario) {
        this.ventanaPrevia = ventanaPrevia;
        this.usuario = usuario;
        
        setTitle(usuario.getNombre() + ": Información");
        setSize(640, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        JPanel contenido = new JPanel();
        contenido.setLayout(new GridLayout(0, 3));
        JPanel l1 = new JPanel();
        JPanel l2 = new JPanel(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(5, 0, 5, 0);

        JPanel l3 = new JPanel();
        contenido.add(l1);
        contenido.add(l2);
        contenido.add(l3);
        add(contenido);
        
        JLabel titulo = new JLabel("Usuario");
        Font fuenteTitulo = new Font("Arial", Font.PLAIN, 32);
        titulo.setFont(fuenteTitulo);
        
        JPanel panelNombre = getPanel("Nombre", usuario.getNombre());
        JPanel panelEmail = getPanel("Email", usuario.getEmail());
        JPanel panelPassword = getPanel("Contraseña", "********");
        
        JButton botonDinamico = getBotonDinamico();
        JButton botonCerrarSesion = new JButton("Cerrar Sesión");
        botonCerrarSesion.setBackground(Color.RED);
        botonCerrarSesion.setForeground(Color.WHITE);
        
        l2.add(titulo, gbc);
        l2.add(panelNombre, gbc);
        l2.add(panelEmail, gbc);
        l2.add(panelPassword, gbc);
        l2.add(botonDinamico, gbc);
        l2.add(botonCerrarSesion, gbc);
        
        botonCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.setUsuario(null);
                
                // Volver a la ventana de portada sin usuario
                new VentanaPortada(null);
                if (ventanaPrevia != null) {
                    ventanaPrevia.dispose();
                }
                dispose();
            }
        });
        
        setVisible(true);
    }

    private JPanel getPanel(String labelText, String placeholder) {
        JPanel panelContenidoCentrado = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel panelLabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelLabel.add(label);

        JPanel panelInput = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField input = new JTextField(placeholder, 15);
        input.setEnabled(false);
        
        ImageIcon iconoEditar= Utils.loadImage("edit.png", 20, 20);
        JLabel iconLabel = new JLabel(iconoEditar); 

        
        if (labelText.equalsIgnoreCase("Nombre")) {
            iconLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    contadorClicksNombre++;
                    editarNombre = !editarNombre;
                    input.setEnabled(editarNombre);
                    
                    if (contadorClicksNombre == 2) {
                        if (!input.getText().trim().isEmpty()) {
                            // Actualizar en la simulación
                            UsuarioSimulacion.actualizarNombre(usuario.getDni(), input.getText().trim());
                            usuario.setNombre(input.getText().trim());
                            setTitle(usuario.getNombre() + ": Información");
                            JOptionPane.showMessageDialog(VentanaInformacionUsuario.this, 
                                "Nombre actualizado correctamente", "Actualización exitosa", 
                                JOptionPane.INFORMATION_MESSAGE);
                        }
                        contadorClicksNombre = 0;
                    }
                }
            });
        } else if (labelText.equalsIgnoreCase("Email")) {
            iconLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    contadorClicksEmail++;
                    editarEmail = !editarEmail;
                    input.setEnabled(editarEmail);
                    
                    if (contadorClicksEmail == 2) {
                        if (!input.getText().trim().isEmpty()) {
                            UsuarioSimulacion.actualizarEmail(usuario.getDni(), input.getText().trim());
                            usuario.setEmail(input.getText().trim());
                            JOptionPane.showMessageDialog(VentanaInformacionUsuario.this, 
                                "Email actualizado correctamente", "Actualización exitosa", 
                                JOptionPane.INFORMATION_MESSAGE);
                        }
                        contadorClicksEmail = 0;
                    }
                }
            });
        } else {
            iconLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    contadorClicksPassword++;
                    editarPassword = !editarPassword;
                    input.setEnabled(editarPassword);
                    
                    if (contadorClicksPassword == 1) {
                        input.setText(""); // Limpiar campo para nueva contraseña
                    }
                    
                    if (contadorClicksPassword == 2) {
                        if (!input.getText().trim().isEmpty()) {
                            UsuarioSimulacion.actualizarContrasena(usuario.getDni(), input.getText().trim());
                            usuario.setContrasena(input.getText().trim());
                            input.setText("********");
                            JOptionPane.showMessageDialog(VentanaInformacionUsuario.this, 
                                "Contraseña actualizada correctamente", "Actualización exitosa", 
                                JOptionPane.INFORMATION_MESSAGE);
                        }
                        contadorClicksPassword = 0;
                    }
                }
            });
        }

        panelInput.add(input);
        panelInput.add(iconLabel);

        panelContenido.add(panelLabel);
        panelContenido.add(panelInput);
        
        panelContenidoCentrado.add(panelContenido);
        
        return panelContenidoCentrado;
    }

    public JButton getBotonDinamico() {
        JButton botonDinamico = new JButton();

        if (usuario instanceof Admin) {
            botonDinamico.setText("Modificar usuarios");

            botonDinamico.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new VentanaAdministracionUsuarios(VentanaInformacionUsuario.this, usuario);
                    dispose();
                }
            });
        } 
        else {
            botonDinamico.setText("Ver historial de reseñas");
            botonDinamico.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Falta VentanaAñadirReview
                    JOptionPane.showMessageDialog(VentanaInformacionUsuario.this, 
                        "Funcionalidad en desarrollo", "Historial", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            });
        }
        return botonDinamico;
    }

    public static void main(String[] args) {
        // Para pruebas
        Admin admin = new Admin("12345678A", "Admin Test", "admin@test.com", "1234", null, null);
        new VentanaInformacionUsuario(null, admin);
    }
}