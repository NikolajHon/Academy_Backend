package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.Course;
import sk.posam.fsa.discussion.repository.CourseRepository;

import java.util.Collection;

public class CourseService implements CourseFacade{
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository repository) {
        this.courseRepository = repository;
    }

    @Override
    public Course getCourse(Long courseId) {
        return courseRepository.getCourse(courseId);
    }

    @Override
    public Collection<Course> readAll() {
        return courseRepository.findAll();
    }

    @Override
    public void create(Course course) {
        courseRepository.create(course);
    }
}
