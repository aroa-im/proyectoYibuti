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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import utils.Utils;

public class VentanaAñadirReviewPelicula extends JFrame {

	private static final long serialVersionUID = 1L;
	private Cliente cliente = (Cliente) main.getUsuario();
	private int rating = 0;
	private String comentario = "";

	public VentanaAñadirReviewPelicula(Pelicula pelicula) {
		
		setTitle("Anadir review");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 400);
		setLocationRelativeTo(null);
		
		JLabel tituloLabel = new JLabel("Añadir review", SwingConstants.CENTER);
		tituloLabel.setFont(new Font("Verdana", Font.BOLD, 32));
		
		JPanel panelIzquierdo = new JPanel();
		panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
		
		JLabel iconoPelicula = new JLabel();
		iconoPelicula.setIcon(pelicula.getFoto());
		JLabel movieTitle = new JLabel(pelicula.getTitulo());	
		
		iconoPelicula.setAlignmentX(CENTER_ALIGNMENT);
		movieTitle.setAlignmentX(CENTER_ALIGNMENT);
		
		panelIzquierdo.add(iconoPelicula);
		panelIzquierdo.add(movieTitle);
		
		JPanel panelDerecho = new JPanel();
		panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
		
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
			comentario = comentarioTextArea.getText();

			if (rating == 0) {
				JOptionPane.showMessageDialog(this, "Por favor, selecciona una valoración", 
					"Error", JOptionPane.WARNING_MESSAGE);
				return;
			}

			JDialog dialogoPorgreso = new JDialog(this, "Publicando review...", true);
			dialogoPorgreso.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			dialogoPorgreso.setSize(400, 100);
			dialogoPorgreso.setLocationRelativeTo(this);
			
			JProgressBar progressBar = new JProgressBar(0, 100);
			progressBar.setValue(0);
			progressBar.setStringPainted(true);
			
			JPanel progressPanel = new JPanel(new BorderLayout());
			progressPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
			progressPanel.add(progressBar, BorderLayout.CENTER);
			
			dialogoPorgreso.add(progressPanel);
			

			SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
				@Override
				protected Void doInBackground() throws Exception {

					for (int i = 0; i <= 100; i++) {
						Thread.sleep(30); 
						publish(i);
					}
					return null;
				}
				
				@Override
				protected void process(List<Integer> chunks) {

					int valor = chunks.get(chunks.size() - 1);
					progressBar.setValue(valor);
				}
				
				@Override
				protected void done() {

					dialogoPorgreso.dispose();
					

					Review review = new Review(pelicula, cliente, comentario, rating);
					pelicula.getComentarios().add(review);
					

					dispose();
					VentanaInformacionRecurso redirigirVentana = new VentanaInformacionRecurso(pelicula);
					JOptionPane.showMessageDialog(redirigirVentana, "Gracias por tu review!", 
						"Review publicada correctamente", JOptionPane.INFORMATION_MESSAGE);
				}
			};
			

			worker.execute();
			dialogoPorgreso.setVisible(true);
		});
		
		comentarioLabel.setAlignmentX(CENTER_ALIGNMENT);
		comentarioTextArea.setAlignmentX(CENTER_ALIGNMENT);
		valoracionLabel.setAlignmentX(CENTER_ALIGNMENT);
		starPanel.setAlignmentX(CENTER_ALIGNMENT);
		publicarButton.setAlignmentX(CENTER_ALIGNMENT);		
				
		panelDerecho.add(comentarioLabel);
		panelDerecho.add(comentarioTextArea);
		panelDerecho.add(valoracionLabel);
		panelDerecho.add(starPanel);
		panelDerecho.add(publicarButton);
		
		comentarioLabel.setBorder(new EmptyBorder(0, 0, 5, 0));
		valoracionLabel.setBorder(new EmptyBorder(5, 0, 0, 0));
		panelIzquierdo.setBorder(new EmptyBorder(50, 70, 0, 0));
		panelDerecho.setBorder(new EmptyBorder(0, 0, 20, 70));
		starPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
		
		add(tituloLabel, BorderLayout.NORTH);
		add(panelIzquierdo, BorderLayout.WEST);
		add(panelDerecho, BorderLayout.EAST);

		setVisible(true);
	}

	public static void starAction(int starIndex, List<JLabel> starList) {

		for (int i = 0; i < 10; i++) {
			starList.get(i).setIcon(Utils.loadImage("estrellaBlanca.png", 24, 24));
		}

		for (int i = 0; i <= starIndex; i++) {
			starList.get(i).setIcon(Utils.loadImage("estrellaNegra.png", 24, 24));
		}
	}
	
	public static void main(String[] args) {

		ImageIcon foto = Utils.loadImage("peliculas.png", 115, 160);
		
		Pelicula pelicula = new Pelicula(
            1L, 
            "Titulo1", 
            "Sinopsis", 
            8.8f, 
            0, 
            new ArrayList<Review>(), 
            TipoPelicula.DVD, 
            GeneroPelicula.ACCION, 
            "Director1", 
            200, 
            foto
        );
		new VentanaAñadirReviewPelicula(pelicula);
	}
}