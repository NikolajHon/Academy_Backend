package sk.posam.fsa.discussion;

import sk.posam.fsa.discussion.rest.dto.CreateCourseRequestDto;

import java.util.List;

public interface CourseRepository {
    Course create(Course course);
    List<Course> findAll();
    Course getCourse(Long courseId);
}
