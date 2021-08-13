package ru.job4j.repository;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Rule;

@Repository
@RequiredArgsConstructor
public class RuleHbm implements HbmRepository {

    private final SessionFactory sf;

    public Rule save(Rule rule) {
        return transaction(session -> {
            session.save(rule);
            return rule;
        }, sf);
    }

    public Rule update(Rule rule) {
        return transaction(session -> {
            session.update(rule);
            return rule;
        }, sf);
    }

    public List<Rule> findAll() {
        final var sql = """
                select distinct r from ru.job4j.model.Rule r
                order by r.name
                """;
        return transaction(session ->
                session
                        .createQuery(sql, Rule.class)
                        .list(), sf);

    }

    public Optional<Rule> findById(long id) {
        return Optional.ofNullable(
                transaction(session ->
                        session.get(Rule.class, id), sf));
    }

}
