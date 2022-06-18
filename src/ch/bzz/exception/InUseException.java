package ch.bzz.exception;

/**
 * Exception to be thrown, when an element can not be deleted, to keep data integrity because it is still used somewhere else
 *
 * @author Kevin
 * @version 1.0
 * @since 18.005.2022
 */
public class InUseException extends Error {

    /**
     * Constructor for the Exception with a definable message
     *
     * @param message to be shown
     */
    public InUseException(String message) {
        super(message);
    }

    /**
     * Constructor for the Exception with a predefined message
     */
    public InUseException() {
        this("This Element can not be removed, because it is still in use");
    }
}
