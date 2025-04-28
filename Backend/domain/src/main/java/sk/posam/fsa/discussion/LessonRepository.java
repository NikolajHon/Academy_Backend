package sk.posam.fsa.discussion;

import java.util.List;
import java.util.Optional;

public interface LessonRepository {
    Lesson save(Lesson lesson);
    List<Lesson> findAll();
    Optional<Lesson> findById(Long id);    // <-- добавили
}
