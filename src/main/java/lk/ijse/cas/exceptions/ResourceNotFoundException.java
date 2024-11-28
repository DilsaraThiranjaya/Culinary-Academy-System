package lk.ijse.cas.exceptions;

// Custom exception to handle IOException
public class ResourceNotFoundException extends CustomException {

    // Constructor with a custom message
    public ResourceNotFoundException(String message) {
        super(message);
    }

    // Constructor with a custom message and a cause (original IOException)
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
