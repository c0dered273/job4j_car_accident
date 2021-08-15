package ru.job4j.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.model.Rule;
import ru.job4j.repository.AccidentRepository;
import ru.job4j.repository.AccidentTypeRepository;
import ru.job4j.repository.RuleRepository;

@Service
@RequiredArgsConstructor
public class AccidentService {

    final Logger logger = LoggerFactory.getLogger(AccidentService.class);

    private final AccidentRepository accidentRepo;
    private final AccidentTypeRepository accidentTypeRepo;
    private final RuleRepository ruleRepo;

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

    @Transactional
    public void saveOrUpdate(Accident accident, String[] ids) {
        accident.setRules(getRulesOfStringIds(ids));
        accidentRepo.save(checkAndSetTypeName(accident));
    }

    public List<AccidentType> getAllTypes() {
        List<AccidentType> result = new ArrayList<>();
        accidentTypeRepo.findAll().forEach(result::add);
        return result;
    }

    public AccidentType getTypeById(long id) {
        return accidentTypeRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Rule> getAllRules() {
        List<Rule> result = new ArrayList<>();
        ruleRepo.findAll().forEach(result::add);
        return result;
    }

    public Set<Rule> getRulesOfStringIds(String[] ids) {
        Set<Rule> accidentRules = new HashSet<>();
        Arrays.stream(ids).forEach(id -> accidentRules.add(getRuleById(id)));
        return accidentRules;
    }

    public Rule getRuleById(String strId) {
        long id = 0;
        try {
            id = Long.parseLong(strId);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return ruleRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
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
