package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.CommentCredentials;
import ru.itmo.wp.form.PostCredentials;
import ru.itmo.wp.service.PostService;
import ru.itmo.wp.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("posts")
    public List<Post> findPosts() {
        return postService.findAll();
    }

    @PostMapping("posts")
    public void writePost(@RequestBody @Valid PostCredentials postCredentials, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        User user = userService.findById(postCredentials.getUserId());
        if (user == null) return;
        Post post = new Post();
        post.setTitle(postCredentials.getTitle());
        post.setText(postCredentials.getText());
        userService.writePost(user, post);
    }

    @PostMapping("comment")
    public void comment(@RequestBody @Valid CommentCredentials commentCredentials, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        User user = userService.findById(commentCredentials.getUserId());
        Post post = postService.findById(commentCredentials.getPostId());
        if (user == null || post == null) return;
        Comment comment = new Comment();
        comment.setText(commentCredentials.getText());
        comment.setUser(user);
        postService.comment(post, comment);
    }
}
