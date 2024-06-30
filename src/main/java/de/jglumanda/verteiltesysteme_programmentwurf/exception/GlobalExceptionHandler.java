package de.jglumanda.verteiltesysteme_programmentwurf.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Global exception handler for the application
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation exceptions
     * @param ex    The exception
     * @return      The response entity
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    return fieldName + ": " + errorMessage;
                })
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse("Validation failed", errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles resource not found exceptions
     * @param ex   The exception
     * @return     The response entity
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Resource not found", List.of(ex.getMessage()));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles blackboard already exists exceptions
     * @param ex    The exception
     * @return      The response entity
     */
    @ExceptionHandler(BlackboardAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(BlackboardAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Blackboard already exists", List.of(ex.getMessage()));
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
