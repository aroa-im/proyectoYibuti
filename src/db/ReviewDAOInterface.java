package db;

import java.util.ArrayList;

import domain.Review;

public interface ReviewDAOInterface {	
	boolean addReview(Review review);
	ArrayList<Review> getReviewsByUsuarioDni(String dniCliente);
	void borrarRegistros();
	ArrayList<Review> getReviewsProductoById(Long idProducto);
	long generarIdUnico();
}
