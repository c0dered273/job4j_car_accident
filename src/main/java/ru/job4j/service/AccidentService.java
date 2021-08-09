package ru.job4j.service;

import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Accident;
import ru.job4j.repository.AccidentMem;

@Service
@RequiredArgsConstructor
public class AccidentService {

    private final AccidentMem accidentRepository;

    @PostConstruct
    public void init() {
        var accident1 = Accident.of("Test Accident 1", "Text of accident 1", "Address of accident 1");
        var accident2 = Accident.of("Test Accident 2", "Text of accident 2", "Address of accident 2");
        var accident3 = Accident.of("Test Accident 3", "Text of accident 3", "Address of accident 3");
        var accident4 = Accident.of("Test Accident 4", "Text of accident 4", "Address of accident 4");
        var accident5 = Accident.of("Test Accident 5", "Text of accident 5", "Address of accident 5");
        accidentRepository.save(accident1);
        accidentRepository.save(accident2);
        accidentRepository.save(accident3);
        accidentRepository.save(accident4);
        accidentRepository.save(accident5);
    }

    public List<Accident> getAllAccidents() {
        return accidentRepository.findAll();
    }
}
