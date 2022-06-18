package ch.bzz.exception;

/**
 * Exception to be thrown, when an element given py a parameter already exist and must be unique
 *
 * @author Kevin
 * @version 1.0
 * @since 18.005.2022
 */
public class DuplicateEntryException extends Error {

    /**
     * Constructor for the Exception with a definable message
     *
     * @param message to be shown
     */
    public DuplicateEntryException(String message) {
        super(message);
    }

    /**
     * Constructor for the Exception with a predefined message
     */
    public DuplicateEntryException() {
        super("This element already exists!");
    }
}
