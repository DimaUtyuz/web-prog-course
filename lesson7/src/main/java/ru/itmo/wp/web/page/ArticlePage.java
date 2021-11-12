package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class ArticlePage {
    private final ArticleService articleService = new ArticleService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (view.get("user") == null) {
            throw new RedirectException("/index");
        }
    }

    private void create(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        Article article = new Article();
        if (view.get("user") == null) {
            throw new ValidationException("Invalid user");
        }

        User user = (User) view.get("user");
        article.setUserId(user.getId());

        article.setTitle(request.getParameter("title"));
        article.setText(request.getParameter("text"));
        article.setHidden(true);

        articleService.validateArticle(article);
        articleService.create(article);

        request.getSession().setAttribute("message", "The article has been created");
        throw new RedirectException("/index");
    }
}
