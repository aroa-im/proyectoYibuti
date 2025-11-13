package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import domain.Cliente;
import domain.Usuario;
import domain.UsuarioSimulacion;


public class VentanaRegistrarse extends JFrame {
private static final long serialVersionUID = 2621741612069651140L;
	
	public VentanaRegistrarse(JFrame previousWindow) {
		setTitle("Regístrate");
		setSize (600, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				previousWindow.setVisible(true);
				dispose();
			}
		});

		// Texto superior
		JLabel topText = new JLabel("Regístrate", SwingConstants.CENTER);
		topText.setFont(new Font("Verdana", Font.BOLD, 32));
		topText.setBorder(new EmptyBorder(20, 0, 20, 0));
				
		// Cuerpo de la ventana
		JPanel body = new JPanel(new GridLayout(8, 1, 0, 0));
		body.setBorder(new EmptyBorder(50, 100, 0, 100));
		
		JLabel textDNI= new JLabel("DNI", SwingConstants.CENTER);
		textDNI.setFont(topText.getFont().deriveFont(Font.PLAIN, 20));

		JLabel textNombre= new JLabel("Nombre", SwingConstants.CENTER);
		textNombre.setFont(topText.getFont().deriveFont(Font.PLAIN, 20));
		
		JLabel textEmail = new JLabel("Email", SwingConstants.CENTER);
		textEmail.setFont(topText.getFont().deriveFont(Font.PLAIN, 20));
		
		JLabel textContrasena = new JLabel("Contraseña", SwingConstants.CENTER);
		textContrasena.setFont(topText.getFont().deriveFont(Font.PLAIN, 20));
		
		JLabel textRepetirContrasenia = new JLabel("Repetir contraseña", SwingConstants.CENTER);
		textRepetirContrasenia.setFont(topText.getFont().deriveFont(Font.PLAIN, 20));
			
		JTextField tfDNI = new JTextField();
		JTextField tfNombre = new JTextField();
		JTextField tfUsuarioEmail = new JTextField();
		JPasswordField tfContrasena = new JPasswordField();
		JPasswordField tfRepetirContrasenia = new JPasswordField();
		
		tfDNI.setPreferredSize(new Dimension(125, 25));
		tfNombre.setPreferredSize(new Dimension(125, 25));
		tfUsuarioEmail.setPreferredSize(new Dimension(125, 25));
		tfContrasena.setPreferredSize(new Dimension(125, 25));
		tfRepetirContrasenia.setPreferredSize(new Dimension(125, 25));
		
		JPanel tfDNIPanel = new JPanel();
		tfDNIPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JPanel tfNombrePanel = new JPanel();
		tfNombrePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JPanel tfUsuarioEmailPanel = new JPanel();
		tfUsuarioEmailPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JPanel tfContrasenaPanel = new JPanel();
		tfContrasenaPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JPanel tfRepetirContrasenaPanel = new JPanel();
		tfRepetirContrasenaPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			
		tfDNIPanel.add(tfDNI);
		tfNombrePanel.add(tfNombre);
		tfUsuarioEmailPanel.add(tfUsuarioEmail);
		tfContrasenaPanel.add(tfContrasena);
		tfRepetirContrasenaPanel.add(tfRepetirContrasenia);

		textContrasena.setForeground(Color.black);
		
		body.add(textDNI);
		body.add(tfDNIPanel);
		body.add(textNombre);
		body.add(tfNombrePanel);
		body.add(textEmail);
		body.add(tfUsuarioEmailPanel);
		body.add(textContrasena);
		body.add(tfContrasenaPanel);
		body.add(textRepetirContrasenia);
		body.add(tfRepetirContrasenaPanel);
		
				
		body.setPreferredSize(new Dimension(20, 20));
		
		
		// Parte baja de la pantalla
		JButton registrarseButton = new JButton("Registrarse");
		JLabel yaCuentaLabel = new JLabel("¿Ya tienes cuenta?");
		yaCuentaLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    	setVisible(false);
                        new VentanaIniciarSesion(previousWindow);
						dispose();
                    }	
		});
		yaCuentaLabel.setForeground(Color.blue);
		
		JPanel tail = new JPanel(new GridLayout(2, 1, 0, 0));
		tail.setBorder(new EmptyBorder(50, 0, 0, 0));
		
		JPanel registrarseButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		registrarseButtonPanel.add(registrarseButton);
		registrarseButton.addActionListener(e -> {
			String dni = tfDNI.getText().trim();
            String nombre = tfNombre.getText().trim();
            String email = tfUsuarioEmail.getText().trim();
            String contrasena = new String(tfContrasena.getPassword());
            String repetirContrasena = new String(tfRepetirContrasenia.getPassword());
            
            // Validar que los campos no estén vacíos
            if (dni.isEmpty() || nombre.isEmpty() || email.isEmpty() || 
                contrasena.isEmpty() || repetirContrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Rellena todos los campos", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Validar que las contraseñas coincidan
            if (!contrasena.equals(repetirContrasena)) {
                JOptionPane.showMessageDialog(this,"Las contraseñas no coinciden","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar si el DNI ya existe
            if (UsuarioSimulacion.existeDNI(dni)) {
                JOptionPane.showMessageDialog(this, "El DNI ya está registrado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Registrar usuario
            boolean registrado = UsuarioSimulacion.registrarUsuario(dni, nombre, email, contrasena);
            
            if (registrado) {
                // Obtener el usuario recién registrado
                Usuario nuevoUsuario = UsuarioSimulacion.validarUsuario(dni, contrasena);
                
                JOptionPane.showMessageDialog(this, 
                    "Registro exitoso", 
                    "¡Bienvenido!", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Cerrar ventanas y volver a portada con usuario
                if (previousWindow != null) {
                    previousWindow.dispose();
                }
                new VentanaPortada(nuevoUsuario);
                dispose();
            }
        });
		
		JPanel yaCuentaLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		yaCuentaLabelPanel.add(yaCuentaLabel);
		
		
		tail.add(registrarseButtonPanel);
		tail.add(yaCuentaLabelPanel);		
		
		
		add(topText, BorderLayout.NORTH);
		add(body, BorderLayout.CENTER);
		add(tail, BorderLayout.SOUTH);
		
		setVisible(true);
		
	}
	public static void main(String[] args) {
		new VentanaRegistrarse(null);
	}

}
