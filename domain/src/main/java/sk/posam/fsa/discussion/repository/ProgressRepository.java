package sk.posam.fsa.discussion.repository;

import sk.posam.fsa.discussion.CourseProgress;
import sk.posam.fsa.discussion.CourseProgressId;

import java.util.List;
import java.util.Optional;

public interface ProgressRepository {

    Optional<CourseProgress> findById(CourseProgressId id);
    CourseProgress save(CourseProgress progress);
    void delete(CourseProgress progress);
    List<CourseProgress> findAllByCourseId(Long courseId);
}
