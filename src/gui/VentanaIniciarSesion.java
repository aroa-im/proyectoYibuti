package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import db.UsuarioDTO;
import domain.Admin;
import domain.Cliente;
import main.main;

public class VentanaIniciarSesion extends JFrame {

    private static final long serialVersionUID = 1L;

    public VentanaIniciarSesion(JFrame previousWindow) {

        setTitle("Iniciar Sesión");
        setSize(650, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel topText = new JLabel("Iniciar sesión", SwingConstants.CENTER);
        topText.setFont(new Font("Verdana", Font.BOLD, 32));

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

        body.add(textDNI);
        body.add(tfDNIPanel);
        body.add(textContrasena);
        body.add(tfContrasenaPanel);

        JButton iniciarSesionButton = new JButton("Iniciar sesión");

        iniciarSesionButton.addActionListener(e -> {

            String dni = tfDNI.getText();
            String password = new String(tfContrasena.getPassword());

            if (!main.getUsuarioDAO().isUsuarioCorrecto(dni, password)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Este usuario no existe o la contraseña es incorrecta",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                tfDNI.setText("");
                tfContrasena.setText("");

            } else {

                UsuarioDTO usuarioLogueado = main.getUsuarioDAO().getUsuario(dni);

                if (usuarioLogueado.isAdmin()) {
                    main.setUsuario(new Admin(usuarioLogueado));
                } else {
                    main.setUsuario(new Cliente(usuarioLogueado));
                }

                if (previousWindow != null) {
                    
                    if (previousWindow instanceof VentanaPeliculas) {
                        new VentanaPeliculas();
                    } else if (previousWindow instanceof VentanaVideojuegos) {
                        new VentanaVideojuegos();
                    }else if(previousWindow instanceof VentanaInformacionRecurso) {
                    	((VentanaInformacionRecurso) previousWindow).refrescar();
                    }else {
                        new VentanaPortada();
                    }
                    previousWindow.dispose();
                }

                dispose(); 
            }
        });

        JLabel noCuentaLabel = new JLabel("¿No tienes cuenta?");
        noCuentaLabel.setForeground(Color.BLUE);

        noCuentaLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new VentanaRegistrarse(previousWindow);
                dispose();
            }
        });

        JPanel tail = new JPanel(new GridLayout(2, 1));

        JPanel iniciarSesionButtonPanel = new JPanel();
        iniciarSesionButtonPanel.add(iniciarSesionButton);

        JPanel noCuentaLabelPanel = new JPanel();
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