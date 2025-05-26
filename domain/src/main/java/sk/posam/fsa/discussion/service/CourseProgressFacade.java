// 4. Расширяем интерфейс Facade
package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.CourseProgress;
import sk.posam.fsa.discussion.CourseProgressId;

import java.util.List;
import java.util.Optional;

public interface CourseProgressFacade {
    List<Long> getCompletedLessonIds(Long courseId, Long userId);
    void addLessonProgress(Long courseId, Long userId, Long lessonId);
    void removeLessonProgress(Long courseId, Long userId, Long lessonId);
    void save(CourseProgress courseProgress);
    Optional<CourseProgress> findById(CourseProgressId id);
    Integer getCourseRating(Long courseId, Long userId);
    void setCourseRating(Long courseId, Long userId, Integer rating);
    List<CourseProgress> listCourseProgressByCourse(Long courseId);
    void deleteByCourseIdAndUserId(Long courseId, Long userId);
}
