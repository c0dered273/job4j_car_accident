package ru.job4j.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.model.Rule;
import ru.job4j.repository.AccidentMem;
import ru.job4j.repository.AccidentTypeMem;
import ru.job4j.repository.RuleMem;

@Component
@RequiredArgsConstructor
public class AccidentMemInit {

    private final AccidentMem accidentRepo;
    private final AccidentTypeMem accidentTypeRepo;
    private final RuleMem ruleRepo;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        accidentTypeInit();
        ruleInit();
        accidentInit();
    }

    private void accidentTypeInit() {
        accidentTypeRepo.save(AccidentType.of("Две машины"));
        accidentTypeRepo.save(AccidentType.of("Машина и человек"));
        accidentTypeRepo.save(AccidentType.of("Машина и велосипед"));
    }

    private void ruleInit() {
        ruleRepo.save(Rule.of("Статья. 1"));
        ruleRepo.save(Rule.of("Статья. 2"));
        ruleRepo.save(Rule.of("Статья. 3"));
    }

    private void accidentInit() {
        var accident1 = Accident.of(
                "Test Accident 1",
                "Text of accident 1",
                "Address of accident 1",
                accidentTypeRepo.findById(1L).get());
        accident1.setRules(new HashSet<>(List.of(ruleRepo.findById(1L).get(), ruleRepo.findById(2L).get())));
        var accident2 = Accident.of(
                "Test Accident 2",
                "Text of accident 2",
                "Address of accident 2",
                accidentTypeRepo.findById(3L).get());
        accident2.setRules(new HashSet<>(List.of(ruleRepo.findById(1L).get(), ruleRepo.findById(3L).get())));
        var accident3 = Accident.of(
                "Test Accident 3",
                "Text of accident 3",
                "Address of accident 3",
                accidentTypeRepo.findById(2L).get());
        accident3.setRules(new HashSet<>(List.of(ruleRepo.findById(1L).get())));
        var accident4 = Accident.of(
                "Test Accident 4",
                "Text of accident 4",
                "Address of accident 4",
                accidentTypeRepo.findById(3L).get());
        accident4.setRules(new HashSet<>(List.of(ruleRepo.findById(3L).get(), ruleRepo.findById(2L).get())));
        var accident5 = Accident.of(
                "Test Accident 5",
                "Text of accident 5",
                "Address of accident 5",
                accidentTypeRepo.findById(1L).get());
        accident5.setRules(new HashSet<>(List.of(ruleRepo.findById(1L).get(), ruleRepo.findById(2L).get())));
        accidentRepo.save(accident1);
        accidentRepo.save(accident2);
        accidentRepo.save(accident3);
        accidentRepo.save(accident4);
        accidentRepo.save(accident5);
    }
}
