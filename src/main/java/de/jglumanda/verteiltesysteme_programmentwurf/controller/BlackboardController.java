package de.jglumanda.verteiltesysteme_programmentwurf.controller;
import de.jglumanda.verteiltesysteme_programmentwurf.dto.CreateBlackboardDTO;
import de.jglumanda.verteiltesysteme_programmentwurf.dto.DisplayDataDTO;
import de.jglumanda.verteiltesysteme_programmentwurf.exception.ErrorResponse;
import de.jglumanda.verteiltesysteme_programmentwurf.model.Blackboard;
import de.jglumanda.verteiltesysteme_programmentwurf.service.BlackboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for the blackboard API
 */
@RestController
@RequestMapping("/api/v1/blackboard")
@AllArgsConstructor
@Tag(name = "Blackboard", description = "API for managing blackboards")
@ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
public class BlackboardController {

    private final BlackboardService blackboardService;

    @Operation(summary = "Creates a blackboard", description = "Creates a blackboard with the given name and validity")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Blackboard.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping("/create")
    public ResponseEntity<Blackboard> createBlackboard(
            @Parameter(description = "The blackboard to create", required = true)
            @Valid @RequestBody CreateBlackboardDTO createBlackboardDTO) {
        Blackboard blackboard = blackboardService.createBlackboard(createBlackboardDTO);
        return ResponseEntity.ok(blackboard);
    }

    @Operation(summary = "Displays new data on the blackboard", description = "Displays the new data of a blackboard and updates the status of the blackboard")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Blackboard.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PutMapping("/display/{name}")
    public ResponseEntity<Blackboard> displayBlackboard(
            @Parameter(description = "The name of the blackboard to display", required = true)
            @PathVariable String name,
            @Parameter(description = "The data to display", required = true)
            @Valid @RequestBody DisplayDataDTO displayDataDTO) {
        Blackboard blackboard = blackboardService.displayBlackboard(name, displayDataDTO);
        return ResponseEntity.ok(blackboard);
    }

    @Operation(summary = "Clears the blackboard", description = "Clears the data of the blackboard")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PutMapping("/clear/{name}")
    public ResponseEntity<Void> clearBlackboard(
            @Parameter(description = "The name of the blackboard to clear", required = true)
            @PathVariable String name) {
        blackboardService.clearBlackboard(name);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Reads the blackboard", description = "Reads the data of the blackboard")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Blackboard.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/read/{name}")
    public ResponseEntity<Blackboard> readBlackboard(
            @Parameter(description = "The name of the blackboard to read", required = true)
            @PathVariable String name) {
        Blackboard blackboard = blackboardService.readBlackboard(name);
        return ResponseEntity.ok(blackboard);
    }

    @Operation(summary = "Lists all blackboards", description = "Lists all blackboards")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Blackboard.class)))),
    })
    @GetMapping("/list")
    public ResponseEntity<List<Blackboard>> listBlackboards() {
        List<Blackboard> blackboards = blackboardService.listBlackboards();
        return ResponseEntity.ok(blackboards);
    }

    @Operation(summary = "Deletes a blackboard", description = "Deletes a blackboard")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/delete/{name}")
    public ResponseEntity<Void> deleteBlackboard(
            @Parameter(description = "The name of the blackboard to delete", required = true)
            @PathVariable String name) {
        blackboardService.deleteBlackboard(name);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Deletes all blackboards", description = "Deletes all blackboards")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllBlackboards() {
        blackboardService.deleteAllBlackboards();
        return ResponseEntity.ok().build();
    }
}