package ru.itmo.wp.lesson8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.lesson8.form.NoticeCredentials;
import ru.itmo.wp.lesson8.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class NoticePage extends Page {
    private final NoticeService noticeService;

    public NoticePage(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/notice")
    public String noticeGet(Model model, HttpSession httpSession) {
        if (getUser(httpSession) == null) {
            setMessage(httpSession, "You are not logged-in");
            return "redirect:/";
        }

        if (getUser(httpSession).isDisabled()) {
            setMessage(httpSession, "You are disabled");
            return "redirect:/";
        }

        model.addAttribute("noticeForm", new NoticeCredentials());
        return "NoticePage";
    }

    @PostMapping("/notice")
    public String noticePost(@Valid @ModelAttribute("noticeForm") NoticeCredentials noticeForm,
                               BindingResult bindingResult,
                               HttpSession httpSession, Model model) {
        if (bindingResult.hasErrors()) {
            return "NoticePage";
        }

        if (getUser(httpSession) == null) {
            setMessage(httpSession, "You are not logged-in");
            return "redirect:/";
        }

        if (getUser(httpSession).isDisabled()) {
            setMessage(httpSession, "You are disabled");
            return "redirect:/";
        }

        noticeService.add(noticeForm);
        setMessage(httpSession, "Congrats, you have been added notice!");

        return "redirect:/";
    }
}
