package db;

import domain.GeneroPelicula;
import domain.GeneroVideoJuego;
import domain.TipoConsola;
import domain.TipoPelicula;

public class ProductoDTO {
    private long id;
    private String titulo;
    private String sinopsis;
    private double precio;
    private int rating;
    private String urlFoto; 


    private String tipoProducto; 


    private TipoPelicula tipoPelicula;
    private GeneroPelicula generoPelicula;
    private String director;
    private int duracion; 

    private GeneroVideoJuego generoVideojuego;
    private TipoConsola tipoConsola;
    private String autorVideojuego; 

    
    public ProductoDTO() {
    }

    public ProductoDTO(long id, String titulo, String sinopsis, double precio, int rating, String urlFoto, 
                       String tipoProducto, TipoPelicula tipoPelicula, GeneroPelicula generoPelicula, 
                       String director, int duracion, GeneroVideoJuego generoVideojuego, 
                       TipoConsola tipoConsola, String autorVideojuego) {
        this.id = id;
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.precio = precio;
        this.rating = rating;
        this.urlFoto = urlFoto;
        this.tipoProducto = tipoProducto;
        this.tipoPelicula = tipoPelicula;
        this.generoPelicula = generoPelicula;
        this.director = director;
        this.duracion = duracion;
        this.generoVideojuego = generoVideojuego;
        this.tipoConsola = tipoConsola;
        this.autorVideojuego = autorVideojuego;
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

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
    
    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public TipoPelicula getTipoPelicula() {
        return tipoPelicula;
    }

    public void setTipoPelicula(TipoPelicula tipoPelicula) {
        this.tipoPelicula = tipoPelicula;
    }

    public GeneroPelicula getGeneroPelicula() {
        return generoPelicula;
    }

    public void setGeneroPelicula(GeneroPelicula generoPelicula) {
        this.generoPelicula = generoPelicula;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public GeneroVideoJuego getGeneroVideojuego() {
        return generoVideojuego;
    }

    public void setGeneroVideojuego(GeneroVideoJuego generoVideojuego) {
        this.generoVideojuego = generoVideojuego;
    }

    public TipoConsola getTipoConsola() {
        return tipoConsola;
    }

    public void setTipoConsola(TipoConsola tipoConsola) {
        this.tipoConsola = tipoConsola;
    }

    public String getAutorVideojuego() {
        return autorVideojuego;
    }

    public void setAutorVideojuego(String autorVideojuego) {
        this.autorVideojuego = autorVideojuego;
    }

    @Override
    public String toString() {
        return "ProductoDTO [id=" + id + ", titulo=" + titulo + ", tipoProducto=" + tipoProducto + "]";
    }
}