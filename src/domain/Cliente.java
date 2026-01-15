package domain;

import java.util.ArrayList;
import db.UsuarioDTO;

public class Cliente extends Usuario {

    private ArrayList<Producto> historial;
    private ArrayList<Review> listaReviews;
    private int amonestaciones;


    public Cliente(String dni, String nombre, String email, String contrasena,
                   ArrayList<Producto> historial,
                   ArrayList<Review> listaReviews,
                   int amonestaciones) {

        super(dni, nombre, email, contrasena);
        this.historial = historial;
        this.listaReviews = listaReviews;
        this.amonestaciones = amonestaciones;
    }

    public Cliente() {
        super();
        this.historial = new ArrayList<>();
        this.listaReviews = new ArrayList<>();
        this.amonestaciones = 0;
    }


    public Cliente(UsuarioDTO usuarioDTO) {
        super(usuarioDTO);
        this.historial = new ArrayList<>();
        this.listaReviews = new ArrayList<>();
        this.amonestaciones = usuarioDTO.getAmonestaciones();
    }

    public ArrayList<Producto> getHistorial() {
        return historial;
    }

    public void setHistorial(ArrayList<Producto> historial) {
        this.historial = historial;
    }

    public ArrayList<Review> getListaReviews() {
        return listaReviews;
    }

    public void setListaReviews(ArrayList<Review> listaReviews) {
        this.listaReviews = listaReviews;
    }

    public int getAmonestaciones() {
        return amonestaciones;
    }

    public void setAmonestaciones(int amonestaciones) {
        this.amonestaciones = amonestaciones;
    }

    @Override
    public String toString() {
        return "Cliente [dni=" + getDni() +
               ", nombre=" + getNombre() +
               ", email=" + getEmail() +
               ", amonestaciones=" + amonestaciones + "]";
    }
}