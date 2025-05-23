package sk.posam.fsa.discussion.service;

import java.util.List;

public interface CourseProgressFacade {
    List<Long> getCompletedLessonIds(Long courseId, Long userId);
    void addLessonProgress(Long courseId, Long userId, Long lessonId);
    void removeLessonProgress(Long courseId, Long userId, Long lessonId);
}
