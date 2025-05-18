package sk.posam.fsa.discussion.repository;

import sk.posam.fsa.discussion.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {
    Question create(Question question);
    Optional<Question> findById(Long id);
    Question update(Question question);
    void delete(Long id);
    List<Question> findByLesson(Long lessonId, int page, int size);
}
