package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.repository.ArticleRepository;

import javax.sql.DataSource;
import java.sql.*;

public class ArticleRepositoryImpl extends BasicRepositoryImpl<Article> implements ArticleRepository {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

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
        save("userId", article.getUserId(), "title", article.getTitle(), "text", article.getText(), "creationTime", null);
    }
}
