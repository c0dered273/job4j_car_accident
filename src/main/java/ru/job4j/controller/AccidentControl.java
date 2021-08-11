package ru.job4j.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.service.AccidentService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AccidentControl {

    private final AccidentService accidentService;

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("types", accidentService.getAllTypes());
        return "accident/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident) {
        accidentService.create(accident);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam String id) {
        model.addAttribute("accident", accidentService.getById(id));
        model.addAttribute("types", accidentService.getAllTypes());
        return "accident/edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute Accident accident) {
        accidentService.update(accident);
        return "redirect:/";
    }
}
