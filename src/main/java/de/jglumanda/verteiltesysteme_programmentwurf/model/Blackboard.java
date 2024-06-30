package de.jglumanda.verteiltesysteme_programmentwurf.model;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "blackboards")
public class Blackboard {
    @MongoId
    private String name;
    private String data;
    private long validityInSeconds;
    private long lastUpdated;
    private Status status;
}