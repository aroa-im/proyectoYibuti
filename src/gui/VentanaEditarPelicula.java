package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import domain.Admin;
import domain.GeneroPelicula;
import domain.Pelicula;
import domain.Review;
import domain.TipoPelicula;
import domain.Usuario;
import main.main;

public class VentanaEditarPelicula extends JFrame{
	private static final long serialVersionUID = 1L;
	private Pelicula libro = new Pelicula();
	

	private Usuario usuario = main.getUsuario();
	
	private String tituloAntiguo;
	private String titulo;
	private String autor;
	private int numeroDePaginas;
	private String sinopsis;
	private String genero;
	private int rating;
	private int añoPublicacion;
	private ImageIcon foto;
	
	private JTextField tfTitulo = new JTextField();
	private JTextField tfDirector = new JTextField();
	private JTextField tfDuracion = new JTextField();
	private JTextArea taSinopsis = new JTextArea();
	private JTextField tfGenero = new JTextField();
	private JTextField tfRating = new JTextField();
	private JTextField tfPrecio = new JTextField();
	
	private JLabel imageLabel = new JLabel();
	private ImageIcon imagen;
	private File ficheroImagen;

	public VentanaEditarPelicula(JFrame previousWindow, Pelicula peliculaAEditar) {
		
		tituloAntiguo= peliculaAEditar.getTitulo();
		
		String titleString = "";
		if (previousWindow instanceof VentanaPeliculas) {
			titleString = "Crear";
			
		} else {
			
			titleString = "Editar";
			tfTitulo.setText(peliculaAEditar.getTitulo());
			tfDirector.setText(peliculaAEditar.getDirector());
			tfDuracion.setText(Integer.toString(peliculaAEditar.getDuracion()));
			taSinopsis.setText(peliculaAEditar.getSinopsis());
			tfGenero.setText(peliculaAEditar.getGenero().toString());
			tfRating.setText(Integer.toString(peliculaAEditar.getRating()));
			tfPrecio.setText(Double.toString(peliculaAEditar.getPrecio()));
			imageLabel.setIcon(peliculaAEditar.getFoto());
		}
		
		setTitle(titleString + "pelicula");
		setSize(1280, 720);
		setLocationRelativeTo(null);

		addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
        	if (previousWindow instanceof VentanaPeliculas) {
        		new VentanaPeliculas();
        	} else {
        		new VentanaInformacionRecurso(peliculaAEditar);
        	}
        	dispose();
        	}
		});
		
		// Texto superior
		JLabel topText = new JLabel(titleString + " libro", SwingConstants.CENTER); // Label con texto centrado
		topText.setFont(new Font("Verdana", Font.BOLD, 32));

		
		// Cuerpo de la ventana
		JPanel body = new JPanel();
		
		JPanel leftBody = new JPanel();
		JPanel rightBody = new JPanel();
		
		rightBody.setLayout(new BoxLayout(rightBody, BoxLayout.Y_AXIS));
		leftBody.setLayout(new BoxLayout(leftBody, BoxLayout.Y_AXIS));
		
		JLabel textTitulo = new JLabel("Título");
		textTitulo.setFont(topText.getFont().deriveFont(Font.PLAIN, 20));
		JLabel textAutor = new JLabel("Autor(a)");
		textAutor.setFont(topText.getFont().deriveFont(Font.PLAIN, 20));
		JLabel textNumeroPaginas = new JLabel("Número de páginas");
		textNumeroPaginas.setFont(topText.getFont().deriveFont(Font.PLAIN, 20));
		JLabel textSinopsis = new JLabel("Sinopsis");
		textSinopsis.setFont(topText.getFont().deriveFont(Font.PLAIN, 20));
		JLabel textGenero = new JLabel("Género");
		textGenero.setFont(topText.getFont().deriveFont(Font.PLAIN, 20));
		JLabel textRating = new JLabel("Rating");
		textRating.setFont(topText.getFont().deriveFont(Font.PLAIN, 20));
		JLabel textAñoPublicacion = new JLabel("Año de publicación");
		textAñoPublicacion.setFont(topText.getFont().deriveFont(Font.PLAIN, 20));
		
		tfTitulo.setPreferredSize(new Dimension(125, 25));
		tfDirector.setPreferredSize(new Dimension(125, 25));
		tfDuracion.setPreferredSize(new Dimension(125, 25));
		tfGenero.setPreferredSize(new Dimension(125, 25));
		tfRating.setPreferredSize(new Dimension(125, 25));
		tfPrecio.setPreferredSize(new Dimension(125, 25));
		
		JPanel tfISBNPanel = new JPanel();
		JPanel tfTituloPanel = new JPanel();
		JPanel tfDirectorPanel = new JPanel();
		JPanel tfDuracionPanel = new JPanel();
		JPanel taSinopsisPanel = new JPanel();
		JPanel tfGeneroPanel = new JPanel();
		JPanel tfRatingPanel = new JPanel();
		JPanel tfFechaPanel = new JPanel();
		
		tfTituloPanel.add(tfTitulo);
		tfDirectorPanel.add(tfDirector);
		tfDuracionPanel.add(tfDuracion);
		taSinopsisPanel.add(taSinopsis);
		tfGeneroPanel.add(tfGenero);
		tfRatingPanel.add(tfRating);
		tfFechaPanel.add(tfPrecio);
		
		textTitulo.setAlignmentX(CENTER_ALIGNMENT);
		tfTitulo.setAlignmentX(CENTER_ALIGNMENT);
		textAutor.setAlignmentX(CENTER_ALIGNMENT);
		textNumeroPaginas.setAlignmentX(CENTER_ALIGNMENT);
		tfDirector.setAlignmentX(CENTER_ALIGNMENT);
		tfDuracion.setAlignmentX(CENTER_ALIGNMENT);
		textSinopsis.setAlignmentX(CENTER_ALIGNMENT);
		textGenero.setAlignmentX(CENTER_ALIGNMENT);
		taSinopsis.setAlignmentX(CENTER_ALIGNMENT);
		tfGenero.setAlignmentX(CENTER_ALIGNMENT);
		textRating.setAlignmentX(CENTER_ALIGNMENT);
		textAñoPublicacion.setAlignmentX(CENTER_ALIGNMENT);
		tfRating.setAlignmentX(CENTER_ALIGNMENT);
		tfPrecio.setAlignmentX(CENTER_ALIGNMENT);
		
		leftBody.add(tfISBNPanel);
		leftBody.add(textTitulo);
		leftBody.add(tfTituloPanel);
		leftBody.add(textAutor);
		leftBody.add(tfDirectorPanel);
		leftBody.add(textNumeroPaginas);
		leftBody.add(tfDuracionPanel);
		rightBody.add(textGenero);
		rightBody.add(tfGeneroPanel);
		rightBody.add(textRating);
		rightBody.add(tfRatingPanel);
		rightBody.add(textAñoPublicacion);
		rightBody.add(tfPrecio);
		
		body.add(leftBody);
		body.add(rightBody);
		
		JPanel west = new JPanel();
		west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
		
		JLabel textImagen = new JLabel("Imagen del libro");
		textImagen.setAlignmentX(CENTER_ALIGNMENT);

		imageLabel.setAlignmentX(CENTER_ALIGNMENT);
		JButton añadirImagenButton = new JButton("Añadir imagen");
		añadirImagenButton.setAlignmentX(CENTER_ALIGNMENT);
		
		añadirImagenButton.addActionListener(e -> {
		    JFileChooser fileChooser = new JFileChooser();

		    FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Imagen .jpg", "jpg");
		    fileChooser.setFileFilter(imageFilter);
		    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

		    int opcion = fileChooser.showOpenDialog(this);

		    if (opcion == JFileChooser.APPROVE_OPTION) {
		        ficheroImagen = fileChooser.getSelectedFile();

		        
		        Path destino = Paths.get("resources/images/books/", titulo + ".jpg");
		        try {
		            Files.copy(ficheroImagen.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);
		        } catch (IOException e1) {
		           e1.printStackTrace();
		        }

		        BufferedImage bi = null;
		        try {
		            bi = ImageIO.read(ficheroImagen);
		        } catch (IOException e1) {
		            e1.printStackTrace();
		        }

		        if (bi != null) {
		            imagen = new ImageIcon(bi.getScaledInstance(128, 200, Image.SCALE_DEFAULT));
		            foto = imagen;
		            imageLabel.setIcon(imagen);
		        }
		    }
		});

		
		west.add(textImagen);
		west.add(imageLabel);
		west.add(añadirImagenButton);
		
		JPanel east = new JPanel();
		east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
		
		taSinopsis.setFont(new Font("Arial", Font.PLAIN, 12));
		taSinopsis.setEditable(true);
		taSinopsis.setLineWrap(true);
		taSinopsis.setBorder(null);
		taSinopsis.setBorder(BorderFactory.createEmptyBorder());
		taSinopsis.setWrapStyleWord(true);
		taSinopsis.setBackground(Color.WHITE);
		taSinopsis.setColumns(30);
		taSinopsis.setRows(30);
		
		east.add(textSinopsis);
		east.add(taSinopsisPanel);
		
		rightBody.setBorder(new EmptyBorder(0, 60, 0, 0));
		leftBody.setBorder(new EmptyBorder(0, 0, 0, 0));

		JPanel tail = new JPanel(new GridLayout(2, 1, 0, 0));
		
		JPanel añadirLibroButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

			tail.add(añadirLibroButtonPanel);
			
			topText.setBorder(new EmptyBorder(20, 0, 20, 0));
			body.setBorder(new EmptyBorder(50, 100, 0, 100));
			tail.setBorder(new EmptyBorder(50, 0, 0, 0));
			
			add(topText, BorderLayout.NORTH);
			add(body, BorderLayout.CENTER);
			add(east, BorderLayout.EAST);
			add(west, BorderLayout.WEST);
			add(tail, BorderLayout.SOUTH);
			
			west.setBorder(new EmptyBorder(50, 50, 0, 0));
			east.setBorder(new EmptyBorder(50, 0, 0, 50));
			
			body.setBorder(new EmptyBorder(100, 30, 0, 0));
			
			setVisible(true);
		}
	}


