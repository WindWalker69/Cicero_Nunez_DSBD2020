package dsbd2020.project.productmanager.exception;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(Integer id){
        super("Product with id: " + id + " not found");
    }
}
