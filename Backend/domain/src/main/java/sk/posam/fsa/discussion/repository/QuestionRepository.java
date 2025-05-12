package sk.posam.fsa.discussion.repository;

import sk.posam.fsa.discussion.Question;

public interface QuestionRepository {
    Question saveQuestion(Question question);
}
