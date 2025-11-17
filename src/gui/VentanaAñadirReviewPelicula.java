package gui;

import domain.Cliente;
import domain.Pelicula;
import domain.Review;
import domain.GeneroPelicula;
import domain.TipoPelicula;
import main.main;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


import utils.Utils;

public class VentanaAñadirReviewPelicula extends JFrame {

	private static final long serialVersionUID = 1L;
	private Cliente cliente = (Cliente) main.getUsuario();
	private int rating = 0;
	private String comment = "";

	public VentanaAñadirReviewPelicula(Pelicula pelicula) {
		
		setTitle("Anadir review");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 400);
		setLocationRelativeTo(null);
		
		JLabel titleLabel = new JLabel("Añadir review", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Verdana", Font.BOLD, 32));
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		
		JLabel movieIcon = new JLabel();
		movieIcon.setIcon(pelicula.getFoto());
		JLabel movieTitle = new JLabel(pelicula.getTitulo());	
		
		movieIcon.setAlignmentX(CENTER_ALIGNMENT);
		movieTitle.setAlignmentX(CENTER_ALIGNMENT);
		
		leftPanel.add(movieIcon);
		leftPanel.add(movieTitle);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		
		JLabel comentarioLabel = new JLabel("Comentario");
		
		JTextArea comentarioTextArea = new JTextArea();
		comentarioTextArea.setLineWrap(true);
		comentarioTextArea.setWrapStyleWord(true);
		JLabel valoracionLabel = new JLabel("Valoración");
		
		JPanel starPanel = new JPanel();
		starPanel.setLayout(new BoxLayout(starPanel, BoxLayout.Y_AXIS));
		
		JPanel starPanelTop = new JPanel();
		JPanel starPanelBottom = new JPanel();
		
		List<JLabel> starList = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			JLabel label = new JLabel();

			final int j = i;
			label.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					starAction(j, starList);
					rating = (j + 1);
				}
			});

			label.setIcon(Utils.loadImage("estrellaBlanca.png", 24, 24));
			
			starList.add(label);
		}

		for (int i = 0; i < 5; i++) {
			starPanelTop.add(starList.get(i));
		}
		
		for (int i = 5; i < 10; i++) {
			starPanelBottom.add(starList.get(i));
		}
		
		starPanel.add(starPanelTop);
		starPanel.add(starPanelBottom);
		
		JButton publicarButton = new JButton("Publicar review");

		publicarButton.addActionListener(e -> {
			comment = comentarioTextArea.getText();
			
			// TODO: Implementar cuando tengas los DTOs
			/*
			UsuarioDTO clienteDTO = new UsuarioDTO();
			PeliculaDTO peliculaDTO = new PeliculaDTO();
			
			clienteDTO.setAdmin(false);
			clienteDTO.setAmonestaciones(cliente.getAmonestaciones());
			clienteDTO.setDni(cliente.getDni());
			clienteDTO.setNombre(cliente.getNombre());
			
			peliculaDTO.setTitulo(pelicula.getTitulo());
			peliculaDTO.setSinopsis(pelicula.getSinopsis());
			peliculaDTO.setPrecio(pelicula.getPrecio());
			peliculaDTO.setRating(pelicula.getRating());
			peliculaDTO.setTipo(pelicula.getTipo());
			peliculaDTO.setGenero(pelicula.getGenero());
			peliculaDTO.setDirector(pelicula.getDirector());
			peliculaDTO.setDuracion(pelicula.getDuracion());
			
			Review review = new Review(peliculaDTO, clienteDTO, comment, rating);
			*/
			
			// Opción temporal sin DTOs (ajusta según tu constructor de Review)
			Review review = new Review(pelicula, cliente, comment, rating);
			
			pelicula.getComentarios().add(review);
			
			// TODO: Implementar cuando tengas el DAO
			// main.getReviewDAO().addReview(review);

			dispose();
			VentanaInformacionProducto redirectWindow = new VentanaInformacionProducto(pelicula);
			JOptionPane.showMessageDialog(redirectWindow, "Gracias por tu review!", "Review publicada correctamente", JOptionPane.INFORMATION_MESSAGE);
		});
		
		comentarioLabel.setAlignmentX(CENTER_ALIGNMENT);
		comentarioTextArea.setAlignmentX(CENTER_ALIGNMENT);
		valoracionLabel.setAlignmentX(CENTER_ALIGNMENT);
		starPanel.setAlignmentX(CENTER_ALIGNMENT);
		publicarButton.setAlignmentX(CENTER_ALIGNMENT);		
				
		rightPanel.add(comentarioLabel);
		rightPanel.add(comentarioTextArea);
		rightPanel.add(valoracionLabel);
		rightPanel.add(starPanel);
		rightPanel.add(publicarButton);
		
		comentarioLabel.setBorder(new EmptyBorder(0, 0, 5, 0));
		valoracionLabel.setBorder(new EmptyBorder(5, 0, 0, 0));
		leftPanel.setBorder(new EmptyBorder(50, 70, 0, 0));
		rightPanel.setBorder(new EmptyBorder(0, 0, 20, 70));
		starPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
		
		add(titleLabel, BorderLayout.NORTH);
		add(leftPanel, BorderLayout.WEST);
		add(rightPanel, BorderLayout.EAST);

		setVisible(true);
	}

	public static void starAction(int starIndex, List<JLabel> starList) {
		// Pinta las estrellas en función de la estrella de review seleccionada

		for (int i = 0; i < 10; i++) {
			starList.get(i).setIcon(Utils.loadImage("estrellaBlanca.png", 24, 24));
		}

		for (int i = 0; i <= starIndex; i++) {
			starList.get(i).setIcon(Utils.loadImage("estrellaNegra.png", 24, 24));
		}
	}
	
	public static void main(String[] args) {
		// Utils.loadImage busca automáticamente en resources/images/
		// Solo necesitas pasar el nombre del archivo
		ImageIcon foto = Utils.loadImage("peliculas.png", 115, 160);
		
		Pelicula pelicula = new Pelicula("Titulo1", "Sinopsis", 8.8f, 0, new ArrayList<Review>(), 
										  TipoPelicula.DVD, GeneroPelicula.ACCION, "Director1", 200, foto);
		new VentanaAñadirReviewPelicula(pelicula);
	}

}