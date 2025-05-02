package com.example.OnlineStore.controllers;

import com.example.OnlineStore.models.User;
import com.example.OnlineStore.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login-page";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration-page";
    }

    @PostMapping("/registration")
    public String registration(User user, Model model) {
        if (userService.createUser(user)) {
            return "redirect:/login";
        }
        model.addAttribute("errorMessage", String.format("Користувач з логіном %s вже існує!", user.getUsername()));
        System.out.println("User with username: " + user.getUsername() + " already exists");
        return "registration-page";
    }
}
