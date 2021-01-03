package dsbd2020.project.productmanager.exception;

public class CategoryNotFoundException extends RuntimeException{

    public CategoryNotFoundException(Integer id){
        super("Category with id: " + id + " not found");
    }
}
