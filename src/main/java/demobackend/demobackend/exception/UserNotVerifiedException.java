package demobackend.demobackend.exception;

public class UserNotVerifiedException extends Exception {

    /** Did we send a new email? */
    private String error;

    /**
     * Constructor.
     * @param newEmailSent Was a new email sent?
     */
    public UserNotVerifiedException(String newEmailSent) {
        super(newEmailSent);
    }



}
