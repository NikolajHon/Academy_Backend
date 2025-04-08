package sk.posam.fsa.discussion.jpa;

import org.springframework.stereotype.Repository;
import sk.posam.fsa.discussion.Question;
import sk.posam.fsa.discussion.QuestionRepository;
@Repository
public class JpaQuestionRepositoryAdapter implements QuestionRepository {

    private final QuestionSpringDataRepository questionSpringDataRepository;

    public JpaQuestionRepositoryAdapter(QuestionSpringDataRepository questionSpringDataRepository) {
        this.questionSpringDataRepository = questionSpringDataRepository;
    }

    @Override
    public Question saveQuestion(Question question) {
        return questionSpringDataRepository.save(question);
    }
}
