package sk.objectify.interview.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import sk.objectify.interview.h2.H2Helper;
import sk.objectify.interview.repository.User;
import sk.objectify.interview.repository.UsersRepository;

public class UsersRepositoryImplTest {
    Connection connection;
    UsersRepository repository;

    UsersRepositoryImplTest() throws SQLException {
        connection = H2Helper.init();
        repository = new UsersRepositoryImpl(connection);
    }

    @Test
    void testUserAdd() throws SQLException {
        // GIVEN
        User user = createUserWithId(1L);

        // WHEN
        repository.save(user);

        // THEN
        try (PreparedStatement statement = connection.prepareStatement("select * from SUSERS;")) {
            ResultSet resultSet = statement.executeQuery();

            Assertions.assertTrue(resultSet.next());

            Assertions.assertEquals(user.getId(), resultSet.getLong("USER_ID"));
            Assertions.assertEquals(user.getGuid(), resultSet.getString("USER_GUID"));
            Assertions.assertEquals(user.getUsername(), resultSet.getString("USER_NAME"));

            Assertions.assertFalse(resultSet.next());
        }
    }

    @Test
    void testFindUsers() throws SQLException {
        // GIVEN
        User user1 = createAndSaveUserWithId(1L);
        User user2 = createAndSaveUserWithId(2L);

        // WHEN
        List<User> users = repository.findAll();

        // THEN
        Assertions.assertEquals(2, users.size());
        checkEquality(user1, users.get(0));
        checkEquality(user2, users.get(1));
    }

    void checkEquality(User userA, User userB) {
        Assertions.assertEquals(userA.getId(), userB.getId());
        Assertions.assertEquals(userA.getGuid(), userB.getGuid());
        Assertions.assertEquals(userA.getUsername(), userB.getUsername());
    }

    @Test
    void testDeleteAllUsers() throws SQLException {
        // GIVEN
        createAndSaveUserWithId(1L);
        createAndSaveUserWithId(2L);

        // WHEN
        repository.deleteAll();

        // THEN
        try (PreparedStatement statement = connection.prepareStatement("select * from SUSERS;")) {
            ResultSet resultSet = statement.executeQuery();
            Assertions.assertFalse(resultSet.next());
        }
    }

    User createAndSaveUserWithId(long id) throws SQLException {
        User user = createUserWithId(id);
    
        String sql = "insert into SUSERS (USER_ID, USER_GUID, USER_NAME) VALUES (?,?,?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, user.getId());
            statement.setString(2, user.getGuid());
            statement.setString(3, user.getUsername());
            statement.execute();
        }

        return user;
    }

    User createUserWithId(long id) {
        User user = new User();
        user.setId(id);
        user.setGuid(id + "a");
        user.setUsername("userName" + id);
        return user;
    }
}
