package gui;

import domain.Cliente;
import domain.Videojuego;
import domain.Review;
import domain.GeneroVideoJuego;
import domain.TipoConsola;
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

public class VentanaAñadirReviewVideojuego extends JFrame {

	private static final long serialVersionUID = 1L;
	private Cliente cliente = (Cliente) main.getUsuario();
	private int rating = 0;
	private String comment = "";

	public VentanaAñadirReviewVideojuego(Videojuego videojuego) {
		
		setTitle("Anadir review");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 400);
		setLocationRelativeTo(null);
		
		JLabel titleLabel = new JLabel("Añadir review", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Verdana", Font.BOLD, 32));
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		
		JLabel gameIcon = new JLabel();
		gameIcon.setIcon(videojuego.getFoto());
		JLabel gameTitle = new JLabel(videojuego.getTitulo());	
		
		gameIcon.setAlignmentX(CENTER_ALIGNMENT);
		gameTitle.setAlignmentX(CENTER_ALIGNMENT);
		
		leftPanel.add(gameIcon);
		leftPanel.add(gameTitle);
		
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
			VideojuegoDTO videojuegoDTO = new VideojuegoDTO();
			
			clienteDTO.setAdmin(false);
			clienteDTO.setAmonestaciones(cliente.getAmonestaciones());
			clienteDTO.setDni(cliente.getDni());
			clienteDTO.setNombre(cliente.getNombre());
			
			videojuegoDTO.setTitulo(videojuego.getTitulo());
			videojuegoDTO.setSinopsis(videojuego.getSinopsis());
			videojuegoDTO.setPrecio(videojuego.getPrecio());
			videojuegoDTO.setRating(videojuego.getRating());
			videojuegoDTO.setGenero(videojuego.getGenero());
			videojuegoDTO.setTipo(videojuego.getTipo());
			videojuegoDTO.setAutor(videojuego.getAutor());
			
			Review review = new Review(videojuegoDTO, clienteDTO, comment, rating);
			*/
			
			// Opción temporal sin DTOs (ajusta según tu constructor de Review)
			Review review = new Review(videojuego, cliente, comment, rating);
			
			videojuego.getComentarios().add(review);
			
			// TODO: Implementar cuando tengas el DAO
			// main.getReviewDAO().addReview(review);

			dispose();
			VentanaInformacionProducto redirectWindow = new VentanaInformacionProducto(videojuego);
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
		ImageIcon foto = Utils.loadImage("videojuegos.png", 115, 160);
		
		// Constructor correcto: titulo, sinopsis, precio, rating, comentarios, genero, tipo, autor, foto
		Videojuego videojuego = new Videojuego("Titulo1", "Sinopsis", 59.99f, 0, new ArrayList<Review>(), 
											   GeneroVideoJuego.ACCION, TipoConsola.PS4, "Desarrollador1", foto);
		new VentanaAñadirReviewVideojuego(videojuego);
	}

}