package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.form.PostCredentials;
import ru.itmo.wp.repository.PostRepository;
import ru.itmo.wp.repository.TagRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    public PostService(PostRepository postRepository, TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreationTimeDesc();
    }

    public Post findById(Long id) {
        return id == null ? null : postRepository.findById(id).orElse(null);
    }

    public void commentPost(Post post, Comment comment) {
        post.addComment(comment);
        postRepository.save(post);
    }

    public Post createPost(PostCredentials postCredentials) {
        Post post = new Post();
        post.setTitle(postCredentials.getTitle());
        post.setText(postCredentials.getText());
        post.setTags(new HashSet<>());
        for (String tagName: postCredentials.getTags().trim().split("\\s+")) {
            tagName = tagName.toLowerCase();
            if (tagRepository.countByName(tagName) == 0) {
                tagRepository.save(new Tag(tagName));
            }
            Tag tag = tagRepository.findByName(tagName);
            post.addTag(tag);
        }
        return post;
    }
}
