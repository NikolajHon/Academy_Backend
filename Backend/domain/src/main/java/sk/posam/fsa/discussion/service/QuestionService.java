package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.Lesson;
import sk.posam.fsa.discussion.Question;
import sk.posam.fsa.discussion.repository.LessonRepository;
import sk.posam.fsa.discussion.repository.QuestionRepository;

import java.util.List;
import java.util.NoSuchElementException;

public class QuestionService implements QuestionFacade {
    private final QuestionRepository questionRepo;
    private final LessonRepository lessonRepo;

    public QuestionService(QuestionRepository questionRepo,
                           LessonRepository lessonRepo) {
        this.questionRepo = questionRepo;
        this.lessonRepo   = lessonRepo;
    }

    @Override
    public Question createQuestion(Long lessonId, Question question) {
        Lesson lesson = lessonRepo.findById(lessonId)
                .orElseThrow(() -> new NoSuchElementException("Lesson not found: " + lessonId));
        question.setLesson(lesson);

        question.getOptions().forEach(opt -> opt.setQuestion(question));

        return questionRepo.create(question);
    }

    @Override
    public List<Question> getQuestionsByLesson(Long lessonId, int page, int size) {
        return questionRepo.findByLesson(lessonId, page, size);
    }

    @Override
    public Question getQuestion(Long questionId) {
        return questionRepo.findById(questionId)
                .orElseThrow(() -> new NoSuchElementException("Question not found: " + questionId));
    }

    @Override
    public Question updateQuestion(Long questionId, Question question) {
        Question existing = getQuestion(questionId);
        existing.setText(question.getText());
        existing.setType(question.getType());
        existing.setOptions(question.getOptions());
        existing.getOptions().forEach(o -> o.setQuestion(existing));
        return questionRepo.update(existing);
    }

    @Override
    public void deleteQuestion(Long questionId) {
        getQuestion(questionId);
        questionRepo.delete(questionId);
    }
}
