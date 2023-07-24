package ee.katrina.webshop.exception;

public class NotEnoughInStockException extends Exception {
    public NotEnoughInStockException(String s) {
        super(s);
    }
}
