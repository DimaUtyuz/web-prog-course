package ru.itmo.wp.lesson8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.lesson8.domain.User;
import ru.itmo.wp.lesson8.form.NoticeCredentials;
import ru.itmo.wp.lesson8.form.UserCredentials;
import ru.itmo.wp.lesson8.form.UserStatus;
import ru.itmo.wp.lesson8.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UsersPage extends Page {
    private final UserService userService;

    public UsersPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/all")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "UsersPage";
    }

    @PostMapping("/users/all")
    public String updateStatusPost(@Valid @ModelAttribute("userStatus") UserStatus userStatus,
                              BindingResult bindingResult,
                              HttpSession httpSession, Model model) {
        if (model.getAttribute("user") == null) {
            setMessage(httpSession, "You are not logged-in");
            return users(model);
        }

        if (bindingResult.hasErrors()) {
            return users(model);
        }

        User user = (User) model.getAttribute("user");
        if (user.isDisabled()) {
            setMessage(httpSession, "You are disabled");
            return users(model);
        }

        userService.updateStatus(userStatus);
        setMessage(httpSession, "Congrats, you have been set status!");
        return users(model);
    }
}
