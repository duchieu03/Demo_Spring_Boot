package demobackend.demobackend.exception;

public class UserAlreadyExistException extends Exception{

    public UserAlreadyExistException(String usernameIsAlreadyInUssed) {
        super(usernameIsAlreadyInUssed);
    }
}
