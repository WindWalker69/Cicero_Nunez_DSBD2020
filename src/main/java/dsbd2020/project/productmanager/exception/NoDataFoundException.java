package dsbd2020.project.productmanager.exception;

public class NoDataFoundException extends RuntimeException{

    public NoDataFoundException(){
        super("No data found");
    }
}
