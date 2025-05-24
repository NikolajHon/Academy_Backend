package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.Course;
import sk.posam.fsa.discussion.User;

import java.util.Collection;
import java.util.Optional;

public interface UserFacade {
    User get(long id);
    Optional<User> get(String email);
    Collection<User> readAll();
    void create(User user);
    void enrollUserToCourse(Long userId, Long courseId);
    void deleteUser(Long id);
    Optional<User> getByKeycloakId(String keycloakId);
}
