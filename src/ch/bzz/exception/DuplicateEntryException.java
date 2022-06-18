package ch.bzz.exception;

public class DuplicateEntryException extends Error{
    public DuplicateEntryException(String message) {
        super(message);
    }

    public DuplicateEntryException() {
        super("This element already exists!");
    }
}
