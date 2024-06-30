package de.jglumanda.verteiltesysteme_programmentwurf.repository;

import de.jglumanda.verteiltesysteme_programmentwurf.model.Blackboard;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlackboardRepository extends MongoRepository<Blackboard, String> {
}
