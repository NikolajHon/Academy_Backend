package sk.posam.fsa.discussion.repository;

import sk.posam.fsa.discussion.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {
    User get(long id);
    Optional<User> get(String email);
    Collection<User> readAll();
    void create(User user);
    void delete(long id);
}
