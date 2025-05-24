package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.Course;
import sk.posam.fsa.discussion.repository.CourseRepository;
import sk.posam.fsa.discussion.User;
import sk.posam.fsa.discussion.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class UserService implements UserFacade {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public UserService(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public User get(long id) {
        return userRepository.get(id);
    }

    @Override
    public Optional<User> get(String email) {
        return userRepository.get(email);
    }

    @Override
    public Collection<User> readAll() {
        return userRepository.readAll();
    }

    @Override
    public void create(User user) {
        userRepository.create(user);
    }
    @Override
    public void enrollUserToCourse(Long userId, Long courseId) {
        User user = userRepository.get(userId);
        Course course = courseRepository.getCourse(courseId);

        List<Course> courses = user.getCourses();
        if (!courses.contains(course)) {
            courses.add(course);
            user.setCourses(courses);
            userRepository.create(user);
        }
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(id);
    }
    @Override
    public Optional<User> getByKeycloakId(String keycloakId) {
        // Предполагаем, что UserRepository умеет искать по полю keycloakId
        return userRepository.getByKeycloakId(keycloakId);
    }


}
