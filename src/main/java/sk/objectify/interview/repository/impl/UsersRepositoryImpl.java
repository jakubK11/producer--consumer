package sk.objectify.interview.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sk.objectify.interview.repository.User;
import sk.objectify.interview.repository.UsersRepository;

public class UsersRepositoryImpl implements UsersRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersRepositoryImpl.class);
    private Connection connection;

    public UsersRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(User user) {
        String sql = "insert into SUSERS (USER_ID, USER_GUID, USER_NAME) VALUES (?,?,?);";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, user.getId());
            statement.setString(2, user.getGuid());
            statement.setString(3, user.getUsername());
            statement.execute();

            LOGGER.debug("User {} saved.", user);
        } catch (SQLException e) {
            throw new IllegalStateException("Error in sql expression", e);
        }

    }

    @Override
    public void deleteAll() {
        String sql = "delete from SUSERS;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalStateException("Error in sql expression", e);
        }

    }

    @Override
    public List<User> findAll() {
        String sql = "select * from SUSERS;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("USER_ID"));
                user.setGuid(resultSet.getString("USER_GUID"));
                user.setUsername(resultSet.getString("USER_NAME"));

                users.add(user);
            }

            return users;
        } catch (SQLException e) {
            throw new IllegalStateException("Error in sql expression", e);
        }
    }

}
