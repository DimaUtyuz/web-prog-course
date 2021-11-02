package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.UserRepository;

import java.sql.*;

public class UserRepositoryImpl extends BasicRepositoryImpl<User> implements UserRepository {
    public UserRepositoryImpl() {
        super("User");
    }

    @Override
    public User find(long id) {
        return findBy("id", id);
    }

    @Override
    public User findByLogin(String login) {
        return findBy("login", login);
    }

    @Override
    public User findByEmail(String email) {
        return findBy("email", email);
    }

    protected User toCreate(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        User user = new User();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    user.setId(resultSet.getLong(i));
                    break;
                case "login":
                    user.setLogin(resultSet.getString(i));
                    break;
                case "creationTime":
                    user.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return user;
    }

    @Override
    public void save(User user, String passwordSha) {
        save("login", user.getLogin(), "passwordSha", passwordSha, "creationTime", null, "email", user.getEmail());
    }
}
