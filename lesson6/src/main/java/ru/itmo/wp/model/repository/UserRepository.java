package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.User;

import java.util.List;
import java.util.Map;

public interface UserRepository {
    User find(long id);
    User findByLogin(String login);
    User findByEmail(String email);
    User findByLoginAndPasswordSha(String login, String passwordSha);
    User findBy(Map<String, String> parameters);
    List<User> findAll();
    void save(User user, String passwordSha);
}
