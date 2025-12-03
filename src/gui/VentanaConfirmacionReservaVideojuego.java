package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;


import domain.Cliente;
import domain.GeneroVideoJuego;
import domain.Pelicula;
import domain.Review;
import domain.Videojuego;
import domain.Seccion;
import domain.TipoConsola;
import domain.Usuario;
import gui.components.Header;
import main.main;
import utils.Utils;

public class VentanaConfirmacionReservaVideojuego extends JFrame {
	
	private JPanel pOeste, pEste, pSur, pCentro, pHeader;
//	private Usuario usuario = main.getUsuario();
	private Usuario usuario;
	private static final long serialVersionUID = -5490640345084381273L;
	
	public VentanaConfirmacionReservaVideojuego(Videojuego videojuego) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Confirmación de reserva");
		setSize(1280, 720);
		setLocationRelativeTo(null);
		
		
		getContentPane().setBackground(Color.WHITE);
		
		pCentro = new JPanel();
	    pSur = new JPanel();
	    pEste = new JPanel();
	    pOeste = new JPanel();
	    pHeader = new Header(Seccion.VIDEOJUEGO, usuario, this);
	  
	    pCentro.setBackground(Color.WHITE);
        pSur.setBackground(Color.WHITE);
        pHeader.setBackground(Color.WHITE);
        pOeste.setBackground(Color.WHITE);
        pEste.setBackground(Color.WHITE);
        
	    getContentPane().add(pCentro, BorderLayout.CENTER);
	    getContentPane().add(pHeader, BorderLayout.NORTH);
		getContentPane().add(pSur, BorderLayout.SOUTH);
		getContentPane().add(pEste, BorderLayout.EAST);
		getContentPane().add(pOeste, BorderLayout.WEST);
		
		pCentro.setLayout(new BoxLayout(pCentro, BoxLayout.Y_AXIS));
		pCentro.setBackground(Color.WHITE);
		
		//panel header
//		Header header = new Header(Seccion.VIDEOJUEGO, new Cliente(), this);
//		header.setBackground(Color.WHITE);
		
		//panel de imagen
		
		JPanel panelimagenVideojuego= new JPanel();
		panelimagenVideojuego.setBackground(Color.WHITE);
		panelimagenVideojuego.setAlignmentX(CENTER_ALIGNMENT);
		panelimagenVideojuego.setBorder(new EmptyBorder (0,65,0,0));
		JLabel imagenDelVideojuego = new JLabel();
		imagenDelVideojuego.setPreferredSize(new Dimension(275,500));
		Image imagen = videojuego.getFoto().getImage().getScaledInstance(200, 350, Image.SCALE_SMOOTH);
		ImageIcon imagenEscalada = new ImageIcon(imagen);
		imagenDelVideojuego.setIcon(imagenEscalada);
		imagenDelVideojuego.setAlignmentX(CENTER_ALIGNMENT);
		
		panelimagenVideojuego.add(imagenDelVideojuego);
		pOeste.add(panelimagenVideojuego);	
		
		//panel texto
		JPanel panelTexto = new JPanel();
		panelTexto.setBackground(Color.WHITE);
		panelTexto.setLayout(new BoxLayout(panelTexto, BoxLayout.Y_AXIS));
		JLabel labelTitulo = new JLabel(videojuego.getTitulo());
		Font fuente = new Font("ARIAL",Font.BOLD, 32);
		labelTitulo.setFont(fuente);
		labelTitulo.setAlignmentX(CENTER_ALIGNMENT);
		panelTexto.add(labelTitulo);
		panelTexto.setBorder(new EmptyBorder(60,0,0,85));
		
		
		List<JTextArea> areas = new ArrayList<>();

		JTextArea taAutor = new JTextArea("Autor(a): " + videojuego.getAutor());
		JTextArea taGenero = new JTextArea("Género: " + videojuego.getGenero());
		JTextArea taRating = new JTextArea("Rating: " + videojuego.getRating() + "/10");
		JTextArea taSinopsis = new JTextArea("Sinopsis: " + videojuego.getSinopsis());
		
		
		areas.add(taAutor);
		areas.add(taGenero);
		areas.add(taRating); 
		areas.add(taSinopsis);

		for (JTextArea ta : areas) {
			ta.setFont(new Font("Arial", Font.PLAIN, 18));
        	ta.setEditable(false);
 	       	ta.setLineWrap(true);
    	    ta.setBorder(null);
        	ta.setBorder(BorderFactory.createEmptyBorder());
     	   	ta.setWrapStyleWord(true);
        	ta.setBackground(Color.WHITE);
		}
		

		
        JScrollPane descripcionScrollPane = new JScrollPane(taSinopsis);
        descripcionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        descripcionScrollPane.setPreferredSize(new Dimension(600, 500)); // Tamaño más grande para descripción
        descripcionScrollPane.setBorder(null);
        
        pCentro.add(panelTexto);
        pCentro.add(taAutor);
        pCentro.add(taGenero);	
        pCentro.add(taRating);
        pCentro.add(taSinopsis);
        
        //panel boton
		JPanel botonesPanel = new JPanel();
		JButton botonConfirmar = new JButton("Confirmar reserva");
		JButton botonVolver = new JButton ("Volver");

		botonesPanel.setBackground(Color.WHITE);
        botonesPanel.add(botonVolver);
        botonesPanel.add(botonConfirmar);
		botonConfirmar.setFont(new Font("Arial", Font.BOLD, 17));
		botonVolver.setFont(new Font("Arial", Font.BOLD, 17));
		botonesPanel.setBorder(new EmptyBorder(0,0,50,0));
		
		pCentro.add(botonesPanel);
		
		//TODO: HACER EL LISTENER DEL BOTON
		
		botonConfirmar.addActionListener(e -> {
			dispose();
			VentanaInformacionProducto redirectWindow = new VentanaInformacionProducto(videojuego);
			JOptionPane.showMessageDialog(redirectWindow, "Gracias por hacer tu reserva!", "Reserva hecha correctamente", JOptionPane.INFORMATION_MESSAGE);
			
		});
		
		botonVolver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaInformacionProducto nuevaVentana = new VentanaInformacionProducto(videojuego);
				nuevaVentana.setVisible(true);
				dispose();
			}
			
		});
		
//		add(header,BorderLayout.NORTH);

		setVisible(true);
	} 
	public static void main(String[] args) {
			
			ImageIcon foto = Utils.loadImage("videojuegos/videojuego1.png", 128, 200);
			    
			    Videojuego videojuego = new Videojuego(
	                1L,
			        "Videojuego1",             
			        "Una aventura épica en un mundo abierto",               
			        59.99f,                                                  
			        10,                                                      
			        new ArrayList<Review>(),                                 
			        GeneroVideoJuego.AVENTURA,                             
			        TipoConsola.NINTENDO,                            
			        "Nintendo",                                              
			        foto                                                     
			    );
			    
			new VentanaConfirmacionReservaVideojuego(videojuego);
		}
}
