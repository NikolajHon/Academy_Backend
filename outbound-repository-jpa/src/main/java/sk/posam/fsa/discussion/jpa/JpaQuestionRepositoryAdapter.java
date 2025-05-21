package sk.posam.fsa.discussion.jpa;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import sk.posam.fsa.discussion.Question;
import sk.posam.fsa.discussion.repository.QuestionRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaQuestionRepositoryAdapter implements QuestionRepository {
    private final QuestionSpringDataRepository spring;

    public JpaQuestionRepositoryAdapter(QuestionSpringDataRepository spring) {
        this.spring = spring;
    }

    @Override
    public Question create(Question question) {
        return spring.save(question);
    }

    @Override
    public Optional<Question> findById(Long id) {
        return spring.findById(id);
    }

    @Override
    public Question update(Question question) {
        return spring.save(question);
    }

    @Override
    public void delete(Long id) {
        spring.deleteById(id);
    }

    @Override
    public List<Question> findByLesson(Long lessonId, int page, int size) {
        Pageable p = PageRequest.of(page, size);
        return spring.findByLessonId(lessonId, p).getContent();
    }
}
