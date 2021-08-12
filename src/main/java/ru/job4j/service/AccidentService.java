package ru.job4j.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.model.Rule;
import ru.job4j.repository.AccidentJdbcTemplate;
import ru.job4j.repository.AccidentTypeJdbcTemplate;
import ru.job4j.repository.RuleJdbcTemplate;

@Service
@RequiredArgsConstructor
public class AccidentService {

    private final AccidentJdbcTemplate accidentRepo;
    private final AccidentTypeJdbcTemplate accidentTypeRepo;
    private final RuleJdbcTemplate ruleRepo;

    public List<Accident> getAllAccidents() {
        return accidentRepo.findAll();
    }

    public Accident getById(String strId) {
        long id = 0;
        try {
            id = Long.parseLong(strId);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return accidentRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    public void create(Accident accident) {
        accidentRepo.save(checkAndSetTypeName(accident));
    }

    public void update(Accident accident) {
        accidentRepo.update(checkAndSetTypeName(accident));
    }

    public List<AccidentType> getAllTypes() {
        return accidentTypeRepo.findAll();
    }

    public AccidentType getTypeById(long id) {
        List<AccidentType> types = getAllTypes();
        return  types.stream()
                .filter(type -> type.getId() == id)
                .findAny()
                .orElseThrow();
    }

    public List<Rule> getAllRules() {
        return ruleRepo.findAll();
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
