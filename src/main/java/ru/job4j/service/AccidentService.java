package ru.job4j.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.model.Rule;
import ru.job4j.repository.AccidentMem;

@Service
@RequiredArgsConstructor
public class AccidentService {

    private final AccidentMem accidentRepository;

    @PostConstruct
    public void init() {
        var accident1 = Accident.of(
                "Test Accident 1",
                "Text of accident 1",
                "Address of accident 1",
                AccidentType.of(1L, "Две машины"),
                new HashSet<>(List.of(Rule.of(1, "Статья. 1"), Rule.of(2, "Статья. 2")))
        );
        var accident2 = Accident.of(
                "Test Accident 2",
                "Text of accident 2",
                "Address of accident 2",
                AccidentType.of(1L, "Машина и человек"),
                new HashSet<>(List.of(Rule.of(1, "Статья. 1"), Rule.of(3, "Статья. 3")))
        );
        var accident3 = Accident.of(
                "Test Accident 3",
                "Text of accident 3",
                "Address of accident 3",
                AccidentType.of(1L, "Машина и человек"),
                new HashSet<>(List.of(Rule.of(1, "Статья. 1")))
        );
        var accident4 = Accident.of(
                "Test Accident 4",
                "Text of accident 4",
                "Address of accident 4",
                AccidentType.of(1L, "Две машины"),
                new HashSet<>(List.of(Rule.of(3, "Статья. 3"), Rule.of(2, "Статья. 2")))
        );
        var accident5 = Accident.of(
                "Test Accident 5",
                "Text of accident 5",
                "Address of accident 5",
                AccidentType.of(1L, "Машина и велосипед"),
                new HashSet<>(List.of(Rule.of(1, "Статья. 1"), Rule.of(2, "Статья. 2")))
        );
        accidentRepository.save(accident1);
        accidentRepository.save(accident2);
        accidentRepository.save(accident3);
        accidentRepository.save(accident4);
        accidentRepository.save(accident5);
    }

    public List<Accident> getAllAccidents() {
        return accidentRepository.findAll();
    }

    public Accident getById(String strId) {
        long id = 0;
        try {
            id = Long.parseLong(strId);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return accidentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    public void create(Accident accident) {
        accidentRepository.save(checkAndSetTypeName(accident));
    }

    public void update(Accident accident) {
        accidentRepository.update(checkAndSetTypeName(accident));
    }

    public List<AccidentType> getAllTypes() {
        List<AccidentType> types = new ArrayList<>();
        types.add(AccidentType.of(1, "Две машины"));
        types.add(AccidentType.of(2, "Машина и человек"));
        types.add(AccidentType.of(3, "Машина и велосипед"));
        return types;
    }

    public AccidentType getTypeById(long id) {
        List<AccidentType> types = getAllTypes();
        return  types.stream()
                .filter(type -> type.getId() == id)
                .findAny()
                .orElseThrow();
    }

    public List<Rule> getAllRules() {
        List<Rule> rules = new ArrayList<>();
        rules.add(Rule.of(1, "Статья. 1"));
        rules.add(Rule.of(2, "Статья. 2"));
        rules.add(Rule.of(3, "Статья. 3"));
        return rules;
    }

    public Set<Rule> getRulesOfStringIds(String[] ids) {
        Set<Rule> accidentRules = new HashSet<>();
        Arrays.stream(ids).forEach(id -> accidentRules.add(getRuleById(id)));
        return accidentRules;
    }

    public Rule getRuleById(String strId) {
        List<Rule> rules = getAllRules();
        long id = 0;
        try {
            id = Long.parseLong(strId);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        long finalId = id;
        return rules.stream()
                .filter(rule -> rule.getId() == finalId)
                .findAny()
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
    }

    private Accident checkAndSetTypeName(Accident accident) {
        AccidentType type = accident.getType();
        if (type.getName() == null) {
            type = getTypeById(type.getId());
            accident.setType(type);
        }
        return accident;
    }
}
