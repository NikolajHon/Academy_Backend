package sk.posam.fsa.discussion.jpa;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import sk.posam.fsa.discussion.CourseProgress;
import sk.posam.fsa.discussion.CourseProgressId;

import java.util.List;

public interface CourseProgressSpringDataRepository
        extends JpaRepository<CourseProgress, CourseProgressId> {
    List<CourseProgress> findAllByCourseProgressIdCourseId(Long courseId);
    @Modifying
    @Transactional
    void deleteByCourseProgressIdCourseIdAndCourseProgressIdUserId(Long courseId, Long userId);
}