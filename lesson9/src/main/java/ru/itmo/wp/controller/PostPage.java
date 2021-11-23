package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }

    @Guest
    @GetMapping("/post/{postId}")
    public String post(@PathVariable String postId, Model model) {
        Long id = null;
        try {
            id = Long.parseLong(postId);
        } catch (NumberFormatException ignored) {
            // No operations.
        }
        model.addAttribute("postPage", postService.findById(id));
        if (!model.containsAttribute("comment")) {
            model.addAttribute("comment", new Comment());
        }
        return "PostPage";
    }

    @Guest
    @PostMapping("/post/{postId}")
    public String commentPost(@PathVariable String postId,
                              @Valid @ModelAttribute("comment") Comment comment,
                              BindingResult bindingResult, HttpSession httpSession, Model model) {
        if (bindingResult.hasErrors()) {
            return post(postId, model);
        }
        if (getUser(httpSession) == null) {
            putMessage(httpSession, "You are not logged-in");
            return "redirect:/post/" + postId;
        }
        long id;
        try {
            id = Long.parseLong(postId);
        } catch (NumberFormatException ignored) {
            putMessage(httpSession, "Post is not exist");
            return "redirect:/post/" + postId;
        }
        comment.setUser(getUser(httpSession));
        postService.commentPost(postService.findById(id), comment);
        putMessage(httpSession, "Congrats, you have been commented!");

        return "redirect:/post/" + postId;
    }
}
