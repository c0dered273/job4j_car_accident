package ru.job4j.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.model.Accident;
import ru.job4j.service.AccidentService;

@Controller
@RequiredArgsConstructor
public class AccidentControl {

    private final AccidentService accidentService;

    @GetMapping("/create")
    public String create() {
        return "accident/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident) {
        accidentService.create(accident);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable String id) {
        model.addAttribute("accident", accidentService.getById(id));
        return "accident/edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute Accident accident) {
        accidentService.update(accident);
        return "redirect:/";
    }
}
