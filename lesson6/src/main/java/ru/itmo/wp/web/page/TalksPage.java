package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TalksPage extends BasePage {
    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        User user = getUser();
        if (user == null) {
            setMessage("You aren't logged in");
            throw new RedirectException("/index");
        }
        putUsersAndTalks(view);
    }

    protected void send(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        try {
            Talk talk = new Talk();
            try {
                talk.setSourceUserId(getUser().getId());
            } catch (NullPointerException ignored) {
                throw new ValidationException("Invalid source user login");
            }
            try {
                talk.setTargetUserId(Long.parseLong(request.getParameter("targetUserId")));
            } catch (NullPointerException | NumberFormatException ignored) {
                throw new ValidationException("Invalid target user login");
            }
            talk.setText(request.getParameter("text"));

            talkService.validateTalk(talk);
            talkService.save(talk);

            setMessage("The message has been sent");
        } finally {
            putUsersAndTalks(view);
        }
    }

    private void putUsersAndTalks(Map<String, Object> view) {
        view.put("talks", talkService.findByUserId(getUser().getId()));
        view.put("users", userService.findAll());
        view.put("userService", userService);
    }
}
