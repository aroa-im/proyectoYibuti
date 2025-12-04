package gui;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;

import javax.swing.*;
import javax.swing.border.*;

import db.UsuarioDTO;
import domain.Admin;
import domain.Cliente;
import domain.Usuario;
import main.main;


public class VentanaIniciarSesion extends JFrame {
	private static final long serialVersionUID = 1L;
	public VentanaIniciarSesion(JFrame previousWindow) {
		setTitle("Iniciar Sesión");
		setSize(650, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(previousWindow !=null) {
					previousWindow.setVisible(true);
				}
				dispose();
			}
		});

		// Texto superior
		JLabel topText = new JLabel("Iniciar sesión", SwingConstants.CENTER); // Label con texto centrado
		topText.setFont(new Font("Verdana", Font.BOLD, 32));

		
		// Cuerpo de la ventana
		JPanel body = new JPanel();
		body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
				
		JLabel textDNI = new JLabel("DNI");
		textDNI.setFont(topText.getFont().deriveFont(Font.PLAIN, 20));
		JLabel textContrasena = new JLabel("Contraseña");
		textContrasena.setFont(topText.getFont().deriveFont(Font.PLAIN, 20));
		
		JTextField tfDNI = new JTextField();
		JPasswordField tfContrasena = new JPasswordField();
		
		tfDNI.setPreferredSize(new Dimension(125, 25));
		tfContrasena.setPreferredSize(new Dimension(125, 25));
		
		JPanel tfDNIPanel = new JPanel();
		JPanel tfContrasenaPanel = new JPanel();
	
		tfDNIPanel.add(tfDNI);
		tfContrasenaPanel.add(tfContrasena);

		
		textDNI.setAlignmentX(CENTER_ALIGNMENT);
		textContrasena.setAlignmentX(CENTER_ALIGNMENT);
		tfDNI.setAlignmentX(CENTER_ALIGNMENT);
		tfContrasena.setAlignmentX(CENTER_ALIGNMENT);
		
		body.add(textDNI);
		body.add(tfDNIPanel);
		body.add(textContrasena);
		body.add(tfContrasenaPanel);
		
		// Parte baja de la pantalla
		JButton iniciarSesionButton = new JButton("Iniciar sesión");
		iniciarSesionButton.addActionListener(e -> {
			// TODO: COMPROBACIÓN DE QUE EL USUARIO EXISTE
			String dni = tfDNI.getText();
			String password = new String(tfContrasena.getPassword());
			
			if (!main.getUsuarioDAO().isUsuarioCorrecto(dni, password)) { // El usuario no existe en la BD
				JOptionPane.showMessageDialog(this, "Este usuario no existe o la contraseña es incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
				tfDNI.setText("");
				tfContrasena.setText("");
			} else { // El usuario existe en la BD
				UsuarioDTO usuarioLogueado = main.getUsuarioDAO().getUsuario(dni); // getUsuario
				// getDatosAdicionales
				if (usuarioLogueado.isAdmin()) {
					main.setUsuario(new Admin(usuarioLogueado));
				} else {
					main.setUsuario(new Cliente(usuarioLogueado));
				}
				
//				Instanciar una nueva ventana Madre
				try {
					if (previousWindow instanceof VentanaInformacionRecurso) {
						new VentanaPortada();
					} else {
						previousWindow.getClass().getConstructor().newInstance();
					}
					previousWindow.dispose();
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				dispose();
			}
			

		});
		JLabel noCuentaLabel = new JLabel("¿No tienes cuenta?", SwingConstants.CENTER);
		noCuentaLabel.setForeground(Color.blue);
		noCuentaLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    	setVisible(false);
                        new VentanaRegistrarse(previousWindow);
						dispose();
                    }
			
		});
		
		JPanel tail = new JPanel(new GridLayout(2, 1, 0, 0));
		
		JPanel iniciarSesionButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		iniciarSesionButtonPanel.add(iniciarSesionButton);

		JPanel noCuentaLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		noCuentaLabelPanel.add(noCuentaLabel);
		
		tail.add(iniciarSesionButtonPanel);
		tail.add(noCuentaLabelPanel);
		
		topText.setBorder(new EmptyBorder(20, 0, 20, 0));
		body.setBorder(new EmptyBorder(50, 100, 0, 100));
		tail.setBorder(new EmptyBorder(50, 0, 0, 0));
		
		add(topText, BorderLayout.NORTH);
		add(body, BorderLayout.CENTER);
		add(tail, BorderLayout.SOUTH);
		setVisible(true);
	}
	public static void main(String[] args) {
		new VentanaIniciarSesion(null);	
	}
}
