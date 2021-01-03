package dsbd2020.project.productmanager.exception;

public class WrongRequestBodyException extends RuntimeException{

    public WrongRequestBodyException(String msg){
        super("Error in request body: " + msg);
    }
}
