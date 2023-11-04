package demobackend.demobackend.handler;

import demobackend.demobackend.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class HandleGlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> handleInvalidValueProperty(MethodArgumentNotValidException arg){
        HashMap<String,String> errors = new HashMap<>();
        arg.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField(),error.getDefaultMessage()));
        return errors;
    }

    @ExceptionHandler(EmailDoesNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> handleEmailDoesNotExistException(EmailDoesNotExistException ex){
        HashMap<String,String> hashMap= new HashMap<>();
        hashMap.put("error",ex.getMessage());
        return hashMap;
    }

    @ExceptionHandler(EmailFailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> handleEmailFailException(EmailFailException ex){
        HashMap<String,String> hashMap= new HashMap<>();
        hashMap.put("error",ex.getMessage());
        return hashMap;
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> handleUserAlreadyExistException(UserAlreadyExistException ex){
        HashMap<String,String> hashMap= new HashMap<>();
        hashMap.put("error",ex.getMessage());
        return hashMap;
    }

    @ExceptionHandler(UserNotVerifiedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> handleUserNotVerifiedException(UserNotVerifiedException ex){
        HashMap<String,String> hashMap= new HashMap<>();
        hashMap.put("error",ex.getMessage());
        return hashMap;
    }

    @ExceptionHandler(OutOfStock.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> handleOutOfStockException(OutOfStock ex){
        HashMap<String,String> hashMap= new HashMap<>();
        hashMap.put("error",ex.getMessage());
        return hashMap;
    }

    @ExceptionHandler(ProductDoesNotExist.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> handleProductDoesNotExistException(ProductDoesNotExist ex){
        HashMap<String,String> hashMap= new HashMap<>();
        hashMap.put("error",ex.getMessage());
        return hashMap;
    }
}
