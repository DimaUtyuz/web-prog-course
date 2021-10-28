package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class BasePage {
    protected final UserService userService = new UserService();
    protected HttpServletRequest request;

    protected void action(HttpServletRequest request, Map<String, Object> view) {}

    protected void before(HttpServletRequest request, Map<String, Object> view) {
        this.request = request;

        view.put("userCount", userService.findCount());

        view.put("user", getUser());
    }

    protected void after(HttpServletRequest request, Map<String, Object> view) {

    }

    protected void putMessage(Map<String, Object> view) {
        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }

    protected void setMessage(String message) {
        request.getSession().setAttribute("message", message);
    }

    protected void setUser(User user) {
        request.getSession().setAttribute("user", user);
    }

    protected User getUser() {
        return (User) request.getSession().getAttribute("user");
    }
}
