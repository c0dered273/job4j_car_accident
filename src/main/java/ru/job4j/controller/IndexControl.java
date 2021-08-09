package ru.job4j.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexControl {

    @GetMapping("/")
    public String index(Model model) {
        List<String> lst = Arrays.asList(
                "Row 0", "Row 1", "Row 2", "Row 3", "Row 4"
        );
        model.addAttribute("list", lst);
        return "index";
    }
}
