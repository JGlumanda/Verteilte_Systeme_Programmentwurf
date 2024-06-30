package de.jglumanda.verteiltesysteme_programmentwurf.exception;

/**
 * Exception that is thrown when a resource is not found
 */
public class BlackboardAlreadyExistsException extends RuntimeException {
    public BlackboardAlreadyExistsException(String message) {
        super(message);
    }
}