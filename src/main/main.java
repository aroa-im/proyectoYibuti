package main;

import javax.swing.SwingUtilities;

import domain.Usuario;
import gui.VentanaPortada;

public class main {
	
	private static Usuario usuario;
	
	
	
	
//	public static Usuario getUsuario() {
//		return usuario;
//	}

	public static void setUsuario(Usuario usuario) {
		main.usuario = usuario;
	}
	public static void main(String[] args) {
		usuario = null;
             
        // Inicio de la interfaz gráfica
    	SwingUtilities.invokeLater(() -> new VentanaPortada(usuario));
	}
}
