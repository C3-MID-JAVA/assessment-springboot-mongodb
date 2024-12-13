package co.com.sofka.cuentabancaria.config.exceptions;

public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }

    // Constructor que recibe el mensaje de error y una causa (Throwable)
    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
