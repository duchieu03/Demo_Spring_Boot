package demobackend.demobackend.exception;

public class OutOfStock extends RuntimeException {
    public OutOfStock(Integer id) {
        super("Product with id = " + id + " is out of stock");
    }


}
