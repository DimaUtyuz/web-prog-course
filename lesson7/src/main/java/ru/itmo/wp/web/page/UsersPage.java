package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class UsersPage {
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (view.get("user") != null) {
            User user = (User) view.get("user");
            view.put("user", userService.find(user.getId()));
        }
    }

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    private void findUser(HttpServletRequest request, Map<String, Object> view) {
        view.put("user", userService.find(Long.parseLong(request.getParameter("userId"))));
    }

    private void swap(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        long id;
        if (request.getParameter("id") == null || view.get("user") == null) {
            throw new ValidationException("Invalid user");
        }
        try {
            id = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid user");
        }
        User user = userService.find(id);
        User curUser = userService.find(((User) view.get("user")).getId());
        if (user == null || curUser == null || !curUser.getAdmin()) {
            throw new ValidationException("Invalid user");
        }
        userService.setAdmin(user.getId(), !user.getAdmin());
    }
}
