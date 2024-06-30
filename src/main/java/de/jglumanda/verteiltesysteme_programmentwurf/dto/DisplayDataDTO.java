package de.jglumanda.verteiltesysteme_programmentwurf.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data transfer object for displaying data
 */
@Data
public class DisplayDataDTO {

    @NotBlank(message = "Data should not be blank")
    @Size(min = 1, max = 1000, message = "Data should be between 1 and 1000 characters")
    private String data;
}
