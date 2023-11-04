package demobackend.demobackend.exception;

public class ProductDoesNotExist extends RuntimeException {
    public ProductDoesNotExist(Integer id) {
        super("Product with id = " + id + " does not exist");
    }
}
