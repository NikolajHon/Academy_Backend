package sk.posam.fsa.discussion.repository;

import sk.posam.fsa.discussion.Lesson;

import java.util.List;
import java.util.Optional;

public interface LessonRepository {
    Lesson save(Lesson lesson);
    List<Lesson> findAll();
    Optional<Lesson> findById(Long id);
    Lesson get(long id);
    void delete(Long lessonId);
}
