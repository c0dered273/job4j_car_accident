package ru.job4j.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.model.Accident;


public interface AccidentRepository extends CrudRepository<Accident, Long> {

    @EntityGraph(value = "accident-type-rules")
    List<Accident> findAll();

    @EntityGraph(value = "accident-type-rules")
    Optional<Accident> findById(long id);

}
