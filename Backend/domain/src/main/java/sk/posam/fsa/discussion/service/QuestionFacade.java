package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.Question;

import java.util.List;

public interface QuestionFacade {
    Question createQuestion(Long lessonId, Question question);
    List<Question> getQuestionsByLesson(Long lessonId, int page, int size);
    Question getQuestion(Long questionId);
    Question updateQuestion(Long questionId, Question question);
    void deleteQuestion(Long questionId);
}
