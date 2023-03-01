package _20220219_MyShell;

public class NotCreateCommandException extends RuntimeException {
    public NotCreateCommandException() {
        super();
    }

    public NotCreateCommandException(String message) {
        super(message);
    }
}
