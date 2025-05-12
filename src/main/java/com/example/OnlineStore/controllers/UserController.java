package com.example.OnlineStore.controllers;

import com.example.OnlineStore.models.User;
import com.example.OnlineStore.services.CartService;
import com.example.OnlineStore.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final CartService cartService;

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        String errorMessage = (String) request.getSession().getAttribute("errorMessage");
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            request.getSession().removeAttribute("errorMessage");
        }
        return "login-page";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration-page";
    }

    @PostMapping("/registration")
    public String registration(User user, @RequestParam(defaultValue = "false") boolean isSeller, Model model) {

        try {
            userService.createUser(user, isSeller);
            cartService.createCart(user);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "registration-page";
        }

//        if (userService.createUser(user, isSeller)) {
//            cartService.createCart(user);
//            return "redirect:/login";
//        }
//        model.addAttribute("errorMessage", String.format("Користувач з логіном %s вже існує!", user.getUsername()));
//        System.out.println("User with username: " + user.getUsername() + " already exists");
//        return "registration-page";
    }
}
