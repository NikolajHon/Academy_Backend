package sk.posam.fsa.discussion.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.posam.fsa.discussion.CourseProgress;
import sk.posam.fsa.discussion.CourseProgressId;

import java.util.List;

public interface CourseProgressSpringDataRepository
        extends JpaRepository<CourseProgress, CourseProgressId> {
    List<CourseProgress> findAllByCourseProgressIdCourseId(Long courseId);
}