package domain;

import java.util.ArrayList;
import java.util.Objects;

import javax.swing.ImageIcon;

import db.ProductoDTO;
import main.main;
import utils.Utils;

public abstract class Producto {

    private long id;
    private String titulo;
    private String sinopsis;
    private double precio;
    private int rating;

    private ArrayList<Review> reviews;
    private ImageIcon foto;

    public Producto(long id, String titulo, String sinopsis, double precio, int rating,
                    ArrayList<Review> comentarios, ImageIcon foto) {

        this.id = id;
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.precio = precio;
        this.rating = rating;
        this.reviews = comentarios != null ? comentarios : new ArrayList<>();
        this.foto = foto;
    }

    public Producto() {
        this.id = 0;
        this.titulo = "";
        this.sinopsis = "";
        this.precio = 0.0;
        this.rating = 0;
        this.reviews = new ArrayList<>();
        this.foto = null;
    }

    public Producto(ProductoDTO productoDTO) {

        this.id = productoDTO.getId();
        this.titulo = productoDTO.getTitulo();
        this.sinopsis = productoDTO.getSinopsis();
        this.precio = productoDTO.getPrecio();
        this.rating = productoDTO.getRating();

        this.reviews = main.getReviewDAO() != null ?
                       main.getReviewDAO().getReviewsProductoById(productoDTO.getId()) :
                       new ArrayList<>();

        this.foto = Utils.loadImage(productoDTO.getUrlFoto(), 98, 151);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public ImageIcon getFoto() {
        return foto;
    }

    public void setFoto(ImageIcon foto) {
        this.foto = foto;
    }


    @Override
    public int hashCode() {
        return Objects.hash(titulo, sinopsis, precio, rating, reviews, foto);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Producto other = (Producto) obj;

        return rating == other.rating &&
               Double.doubleToLongBits(precio) == Double.doubleToLongBits(other.precio) &&
               Objects.equals(titulo, other.titulo) &&
               Objects.equals(sinopsis, other.sinopsis) &&
               Objects.equals(reviews, other.reviews) &&
               Objects.equals(foto, other.foto);
    }

    @Override
    public String toString() {
        return "Producto [titulo=" + titulo +
               ", sinopsis=" + sinopsis +
               ", precio=" + precio +
               ", rating=" + rating +
               ", comentarios=" + reviews +
               ", foto=" + (foto != null ? "CARGADA" : "NULL") +
               "]";
    }
}