package sk.posam.fsa.discussion.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import sk.posam.fsa.discussion.Course;
import sk.posam.fsa.discussion.CourseRepository;
import sk.posam.fsa.discussion.User;
import sk.posam.fsa.discussion.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class UserService implements UserFacade {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CourseRepository courseRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
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


}
