package sk.objectify.interview.repository;

import java.util.List;

public interface UsersRepository {
    void save(User user);
    void deleteAll();
    List<User> findAll();
}
