package ru.job4j.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import ru.job4j.model.AccidentType;

@Repository
public class AccidentTypeMem {

    private final Map<Long, AccidentType> accidentTypes = new ConcurrentHashMap<>();
    private long id = 1L;

    public void save(AccidentType accidentType) {
        accidentType.setId(id++);
        accidentTypes.put(accidentType.getId(), accidentType);
    }

    public void update(AccidentType accidentType) {
        accidentTypes.put(accidentType.getId(), accidentType);
    }

    public Optional<AccidentType> findById(long id) {
        return Optional.of(accidentTypes.get(id));
    }

    public List<AccidentType> findAll() {
        return new ArrayList<>(accidentTypes.values());
    }
}
