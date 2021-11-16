package ru.itmo.wp.lesson8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.wp.lesson8.service.UserService;

@Controller
public class UserPage extends Page {
    private final UserService userService;

    public UserPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{userId}")
    public String user(@PathVariable String userId, Model model) {
        Long id = null;
        try {
            id = Long.parseLong(userId);
        } catch (NumberFormatException ignored) {
            // No operations.
        }
        model.addAttribute("userPage", userService.findById(id));
        return "UserPage";
    }
}
