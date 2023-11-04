package demobackend.demobackend.exception;

public class EmailFailException extends Exception{
    public EmailFailException(String canNotSendMail) {
        super(canNotSendMail);
    }
}
