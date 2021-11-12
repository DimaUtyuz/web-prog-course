package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

/** @noinspection unused*/
public class MyArticlesPage {
    private final ArticleService articleService = new ArticleService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (view.get("user") == null) {
            throw new RedirectException("/index");
        }
        User user = (User) view.get("user");
        view.put("myArticles", articleService.findByUserId(user.getId()));
    }

    private void swap(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        long id;
        try {
            id = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid article");
        }
        Article article = articleService.find(id);
        User user = (User) view.get("user");
        if (article == null || user == null || article.getUserId() != user.getId()) {
            throw new ValidationException("Invalid article");
        }
        articleService.setHidden(article.getId(), !article.getHidden());
    }
}
