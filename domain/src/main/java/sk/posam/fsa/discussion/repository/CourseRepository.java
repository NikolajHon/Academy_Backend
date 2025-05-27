package sk.posam.fsa.discussion.repository;


import sk.posam.fsa.discussion.Course;
import sk.posam.fsa.discussion.Lesson;

import java.util.List;

public interface CourseRepository {
    Course create(Course course);
    List<Course> findAll();
    Course getCourse(Long courseId);
}
