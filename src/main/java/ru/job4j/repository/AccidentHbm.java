package ru.job4j.repository;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Accident;

//@Repository
@RequiredArgsConstructor
public class AccidentHbm implements HbmRepository {

    private final SessionFactory sf;

    public Accident save(Accident accident) {
        return transaction(session -> {
            session.save(accident);
            return accident;
        }, sf);
    }

    public Accident update(Accident accident) {
        return transaction(session -> {
            session.update(accident);
            return accident;
        }, sf);
    }

    public List<Accident> findAll() {
        final var sql = """
                select distinct a from ru.job4j.model.Accident a
                join fetch a.type t
                join fetch a.rules r
                order by a.name
                """;
        return transaction(session ->
                session
                        .createQuery(sql, Accident.class)
                        .list(), sf);
    }

    public Optional<Accident> findById(long id) {
        final var sql = """
                select distinct a from ru.job4j.model.Accident a
                join fetch a.type t
                join fetch a.rules r
                where a.id = :id
                """;
        return transaction(session ->
                session
                        .createQuery(sql, Accident.class)
                        .setParameter("id", id)
                        .uniqueResultOptional(), sf);
    }
}
