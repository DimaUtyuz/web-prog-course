package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

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
        if (Strings.isNullOrEmpty(article.getText())) {
            throw new ValidationException("The text is empty");
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
}
