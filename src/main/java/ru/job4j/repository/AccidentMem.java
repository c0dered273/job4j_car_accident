package ru.job4j.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Accident;

@Repository
public class AccidentMem {

    private final HashMap<Long, Accident> accidents = new HashMap<>();
    private long id;

    public void save(Accident accident) {
        accident.setId(id++);
        accidents.put(accident.getId(), accident);
    }

    public void update(Accident accident) {
        accidents.put(accident.getId(), accident);
    }

    public Optional<Accident> findById(long id) {
        return Optional.of(accidents.get(id));
    }

    public List<Accident> findAll() {
        return new ArrayList<>(accidents.values());
    }
}
