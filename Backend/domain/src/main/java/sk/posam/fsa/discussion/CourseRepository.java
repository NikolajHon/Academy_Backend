package sk.posam.fsa.discussion;


import java.util.List;

public interface CourseRepository {
    Course create(Course course);
    List<Course> findAll();
    Course getCourse(Long courseId);
}
