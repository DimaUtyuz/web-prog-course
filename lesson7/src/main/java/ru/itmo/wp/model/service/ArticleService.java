package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** @noinspection UnstableApiUsage*/
public class ArticleService {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public void validateArticle(Article article) throws ValidationException {
        if (userRepository.find(article.getUserId()) == null) {
            throw new ValidationException("The user doesn't exist");
        }
        if (Strings.isNullOrEmpty(article.getTitle())) {
            throw new ValidationException("The title is empty");
        }
        if (article.getText() == null) {
            throw new ValidationException("The text is empty");
        }
        if (article.getTitle().length() > 255) {
            throw new ValidationException("The title is too long");
        }
        if (article.getText().length() > 255) {
            throw new ValidationException("The text is too long");
        }
    }

    public void create(Article article) {
        articleRepository.save(article);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Article find(long id) {
        return articleRepository.find(id);
    }

    public List<Article> findByUserId(long userId) {
        return articleRepository.findByUserId(userId);
    }

    public void setHidden(long id, boolean hidden) {
        articleRepository.setHidden(id, hidden);
    }
}
