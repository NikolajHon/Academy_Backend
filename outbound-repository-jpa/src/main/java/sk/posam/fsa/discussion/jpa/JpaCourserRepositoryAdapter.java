package sk.posam.fsa.discussion.jpa;

import org.springframework.stereotype.Repository;
import sk.posam.fsa.discussion.Course;
import sk.posam.fsa.discussion.repository.CourseRepository;

import java.util.List;

@Repository
public class JpaCourserRepositoryAdapter implements CourseRepository {

    private final CourseSpringDataRepository courseSpringDataRepository;

    public JpaCourserRepositoryAdapter(CourseSpringDataRepository courseSpringDataRepository) {
        this.courseSpringDataRepository = courseSpringDataRepository;
    }

    @Override
    public Course create(Course course) {
        return courseSpringDataRepository.save(course);
    }

    @Override
    public List<Course> findAll() {
        return courseSpringDataRepository.findAll();
    }

    @Override
    public Course getCourse(Long courseId) {
        return courseSpringDataRepository.getReferenceById(courseId);
    }
}
