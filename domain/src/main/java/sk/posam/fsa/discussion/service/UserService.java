package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.Course;
import sk.posam.fsa.discussion.User;
import sk.posam.fsa.discussion.exceptions.EducationAppException;
import sk.posam.fsa.discussion.exceptions.ResourceAlreadyExistsException;
import sk.posam.fsa.discussion.exceptions.ResourceNotFoundException;
import sk.posam.fsa.discussion.repository.CourseRepository;
import sk.posam.fsa.discussion.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class UserService implements UserFacade {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public UserService(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository   = userRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public User get(long id) {
        User user;
        try {
            user = userRepository.get(id);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException("Failed to fetch user id=" + id, e);
        }
        if (user == null) {
            throw new ResourceNotFoundException("User with id=" + id + " not found");
        }
        return user;
    }

    @Override
    public Optional<User> get(String email) {
        try {
            return userRepository.get(email);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException("Failed to fetch user by email=" + email, e);
        }
    }

    @Override
    public Collection<User> readAll() {
        try {
            return userRepository.readAll();
        } catch (RuntimeException | Error e) {
            throw new EducationAppException("Failed to read all users", e);
        }
    }

    @Override
    public void create(User user) {
        try {
            Optional<User> existing = userRepository.get(user.getEmail());
            if (existing.isPresent()) {
                throw new ResourceAlreadyExistsException(
                        "User with email=" + user.getEmail() + " already exists"
                );
            }
            userRepository.create(user);
        } catch (ResourceAlreadyExistsException raee) {
            throw raee;
        } catch (RuntimeException | Error e) {
            throw new EducationAppException("Failed to create user", e);
        }
    }

    @Override
    public void enrollUserToCourse(Long userId, Long courseId) {
        User user = get(userId);
        Course course;
        try {
            course = courseRepository.getCourse(courseId);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException("Failed to fetch course id=" + courseId, e);
        }
        if (course == null) {
            throw new ResourceNotFoundException("Course with id=" + courseId + " not found");
        }
        List<Course> courses = user.getCourses();
        if (courses.contains(course)) {
            throw new ResourceAlreadyExistsException(
                    "User id=" + userId + " is already enrolled in course id=" + courseId
            );
        }
        courses.add(course);
        user.setCourses(courses);
        try {
            userRepository.create(user);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException(
                    "Failed to enroll user id=" + userId + " to course id=" + courseId, e
            );
        }
    }

    @Override
    public void deleteUser(Long id) {
        get(id);
        try {
            userRepository.delete(id);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException("Failed to delete user id=" + id, e);
        }
    }

    @Override
    public Optional<User> getByKeycloakId(String keycloakId) {
        try {
            return userRepository.getByKeycloakId(keycloakId);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException(
                    "Failed to fetch user by keycloakId=" + keycloakId, e
            );
        }
    }
}
