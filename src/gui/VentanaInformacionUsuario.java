package gui;

import domain.Admin;
import domain.Usuario;
import gui.components.Header;
import main.main;
import utils.Utils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.*;

import db.UsuarioDTO;

public class VentanaInformacionUsuario extends JFrame {

	private static final long serialVersionUID = 5069329725320750186L;
	private static boolean editarNombre = false;
	private static int contadorClicksNombre = 0;
	private static boolean editarEmail = false;
	private static int contadorClicksEmail = 0;
	private static boolean editarPassword = false;
	private static int contadorClicksPassword = 0;

	private Usuario usuario = main.getUsuario();

	public VentanaInformacionUsuario(JFrame ventanaPrevia) {
		setTitle(usuario.getNombre() + ": Información");
		setSize(640, 480);
		setLocationRelativeTo(null);

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

		botonCerrarSesion.addActionListener(e -> {
			main.setUsuario(null);
			if (ventanaPrevia instanceof VentanaPortada) {
				((VentanaPortada) ventanaPrevia).refrescarUsuario();
			} else if (ventanaPrevia != null) {
				for (Component c : ventanaPrevia.getContentPane().getComponents()) {
					if (c instanceof Header) {
						((Header) c).refrescarUsuario(null);
					}
				}
				ventanaPrevia.setVisible(true);
			}
			dispose();
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

		ImageIcon iconoEditar = Utils.loadImage("edit.png", 20, 20);
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
						usuario.setNombre(input.getText());
						main.getUsuarioDAO().updateUsuario(usuario);
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
						usuario.setEmail(input.getText());
						main.getUsuarioDAO().updateUsuario(usuario);
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

					if (contadorClicksPassword == 2) {
						usuario.setContrasena(input.getText());
						UsuarioDTO usuarioDTO = main.getUsuarioDAO().getUsuario(usuario.getDni());
						main.getUsuarioDAO().updatePassword(usuarioDTO, input.getText());
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
					new VentanaAdministracionUsuarios().setVisible(true);
					dispose();
				}
			});
		} else {
			botonDinamico.setText("Ver historial de reseñas");
			botonDinamico.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					VentanaHistorialUsuario ventanaHistorial = new VentanaHistorialUsuario(usuario);
					ventanaHistorial.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					ventanaHistorial.setVisible(true);
					VentanaInformacionUsuario.this.setVisible(false);
					ventanaHistorial.addWindowListener(new java.awt.event.WindowAdapter() {
			            @Override
			            public void windowClosed(java.awt.event.WindowEvent e) {
			                VentanaInformacionUsuario.this.setVisible(true);
			            }
			        });
				}
			});
		}
		return botonDinamico;
	}

	public static void main(String[] args) {
		new VentanaInformacionUsuario(null);
	}
}
