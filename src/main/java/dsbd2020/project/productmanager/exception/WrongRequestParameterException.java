package dsbd2020.project.productmanager.exception;

public class WrongRequestParameterException extends RuntimeException{

    public WrongRequestParameterException(){
        super("The parameters entered are incorrect");
    }
}
