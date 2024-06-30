package de.jglumanda.verteiltesysteme_programmentwurf.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Response that is sent when an error occurs
 */
@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private List<String> details;
}
