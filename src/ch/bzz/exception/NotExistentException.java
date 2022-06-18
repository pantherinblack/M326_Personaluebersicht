package ch.bzz.exception;

public class NotExistentException extends Error{
    public NotExistentException(String message) {
        super(message);
    }

    public NotExistentException() {
        this("This element does not exist!");
    }
}
