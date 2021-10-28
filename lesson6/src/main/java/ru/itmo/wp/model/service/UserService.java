package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.EventRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @noinspection UnstableApiUsage*/
public class UserService {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final EventRepository eventRepository = new EventRepositoryImpl();
    private static final String PASSWORD_SALT = "177d4b5f2e4f4edafa7404533973c04c513ac619";

    public void validateRegistration(User user, String password, String passwordConfirmation) throws ValidationException {
        if (Strings.isNullOrEmpty(user.getLogin())) {
            throw new ValidationException("Login is required");
        }
        if (!user.getLogin().matches("[a-z]+")) {
            throw new ValidationException("Login can contain only lowercase Latin letters");
        }
        if (user.getLogin().length() > 8) {
            throw new ValidationException("Login can't be longer than 8 letters");
        }
        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new ValidationException("Login is already in use");
        }

        if (Strings.isNullOrEmpty(user.getEmail())) {
            throw new ValidationException("Email is required");
        }
        if (!user.getEmail().matches("^[^@]+@[^@]+")) {
            throw new ValidationException("Email should contain one '@' and other characters");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new ValidationException("Email is already in use");
        }

        if (Strings.isNullOrEmpty(password)) {
            throw new ValidationException("Password is required");
        }
        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4 characters");
        }
        if (password.length() > 12) {
            throw new ValidationException("Password can't be longer than 12 characters");
        }

        if (!password.equals(passwordConfirmation)) {
            throw new ValidationException("Passwords don't match");
        }
    }

    public void register(User user, String password) {
        userRepository.save(user, getPasswordSha(password));
    }

    private String getPasswordSha(String password) {
        return Hashing.sha256().hashBytes((PASSWORD_SALT + password).getBytes(StandardCharsets.UTF_8)).toString();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void validateEnter(String loginOrEmail, String password) throws ValidationException {
        String nameLoginOrEmail = setLoginOrEmail(loginOrEmail);
        Map<String, String> parameters = new HashMap<>();
        parameters.put(nameLoginOrEmail, loginOrEmail);
        parameters.put("passwordSha", getPasswordSha(password));
        User user = userRepository.findBy(parameters);
        if (user == null) {
            throw new ValidationException("Invalid " + nameLoginOrEmail + " or password");
        }
    }

    public User findByLoginOrEmailAndPassword(String loginOrEmail, String password) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(setLoginOrEmail(loginOrEmail), loginOrEmail);
        parameters.put("passwordSha", getPasswordSha(password));
        return userRepository.findBy(parameters);
    }

    private String setLoginOrEmail(String loginOrEmail) {
        return loginOrEmail.contains("@") ? "email" : "login";
    }

    public long findCount() {
        return userRepository.findCount();
    }

    public void enter(User user) {
        Event event = new Event();
        event.setUserId(user.getId());
        event.setType(Event.TYPES.ENTER);
        eventRepository.save(event);
    }

    public void logout(User user) {
        Event event = new Event();
        event.setUserId(user.getId());
        event.setType(Event.TYPES.LOGOUT);
        eventRepository.save(event);
    }
}
