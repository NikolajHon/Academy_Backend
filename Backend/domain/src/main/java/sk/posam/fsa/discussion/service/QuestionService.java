package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.Question;
import sk.posam.fsa.discussion.QuestionRepository;

public class QuestionService implements QuestionFacade {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question create(Question question) {
        return questionRepository.saveQuestion(question);
    }
}
