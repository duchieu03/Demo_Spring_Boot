package demobackend.demobackend.exception;

public class EmailDoesNotExistException extends Exception{
    public EmailDoesNotExistException(String s) {
        super(s);
    }
}
