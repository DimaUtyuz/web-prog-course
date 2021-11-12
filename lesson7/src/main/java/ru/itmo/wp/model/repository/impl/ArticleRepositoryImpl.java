package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.ArticleRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleRepositoryImpl extends BasicRepositoryImpl<Article> implements ArticleRepository {

    public ArticleRepositoryImpl() {
        super("Article");
    }

    protected Article toCreate(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Article article = new Article();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    article.setId(resultSet.getLong(i));
                    break;
                case "userId":
                    article.setUserId(resultSet.getLong(i));
                    break;
                case "title":
                    article.setTitle(resultSet.getString(i));
                    break;
                case "text":
                    article.setText(resultSet.getString(i));
                    break;
                case "creationTime":
                    article.setCreationTime(resultSet.getTimestamp(i));
                    break;
                case "hidden":
                    article.setHidden(resultSet.getBoolean(i));
                    break;
                default:
                    // No operations.
            }
        }

        return article;
    }

    @Override
    public Article find(long id) {
        return findBy("id", id);
    }

    @Override
    public void save(Article article) {
        save("userId", article.getUserId(),
                "title", article.getTitle(),
                "text", article.getText(),
                "creationTime", null,
                "hidden", article.getHidden());
    }

    public List<Article> findByUserId(long userId) {
        List<Article> articles = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Article WHERE userId=? ORDER BY creationTime DESC")) {
                statement.setLong(1, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    Article article;
                    while ((article = toCreate(statement.getMetaData(), resultSet)) != null) {
                        articles.add(article);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find Articles.", e);
        }
        return articles;
    }

    @Override
    public void setHidden(long id, boolean hidden) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE Article SET hidden=? WHERE id=?")) {
                statement.setBoolean(1, hidden);
                statement.setLong(2, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't update Article.", e);
        }
    }
}
