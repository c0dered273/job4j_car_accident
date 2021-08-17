package ru.job4j.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.model.Accident;
import ru.job4j.model.User;
import ru.job4j.repository.AuthorityRepository;
import ru.job4j.repository.UserRepository;

@Controller
@RequiredArgsConstructor
public class RegControl {

    private final PasswordEncoder encoder;
    private final UserRepository users;
    private final AuthorityRepository authorities;

    @PostMapping("/reg")
    public String save(@ModelAttribute User user, Model model) {
        if (users.existsUserByUsername(user.getUsername())) {
            String errorMessage = "User already exists";
            model.addAttribute("errorMessage", errorMessage);
            return "redirect:/reg";
        }
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorities.findByAuthority("ROLE_USER"));
        users.save(user);
        return "redirect:/login";
    }

    @GetMapping("/reg")
    public String reg(@ModelAttribute Accident accident) {
        return "reg";
    }
}
