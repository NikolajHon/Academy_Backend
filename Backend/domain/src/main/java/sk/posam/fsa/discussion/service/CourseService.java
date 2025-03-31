package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.Course;
import sk.posam.fsa.discussion.CourseRepository;
import sk.posam.fsa.discussion.rest.dto.CreateCourseRequestDto;

import java.util.Collection;
import java.util.List;

public class CourseService implements CourseFacade{
    private final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<Course> readAll() {
        return repository.findAll();
    }

    @Override
    public void create(Course course) {
        repository.create(course);
    }
}
