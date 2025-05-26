package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.Course;
import sk.posam.fsa.discussion.exceptions.EducationAppException;
import sk.posam.fsa.discussion.exceptions.ResourceAlreadyExistsException;
import sk.posam.fsa.discussion.exceptions.ResourceNotFoundException;
import sk.posam.fsa.discussion.repository.CourseRepository;

import java.util.Collection;

public class CourseService implements CourseFacade {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository repository) {
        this.courseRepository = repository;
    }

    @Override
    public Course getCourse(Long courseId) {
        Course course = courseRepository.getCourse(courseId);
        if (course == null) {
            throw new ResourceNotFoundException(
                    "Course with id=" + courseId + " not found"
            );
        }
        return course;
    }

    @Override
    public Collection<Course> readAll() {
        return courseRepository.findAll();
    }

    @Override
    public void create(Course course) {
        if (course.getId() != null && courseRepository.getCourse(course.getId()) != null) {
            throw new ResourceAlreadyExistsException(
                    "Course with id=" + course.getId() + " already exists"
            );
        }
        try {
            courseRepository.create(course);
        } catch (RuntimeException | Error e) {
            // оборачиваем в ваше unchecked-исключение
            throw new EducationAppException("Failed to create course", e);
        }
    }

}
