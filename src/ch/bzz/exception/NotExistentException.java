package ch.bzz.exception;

/**
 * Exception to be thrown, when an element given py a parameter does not exist
 *
 * @author Kevin
 * @version 1.0
 * @since 18.005.2022
 */
public class NotExistentException extends Error {

    /**
     * Constructor for the Exception with a definable message
     *
     * @param message to be shown
     */
    public NotExistentException(String message) {
        super(message);
    }

    /**
     * Constructor for the Exception with a predefined message
     */
    public NotExistentException() {
        this("This element does not exist!");
    }
}
