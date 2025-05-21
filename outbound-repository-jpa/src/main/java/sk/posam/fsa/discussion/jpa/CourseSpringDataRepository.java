package sk.posam.fsa.discussion.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.posam.fsa.discussion.Course;

public interface CourseSpringDataRepository extends JpaRepository<Course, Long> {
}
