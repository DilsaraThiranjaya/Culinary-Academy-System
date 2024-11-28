package lk.ijse.cas.exceptions;

// Custom exception to handle ClassNotFoundException
public class ClassLoadingException extends CustomException {

    // Constructor with a custom message
    public ClassLoadingException(String message) {
        super(message);
    }

    // Constructor with a custom message and a cause (original ClassNotFoundException)
    public ClassLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
