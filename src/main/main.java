package main;

import domain.Usuario;
import gui.VentanaPortada;
//import io.CargarDatosEnBBDD;
import io.CrearBBDD;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import db.ProductoDAO;
import db.ProductoDAOInterface;
import db.ReviewDAO;
import db.ReviewDAOInterface;
import db.UsuarioDAO;
import db.UsuarioDAOInterface;

public class main {
	private final static String PROPERTIES_FILE = "conf/app.properties";
	private static String driver;
	private static String connection;
	private static String nombreBD;
	
	private static Usuario usuario;
	
	private static Connection conexionBD;
	private static Logger logger;
	
	private static ArrayList<Thread> threads;
	
	private static UsuarioDAOInterface usuarioDAO;
	private static ReviewDAOInterface reviewDAO;
	private static ProductoDAOInterface productoDAO;
	
	public static UsuarioDAOInterface getUsuarioDAO() {
		return usuarioDAO;
	}
	
	public static void setUsuarioDAO(UsuarioDAOInterface usuarioDAO) {
		main.usuarioDAO = usuarioDAO;
	}
	
	public static ReviewDAOInterface getReviewDAO() {
		return reviewDAO;
	}
	
	public static void setReviewDAO(ReviewDAOInterface reviewDAO) {
		main.reviewDAO = reviewDAO;
	}
    
	public static Usuario getUsuario() {
		return usuario;
	}
	
	public static void setUsuario(Usuario usuario) {
		main.usuario = usuario;
	}
	
	public static ProductoDAOInterface getProductoDAO() {
		return productoDAO;
	}
	
	public static void setProductoDAO(ProductoDAOInterface productoDAO) {
		main.productoDAO = productoDAO;
	}
	
	public static Connection getConexionBD() {
		return conexionBD;
	}
	
	public static void setConexionBD(Connection conexionBD) {
		main.conexionBD = conexionBD;
	}
	
	public static Logger getLogger() {
		return logger;
	}
	
	public static void setLogger(Logger logger) {
		main.logger = logger;
	}
	
	public static ArrayList<Thread> getThreads() {
		return threads;
	}
	
	public static void setThreads(ArrayList<Thread> threads) {
		main.threads = threads;
	}
	
	public static void main(String[] args) {
		
		Properties properties = new Properties();
		try {
			properties.load(new FileReader(PROPERTIES_FILE));
		} catch (IOException e) {
			System.err.println("Error al abrir el fichero de propiedades:" + e);
		}
		
		driver = properties.getProperty("driver");
		connection = properties.getProperty("connection");
		nombreBD = properties.getProperty("dbName");

		try {
			Class.forName(driver);
			conexionBD = DriverManager.getConnection(connection);
			logger = Logger.getLogger("GestorPersistencia-" + nombreBD);
		} catch (ClassNotFoundException | SQLException | NullPointerException e) {
			conexionBD = null;
			if (logger != null)
				logger.log(Level.SEVERE, "Error en el .jar o en la conexión de base de datos " + nombreBD + ".db", e);
		}
		

		usuarioDAO = new UsuarioDAO();
		reviewDAO = new ReviewDAO();
		productoDAO = new ProductoDAO();

		threads = new ArrayList<>();

		new CrearBBDD();

		SwingUtilities.invokeLater(() -> new VentanaPortada());
	}
}