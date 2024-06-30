package de.jglumanda.verteiltesysteme_programmentwurf.exception;

/**
 * Exception that is thrown when a resource is not found
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
