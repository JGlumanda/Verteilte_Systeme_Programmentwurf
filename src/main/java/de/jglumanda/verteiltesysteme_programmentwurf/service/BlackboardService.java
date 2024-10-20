package de.jglumanda.verteiltesysteme_programmentwurf.service;

import de.jglumanda.verteiltesysteme_programmentwurf.dto.CreateBlackboardDTO;
import de.jglumanda.verteiltesysteme_programmentwurf.dto.DisplayDataDTO;
import de.jglumanda.verteiltesysteme_programmentwurf.exception.BlackboardAlreadyExistsException;
import de.jglumanda.verteiltesysteme_programmentwurf.exception.ResourceNotFoundException;
import de.jglumanda.verteiltesysteme_programmentwurf.model.Blackboard;
import de.jglumanda.verteiltesysteme_programmentwurf.model.Status;
import de.jglumanda.verteiltesysteme_programmentwurf.repository.BlackboardRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

@AllArgsConstructor
@Service
public class BlackboardService {
    private final BlackboardRepository blackboardRepository;
    private final ConcurrentHashMap<String, ReentrantLock> lockMap = new ConcurrentHashMap<>();

    /**
     * Executes a given action with a lock for the specified blackboard name.
     * This method ensures that only one thread can perform the action on the blackboard at a time.
     * @param name      The name of the blackboard.
     * @param action    The action to be executed.
     * @return          The result of the action.
     * @param <T>       The return type of the action.
     */
    private <T> T executeWithLock(String name, Supplier<T> action) {
        ReentrantLock lock = lockMap.computeIfAbsent(name, k -> new ReentrantLock());
        lock.lock();
        try {
            return action.get();
        } finally {
            lock.unlock();
            cleanupLock(name, lock);
        }
    }

    /**
     * Executes a given action with a lock for the specified blackboard name.
     * This method ensures that only one thread can perform the action on the blackboard at a time.
     * @param name      The name of the blackboard.
     * @param action    The name of the blackboard.
     */
    private void executeWithLock(String name, Runnable action) {
        ReentrantLock lock = lockMap.computeIfAbsent(name, k -> new ReentrantLock());
        lock.lock();
        try {
            action.run();
        } finally {
            lock.unlock();
            cleanupLock(name, lock);
        }
    }

    /**
     * Creates a new blackboard.
     * @param createBlackboardDTO   Data Transfer Object containing the details of the blackboard to be created.
     * @return                      The created blackboard.
     */
    public Blackboard createBlackboard(CreateBlackboardDTO createBlackboardDTO) {
        return executeWithLock(createBlackboardDTO.getName(), () -> {
            Optional<Blackboard> existingBlackboard = blackboardRepository.findById(createBlackboardDTO.getName());
            if (existingBlackboard.isPresent()) {
                throw new BlackboardAlreadyExistsException("Blackboard with name '" + createBlackboardDTO.getName() + "' already exists");
            }

            Blackboard blackboard = new Blackboard();
            blackboard.setName(createBlackboardDTO.getName());
            blackboard.setData("");
            blackboard.setValidityInSeconds(createBlackboardDTO.getValidityInSeconds());
            blackboard.setLastUpdated(System.currentTimeMillis());
            blackboard.setStatus(Status.VALID);
            return blackboardRepository.insert(blackboard);
        });
    }

    /**
     * Displays data on the blackboard with the specified name.
     * @param name              The name of the blackboard.
     * @param displayDataDTO    Data Transfer Object containing the data to be displayed.
     * @return                  The updated blackboard.
     */
    public Blackboard displayBlackboard(String name, DisplayDataDTO displayDataDTO) {
        return executeWithLock(name, () -> {
            Blackboard blackboard = blackboardRepository.findById(name)
                    .orElseThrow(() -> new ResourceNotFoundException("Blackboard not found"));

            blackboard.setData(displayDataDTO.getData());
            blackboard.setLastUpdated(System.currentTimeMillis());
            blackboard.setStatus(Status.VALID);
            return blackboardRepository.save(blackboard);
        });
    }

    /**
     * Clears the data on the blackboard with the specified name.
     * @param name The name of the blackboard.
     */
    public void clearBlackboard(String name) {
        executeWithLock(name, () -> {
            Blackboard blackboard = blackboardRepository.findById(name)
                    .orElseThrow(() -> new ResourceNotFoundException("Blackboard not found"));

            blackboard.setData("");
            blackboard.setLastUpdated(System.currentTimeMillis());
            blackboard.setStatus(Status.INVALID);
            blackboardRepository.save(blackboard);
        });
    }

    /**
     * Reads the blackboard with the specified name.
     * @param name  The name of the blackboard.
     * @return      The read blackboard.
     */
    public Blackboard readBlackboard(String name) {
        return executeWithLock(name, () -> blackboardRepository.findById(name)
                .orElseThrow(() -> new ResourceNotFoundException("Blackboard not found")));
    }

    /**
     * Lists all blackboards.
     * @return A list of all blackboards.
     */
    public List<Blackboard> listBlackboards() {
        return blackboardRepository.findAll();
    }

    /**
     * Deletes the blackboard with the specified name.
     * @param name The name of the blackboard.
     */
    public void deleteBlackboard(String name) {
        executeWithLock(name, () -> {
            Blackboard blackboard = blackboardRepository.findById(name)
                    .orElseThrow(() -> new ResourceNotFoundException("Blackboard not found"));

            blackboardRepository.delete(blackboard);
        });
    }

    /**
     * Deletes all blackboards.
     */
    public void deleteAllBlackboards() {
        List<Blackboard> blackboards = blackboardRepository.findAll();
        for (Blackboard blackboard : blackboards) {
            executeWithLock(blackboard.getName(), () -> blackboardRepository.delete(blackboard));
        }
    }

    /**
     * Updates the status of all blackboards based on their validity period.
     */
    @Scheduled(fixedRate = 1000)
    public void updateBlackboardStatus() {
        List<Blackboard> blackboards = blackboardRepository.findAll();
        long currentTime = System.currentTimeMillis();

        for (Blackboard blackboard : blackboards) {
            executeWithLock(blackboard.getName(), () -> {
                if (blackboard.getValidityInSeconds() > 0 &&
                        currentTime - blackboard.getLastUpdated() > blackboard.getValidityInSeconds() * 1000) {
                    blackboard.setStatus(Status.INVALID);
                    blackboardRepository.save(blackboard);
                }
            });
        }
    }

    /**
     * Cleans up the lock for the specified blackboard name.
     * @param name The name of the blackboard.
     * @param lock The lock to be cleaned up.
     */
    private void cleanupLock(String name, ReentrantLock lock) {
        if (lock.tryLock()) {
            try {
                lockMap.remove(name);
            } finally {
                lock.unlock();
            }
        }
    }
}