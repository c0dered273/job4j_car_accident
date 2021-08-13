package ru.job4j.repository;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.model.AccidentType;

@Repository
@RequiredArgsConstructor
public class AccidentTypeHbm implements HbmRepository {

    private final SessionFactory sf;

    public AccidentType save(AccidentType accidentType) {
        return transaction(session -> {
            session.save(accidentType);
            return accidentType;
        }, sf);
    }

    public AccidentType update(AccidentType accidentType) {
        return transaction(session -> {
            session.update(accidentType);
            return accidentType;
        }, sf);
    }

    public List<AccidentType> findAll() {
        final var sql = """
                select distinct a from ru.job4j.model.AccidentType a
                order by a.name
                """;
        return transaction(session ->
                session
                        .createQuery(sql, AccidentType.class)
                        .list(), sf);

    }

    public Optional<AccidentType> findById(long id) {
        return Optional.ofNullable(
                transaction(session ->
                        session.get(AccidentType.class, id), sf));
    }
}
