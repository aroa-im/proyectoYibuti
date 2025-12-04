package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import domain.Admin;
import domain.Cliente;
import domain.GeneroPelicula;
import domain.GeneroVideoJuego;
import domain.Pelicula;
import domain.Videojuego;
import domain.Review;
import domain.Seccion;
import domain.TipoConsola;
import domain.TipoPelicula;
import domain.Usuario;
import gui.components.Header;
import main.main;
import utils.Utils; 
import javax.swing.ImageIcon;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class VentanaInformacionRecurso extends JFrame {
    
    private JFrame vInformacionProducto;
    private static final long serialVersionUID = 1647556562163809896L;
    private JPanel pOeste, pEste, pSur, pCentro, pHeader;
    //private Usuario usuario = main.getUsuario();
    private Usuario usuario;

    public void setMainWindowProperties(Seccion seccion) {
        
        vInformacionProducto = this;
        
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(Color.WHITE);
        
        pCentro = new JPanel();
        pSur = new JPanel();
        pEste = new JPanel();
        pOeste = new JPanel();
        //pHeader = new Header(usuario, this);
      
        pCentro.setBackground(Color.WHITE);
        //pSur.setBackground(Color.WHITE);
        //pHeader.setBackground(Color.WHITE);
        pOeste.setBackground(Color.WHITE);
        pEste.setBackground(Color.WHITE);
        
        getContentPane().add(pCentro, BorderLayout.CENTER);
        //getContentPane().add(pHeader, BorderLayout.NORTH);
        getContentPane().add(pSur, BorderLayout.SOUTH);
        getContentPane().add(pEste, BorderLayout.EAST);
        getContentPane().add(pOeste, BorderLayout.WEST);
    }
    
    public VentanaInformacionRecurso(Pelicula pelicula) {
    	

        setMainWindowProperties(Seccion.PELICULA);

        setTitle ("Videoclub: " + pelicula.getTitulo());
        //PANEL OESTE
        pOeste.setLayout(new BoxLayout(pOeste, BoxLayout.Y_AXIS));
        pOeste.setBackground(Color.WHITE);
        //PANEL CENTRO
        pCentro.setLayout(new BoxLayout(pCentro, BoxLayout.Y_AXIS));
        pCentro.setBackground(Color.WHITE);
            
        //TAMAÑO DE PANELES SOUTH Y CENTRE
        pOeste.setPreferredSize(new Dimension(500, 0));
        pCentro.setPreferredSize(new Dimension(1000, 0));
        
        //PANEL DE IMAGEN DEL LIBRO
        JPanel panelimagenPelicula= new JPanel();
        panelimagenPelicula.setBackground(Color.WHITE);
        panelimagenPelicula.setAlignmentX(CENTER_ALIGNMENT);
        panelimagenPelicula.setBorder(new EmptyBorder (0,65,0,0));
        JLabel imagenDePelicula = new JLabel();
        imagenDePelicula.setPreferredSize(new Dimension(275,500));
        if (pelicula.getFoto() != null) {
            Image imagen = pelicula.getFoto().getImage().getScaledInstance(200, 350, Image.SCALE_SMOOTH);
            ImageIcon imagenEscalada = new ImageIcon(imagen);
            imagenDePelicula.setIcon(imagenEscalada);
        }
        
        imagenDePelicula.setAlignmentX(CENTER_ALIGNMENT);
        
        panelimagenPelicula.add(imagenDePelicula);
        pOeste.add(panelimagenPelicula);
        
        //PANEL DE LA DESCRIPCIÓN DEL LIBRO
        JPanel panelTitulo = new JPanel();
        panelTitulo.setPreferredSize(new Dimension(900, 50)); 
        panelTitulo.setBackground(Color.WHITE);
        JPanel panelDescripcion = new JPanel();
        panelDescripcion.setLayout(new BoxLayout(panelDescripcion, BoxLayout.Y_AXIS));
        panelDescripcion.setBackground(Color.WHITE);
        JLabel tituloPelicula = new JLabel(pelicula.getTitulo());
        JTextArea tituloPeliculaArea = new JTextArea(pelicula.getTitulo());

        tituloPelicula.setFont(new Font("Arial", Font.BOLD, 24));
        tituloPeliculaArea.setFont(tituloPelicula.getFont());

        List<JTextArea> areas = new ArrayList<>();

        JTextArea taDirector= new JTextArea("Director(a): " + pelicula.getDirector());
        JTextArea taGenero = new JTextArea("Género: " + pelicula.getGenero());
        JTextArea taDuracion = new JTextArea("Duración: " + pelicula.getDuracion());
        JTextArea taRating = new JTextArea("Rating: " + pelicula.getRating() + "/10");
        JTextArea taSinopsis = new JTextArea("Sinopsis: " + pelicula.getSinopsis());

        areas.add(taDirector);
        areas.add(taGenero);
        areas.add(taDuracion);
        areas.add(taRating); // TODO: ?
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
        
        // Agregar JTextArea en un JScrollPane
        JScrollPane sinopsisScrollPane = new JScrollPane(taSinopsis);
        sinopsisScrollPane.setBackground(Color.WHITE);
        sinopsisScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sinopsisScrollPane.setBorder(null);
        
        // Añadir título y descripción al panel
        if (usuario instanceof Admin) {
            panelTitulo.add(tituloPeliculaArea, BorderLayout.NORTH);
        } else {
            panelTitulo.add(tituloPelicula, BorderLayout.NORTH);
        }
        
        panelDescripcion.add(taDirector, BorderLayout.CENTER);
        panelDescripcion.add(taGenero, BorderLayout.CENTER);
        panelDescripcion.add(taDuracion, BorderLayout.CENTER);
        panelDescripcion.add(taRating, BorderLayout.CENTER);
        panelDescripcion.add(sinopsisScrollPane, BorderLayout.CENTER);
        pCentro.add(panelTitulo);
        pCentro.add(panelDescripcion);
        
        //PANEL REVIEWS
        JPanel reviews = new JPanel();
        reviews.setLayout(new BoxLayout(reviews, BoxLayout.Y_AXIS));
        
        reviews.setBackground(Color.WHITE);
        
        JLabel tituloReviews = new JLabel ("REVIEWS");
        tituloReviews.setFont(new Font("Arial", Font.BOLD, 16));
        tituloReviews.setAlignmentX(CENTER_ALIGNMENT);
        reviews.add(tituloReviews);
        
        String stringReviews = "";
        for (Review review : pelicula.getComentarios()) {
            stringReviews += review.toString() + "\n";
        }

        if (stringReviews.equals("")) {
            stringReviews = "Esta pelicula no tiene reviews";
        }

        JTextArea textoReviews = new JTextArea(stringReviews);
        textoReviews.setFont(new Font("Arial", Font.PLAIN, 14));
        textoReviews.setEditable(false);
        textoReviews.setBackground(Color.WHITE);
        
        JScrollPane reviewsScrollPane = new JScrollPane(textoReviews);
        reviewsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        reviewsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        reviewsScrollPane.setPreferredSize(new Dimension(200, 100));
        reviewsScrollPane.setPreferredSize(new Dimension(400, 150));
      
        reviews.add(reviewsScrollPane);
       
        pOeste.add(reviews);
        
        //PANEL BOTONES
        
        JPanel botonesPanel = new JPanel();
        
        botonesPanel.setBackground(Color.WHITE);
        botonesPanel.setBorder(new EmptyBorder(110,0,45, 0));
        
        JButton botonReview = new JButton("Añadir review");
        JButton botonReservar = new JButton("Reservar");
        JButton botonEditar = new JButton("Editar");
        botonEditar.addActionListener(e -> {
            dispose();
            //new VentanaCrearEditarPelicula(this, pelicula);
        });
        botonReview.setFont(new Font("Arial", Font.BOLD, 17));        
        botonReservar.setFont(new Font("Arial", Font.BOLD, 17));
        
        if (usuario == null) {
            botonesPanel.add(botonReview);
            botonesPanel.add(botonReservar);
            botonReview.setEnabled(false);
            botonReservar.setToolTipText("No puedes reservar peliculas sin estar registrado");
            botonReservar.setEnabled(false);
            botonReview.setToolTipText("No puedes hacer una review sin estar registrado");
        } else if (usuario instanceof Admin) {
            botonesPanel.add(botonEditar);
        } else {
            botonesPanel.add(botonReview);
            botonesPanel.add(botonReservar);
        }

        pCentro.add(botonesPanel);
        
        botonReview.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaAñadirReviewPelicula(pelicula);
            }

        });

        pOeste.setBorder(new EmptyBorder(0,20,0,0));
        pCentro.setBorder(new EmptyBorder(70, 50, 0, 0));
        
        botonReservar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {                 
                VentanaConfirmacionReservaPelicula nuevaVentana = new VentanaConfirmacionReservaPelicula(pelicula);
                nuevaVentana.setVisible(true);
                vInformacionProducto.dispose();
            }
        });

        setVisible(true);
    }

	
	public VentanaInformacionRecurso(Videojuego videojuego) {
		setMainWindowProperties(Seccion.VIDEOJUEGO);
		setTitle ("Videoclub: " + videojuego.getTitulo());
		//PANEL OESTE
		pOeste.setLayout(new BoxLayout(pOeste, BoxLayout.Y_AXIS));
		pOeste.setBackground(Color.WHITE);
		//PANEL CENTRO
		pCentro.setLayout(new BoxLayout(pCentro, BoxLayout.Y_AXIS));
		pCentro.setBackground(Color.WHITE);
			
		//TAMAÑO DE PANELES SOUTH Y CENTRE
		pOeste.setPreferredSize(new Dimension(500, 0));
		pCentro.setPreferredSize(new Dimension(1000, 0));
		
		//PANEL DE IMAGEN DEL LIBRO
		JPanel panelimagenVideojuego= new JPanel();
		panelimagenVideojuego.setBackground(Color.WHITE);
		panelimagenVideojuego.setAlignmentX(CENTER_ALIGNMENT);
		panelimagenVideojuego.setBorder(new EmptyBorder (0,65,0,0));
		JLabel imagenDelVideojuego = new JLabel();
		imagenDelVideojuego.setPreferredSize(new Dimension(275,500));
		if (videojuego.getFoto() != null) {
			Image imagen = videojuego.getFoto().getImage().getScaledInstance(200, 350, Image.SCALE_SMOOTH);
			ImageIcon imagenEscalada = new ImageIcon(imagen);
			imagenDelVideojuego.setIcon(imagenEscalada);
		}
		
		imagenDelVideojuego.setAlignmentX(CENTER_ALIGNMENT);
		
		panelimagenVideojuego.add(imagenDelVideojuego);
		pOeste.add(panelimagenVideojuego);
		
		//PANEL DE LA DESCRIPCIÓN DEL LIBRO
		JPanel panelTitulo = new JPanel();
		panelTitulo.setPreferredSize(new Dimension(900, 50)); 
		panelTitulo.setBackground(Color.WHITE);
		JPanel panelDescripcion = new JPanel();
		panelDescripcion.setLayout(new BoxLayout(panelDescripcion, BoxLayout.Y_AXIS));
		panelDescripcion.setBackground(Color.WHITE);
		JLabel tituloLibro = new JLabel(videojuego.getTitulo());
		JTextArea tituloLibroArea = new JTextArea(videojuego.getTitulo());

		tituloLibro.setFont(new Font("Arial", Font.BOLD, 24));
		tituloLibroArea.setFont(tituloLibro.getFont());

		List<JTextArea> areas = new ArrayList<>();

		JTextArea taAutor = new JTextArea("Autor(a): " + videojuego.getAutor());
		JTextArea taGenero = new JTextArea("Género: " + videojuego.getGenero());
		JTextArea taTipoConsola = new JTextArea("Tipo de consola: " + videojuego.getTipo());
		JTextArea taRating = new JTextArea("Rating: " + videojuego.getRating() + "/10");
		JTextArea taSinopsis = new JTextArea("Sinopsis: " + videojuego.getSinopsis());

		areas.add(taAutor);
		areas.add(taGenero);
		areas.add(taTipoConsola);
		areas.add(taRating); // TODO: ?
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
        
        // Agregar JTextArea en un JScrollPane
        JScrollPane sinopsisScrollPane = new JScrollPane(taSinopsis);
        sinopsisScrollPane.setBackground(Color.WHITE);
        sinopsisScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sinopsisScrollPane.setBorder(null);
        
        // Añadir título y descripción al panel
		if (usuario instanceof Admin) {
			panelTitulo.add(tituloLibroArea, BorderLayout.NORTH);
		} else {
        	panelTitulo.add(tituloLibro, BorderLayout.NORTH);
		}
        panelDescripcion.add(taAutor, BorderLayout.CENTER);
		panelDescripcion.add(taGenero, BorderLayout.CENTER);
		panelDescripcion.add(taTipoConsola, BorderLayout.CENTER);
		panelDescripcion.add(taRating, BorderLayout.CENTER);
		panelDescripcion.add(sinopsisScrollPane, BorderLayout.CENTER);
        pCentro.add(panelTitulo);
        pCentro.add(panelDescripcion);
		
		
		//PANEL REVIEWS
		JPanel reviews = new JPanel();
		reviews.setLayout(new BoxLayout(reviews, BoxLayout.Y_AXIS));
		
		reviews.setBackground(Color.WHITE);
		
		JLabel tituloReviews = new JLabel ("REVIEWS");
		tituloReviews.setFont(new Font("Arial", Font.BOLD, 16));
		tituloReviews.setAlignmentX(CENTER_ALIGNMENT);
		reviews.add(tituloReviews);
		
		String stringReviews = "";
		for (Review review : videojuego.getComentarios()) {
			stringReviews += review.toString() + "\n";
		}

		if (stringReviews.equals("")) {
			stringReviews = "Este videojuego no tiene reviews";
		}

		JTextArea textoReviews = new JTextArea(stringReviews);
		textoReviews.setFont(new Font("Arial", Font.PLAIN, 14));
		textoReviews.setEditable(false);
		textoReviews.setBackground(Color.WHITE);
        
		JScrollPane reviewsScrollPane = new JScrollPane(textoReviews);
        reviewsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        reviewsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        reviewsScrollPane.setPreferredSize(new Dimension(200, 100));
        reviewsScrollPane.setPreferredSize(new Dimension(400, 150));
      

        reviews.add(reviewsScrollPane);
       
		pOeste.add(reviews);
		
		//PANEL BOTONES
		
		JPanel botonesPanel = new JPanel();
		
		
		botonesPanel.setBackground(Color.WHITE);
		botonesPanel.setBorder(new EmptyBorder(110,0,45, 0));
		
		/*GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
        */
		
		JButton botonReview = new JButton("Añadir review");
		JButton botonReservar = new JButton("Reservar");
		JButton botonEditar = new JButton("Editar");
		/*botonEditar.addActionListener(e -> {
			dispose();
			new VentanaCrearEditarLibro(this, videojuego);
		});
		*/
		botonReview.setFont(new Font("Arial", Font.BOLD, 17));		
		botonReservar.setFont(new Font("Arial", Font.BOLD, 17));
        
        if (usuario == null) {
        	botonesPanel.add(botonReview);
			botonesPanel.add(botonReservar);
			botonReview.setEnabled(false);
			botonReservar.setToolTipText("No puedes reservar videojuegos sin estar registrado");
			botonReservar.setEnabled(false);
			botonReview.setToolTipText("No puedes hacer una review sin estar registrado");
		}else if (usuario instanceof Admin) {
			botonesPanel.add(botonEditar);
		} else {
			botonesPanel.add(botonReview);
			botonesPanel.add(botonReservar);
		}

		pCentro.add(botonesPanel);
		
		botonReview.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaAñadirReviewVideojuego(videojuego);
			}
		});
		
		
		pOeste.setBorder(new EmptyBorder(0,20,0,0));
		pCentro.setBorder(new EmptyBorder(70, 50, 0, 0));
		
		botonReservar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {				 
				VentanaConfirmacionReservaVideojuego nuevaVentana = new VentanaConfirmacionReservaVideojuego(videojuego);
				nuevaVentana.setVisible(true);
				vInformacionProducto.dispose();
			}
		});
		
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
			
			
			
		    ImageIcon fotoVide = Utils.loadImage("videojuegos.png", 128, 200);
		    Videojuego videojuego = new Videojuego(
	            // ID añadido (1L)
	            1L,
		        "Videojuego1",             
		        "Una aventura épica en un mundo abierto",               
		        59.99f,                                                  
		        10,                                                      
		        new ArrayList<Review>(),                                 
		        GeneroVideoJuego.AVENTURA,                             
		        TipoConsola.NINTENDO,                            
		        "Nintendo",                                              
		        fotoVide                                                     
		    );
		    
		    ImageIcon fotoPeli = Utils.loadImage("peliculas/pelicula1.jpg", 128, 200); 
		    
		    
		    Pelicula pelicula = new Pelicula(
	            2L,
		        "Pelicula1",                              
		        "Una historia épica de acción y aventura", 
		        19.99f,                                   
		        8,                                        
		        new ArrayList<Review>(),                  
		        TipoPelicula.DVD,                        
		        GeneroPelicula.ACCION,                    
		        "Director Ejemplo",                      
		        120,                                      
		        fotoPeli                                      
		    );
		        
		     new VentanaInformacionRecurso(videojuego);
		     new VentanaInformacionRecurso(pelicula);
			    
		    
	
		}
}