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
import domain.GeneroPelicula;
import domain.Pelicula;
import domain.Review;
import domain.Videojuego;
import domain.Seccion;
import domain.TipoPelicula;
import domain.Usuario;
import gui.components.Header;
import main.main;
import utils.Utils;

public class VentanaConfirmacionReservaPelicula extends JFrame {
	
	private JPanel pOeste, pEste, pSur, pCentro, pHeader;
//	private Usuario usuario = main.getUsuario();
	private Usuario usuario;
	
	private static final long serialVersionUID = -5490640345084381273L;
	
	public VentanaConfirmacionReservaPelicula(Pelicula pelicula) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Confirmación de reserva");
		setSize(1280, 720);
		setLocationRelativeTo(null);
		
		
		getContentPane().setBackground(Color.WHITE);
		
		pCentro = new JPanel();
	    pSur = new JPanel();
	    pEste = new JPanel();
	    pOeste = new JPanel();
	    pHeader = new Header(Seccion.PELICULA, usuario, this);
	  
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
//		Header header = new Header(Seccion.PELICULA, new Cliente(), this);
//		header.setBackground(Color.WHITE);
		
		//panel de imagen
		
		JPanel panelimagenPelicula= new JPanel();
		panelimagenPelicula.setBackground(Color.WHITE);
		panelimagenPelicula.setAlignmentX(CENTER_ALIGNMENT);
		panelimagenPelicula.setBorder(new EmptyBorder (0,65,0,0));
		JLabel imagenDelPelicula = new JLabel();
		imagenDelPelicula.setPreferredSize(new Dimension(275,500));
		Image imagen = pelicula.getFoto().getImage().getScaledInstance(200, 350, Image.SCALE_SMOOTH);
		ImageIcon imagenEscalada = new ImageIcon(imagen);
		imagenDelPelicula.setIcon(imagenEscalada);
		imagenDelPelicula.setAlignmentX(CENTER_ALIGNMENT);
		
		panelimagenPelicula.add(imagenDelPelicula);
		pOeste.add(panelimagenPelicula);	
		
		//panel texto
		JPanel panelTexto = new JPanel();
		panelTexto.setBackground(Color.WHITE);
		panelTexto.setLayout(new BoxLayout(panelTexto, BoxLayout.Y_AXIS));
		JLabel labelTitulo = new JLabel(pelicula.getTitulo());
		Font fuente = new Font("ARIAL",Font.BOLD, 32);
		labelTitulo.setFont(fuente);
		labelTitulo.setAlignmentX(CENTER_ALIGNMENT);
		panelTexto.add(labelTitulo);
		panelTexto.setBorder(new EmptyBorder(60,0,0,85));
		
		
		List<JTextArea> areas = new ArrayList<>();

		JTextArea taDirector = new JTextArea("Director(a): " + pelicula.getDirector());
		JTextArea taGenero = new JTextArea("Género: " + pelicula.getGenero());
		JTextArea taDuracion = new JTextArea("Duración: " + pelicula.getDuracion());
		JTextArea taRating = new JTextArea("Rating: " + pelicula.getRating() + "/10");
		JTextArea taSinopsis = new JTextArea("Sinopsis: " + pelicula.getSinopsis());
		
		
		areas.add(taDirector);
		areas.add(taGenero);
		areas.add(taDuracion);
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
        pCentro.add(taDirector);
        pCentro.add(taGenero);
        pCentro.add(taDuracion);
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
			VentanaInformacionRecurso redirectWindow = new VentanaInformacionRecurso(pelicula);
			JOptionPane.showMessageDialog(redirectWindow, "Gracias por hacer tu reserva!", "Reserva hecha correctamente", JOptionPane.INFORMATION_MESSAGE);
		});
		
		botonVolver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaInformacionRecurso nuevaVentana = new VentanaInformacionRecurso(pelicula);
				nuevaVentana.setVisible(true);
				dispose();
			}
			
		});
		
//		add(header,BorderLayout.NORTH);

		setVisible(true);
	} 
	public static void main(String[] args) {
		    
		    ImageIcon foto = Utils.loadImage("peliculas/pelicula1.jpg", 128, 200); 
		    
		    
		    Pelicula pelicula = new Pelicula(
	            1L,
		        "Pelicula1",                              
		        "Una historia épica de acción y aventura", 
		        19.99f,                                   
		        8,                                        
		        new ArrayList<Review>(),                  
		        TipoPelicula.DVD,                        
		        GeneroPelicula.ACCION,                    
		        "Director Ejemplo",                      
		        120,                                      
		        foto                                      
		    );
		    
		   
		    new VentanaConfirmacionReservaPelicula(pelicula);
		}
}
