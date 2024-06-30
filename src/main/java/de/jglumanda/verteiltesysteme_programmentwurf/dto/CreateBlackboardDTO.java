package de.jglumanda.verteiltesysteme_programmentwurf.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data transfer object for creating a blackboard
 */
@Data
public class CreateBlackboardDTO {
    @NotBlank(message = "Name should not be blank")
    @Size(min = 3, max = 50, message = "Name should be between 3 and 50 characters")
    private String name;
    @PositiveOrZero(message = "Validity should be positive or zero")
    private long validityInSeconds;
}