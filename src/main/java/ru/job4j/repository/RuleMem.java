package ru.job4j.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Rule;

//@Repository
public class RuleMem {

    private final Map<Long, Rule> rules = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(1L);

    public void save(Rule rule) {
        rule.setId(id.getAndIncrement());
        rules.put(rule.getId(), rule);
    }

    public void update(Rule rule) {
        rules.put(rule.getId(), rule);
    }

    public Optional<Rule> findById(long id) {
        return Optional.of(rules.get(id));
    }

    public List<Rule> findAll() {
        return new ArrayList<>(rules.values());
    }
}
