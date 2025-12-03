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

public class VentanaAñadirReviewVideojuego extends JFrame {

	private static final long serialVersionUID = 1L;
	private Cliente cliente = (Cliente) main.getUsuario();
	private int rating = 0;
	private String comentario = "";

	public VentanaAñadirReviewVideojuego(Videojuego videojuego) {
		
		setTitle("Anadir review");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 400);
		setLocationRelativeTo(null);
		
		JLabel tituloLabel = new JLabel("Añadir review", SwingConstants.CENTER);
		tituloLabel.setFont(new Font("Verdana", Font.BOLD, 32));
		
		JPanel panelIzquierdo = new JPanel();
		panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
		
		JLabel iconoJuego = new JLabel();
		iconoJuego.setIcon(videojuego.getFoto());
		JLabel gameTitle = new JLabel(videojuego.getTitulo());	
		
		iconoJuego.setAlignmentX(CENTER_ALIGNMENT);
		gameTitle.setAlignmentX(CENTER_ALIGNMENT);
		
		panelIzquierdo.add(iconoJuego);
		panelIzquierdo.add(gameTitle);
		
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
			

			JDialog dialogoPorceso = new JDialog(this, "Publicando review...", true);
			dialogoPorceso.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			dialogoPorceso.setSize(400, 100);
			dialogoPorceso.setLocationRelativeTo(this);
			
			JProgressBar progressBar = new JProgressBar(0, 100);
			progressBar.setValue(0);
			progressBar.setStringPainted(true);
			
			JPanel panelProgresoBarra = new JPanel(new BorderLayout());
			panelProgresoBarra.setBorder(new EmptyBorder(20, 20, 20, 20));
			panelProgresoBarra.add(progressBar, BorderLayout.CENTER);
			
			dialogoPorceso.add(panelProgresoBarra);
			

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

					dialogoPorceso.dispose();

					Review review = new Review(videojuego, cliente, comentario, rating);
					videojuego.getComentarios().add(review);
					
					dispose();
					VentanaInformacionProducto redirigirVentana = new VentanaInformacionProducto(videojuego);
					JOptionPane.showMessageDialog(redirigirVentana, "Gracias por tu review!", 
						"Review publicada correctamente", JOptionPane.INFORMATION_MESSAGE);
				}
			};
			worker.execute();
			dialogoPorceso.setVisible(true);
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

		ImageIcon foto = Utils.loadImage("videojuegos.png", 115, 160);
		
		Videojuego videojuego = new Videojuego(
            1L,
            "Titulo1", 
            "Sinopsis", 
            59.99f, 
            0, 
            new ArrayList<Review>(), 
            GeneroVideoJuego.ACCION, 
            TipoConsola.PS4, 
            "Desarrollador1", 
            foto
        );
		new VentanaAñadirReviewVideojuego(videojuego);
	}
}