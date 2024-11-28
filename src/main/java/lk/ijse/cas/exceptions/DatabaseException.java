package lk.ijse.cas.exceptions;

// Custom exception to handle SQLException
public class DatabaseException extends CustomException {

    // Constructor with a custom message
    public DatabaseException(String message) {
        super(message);
    }

    // Constructor with a custom message and a cause (original SQLException)
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
