package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.*;
import sk.posam.fsa.discussion.exceptions.EducationAppException;
import sk.posam.fsa.discussion.exceptions.ResourceNotFoundException;
import sk.posam.fsa.discussion.repository.LessonRepository;
import sk.posam.fsa.discussion.repository.QuestionRepository;

import java.util.List;
import java.util.stream.Collectors;

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
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Lesson with id=" + lessonId + " not found"
                ));
        question.setLesson(lesson);
        question.getOptions().forEach(opt -> opt.setQuestion(question));
        try {
            return questionRepo.create(question);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException("Failed to create question for lessonId=" + lessonId, e);
        }
    }

    @Override
    public List<Question> getQuestionsByLesson(Long lessonId, int page, int size) {
        try {
            return questionRepo.findByLesson(lessonId, page, size);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException("Failed to load questions for lessonId=" + lessonId, e);
        }
    }

    @Override
    public Question getQuestion(Long questionId) {
        return questionRepo.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Question with id=" + questionId + " not found"
                ));
    }

    @Override
    public Question updateQuestion(Long questionId, Question question) {
        Question existing = getQuestion(questionId);
        existing.setText(question.getText());
        existing.setType(question.getType());
        existing.setOptions(question.getOptions());
        existing.getOptions().forEach(o -> o.setQuestion(existing));
        try {
            return questionRepo.update(existing);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException("Failed to update question id=" + questionId, e);
        }
    }

    @Override
    public void deleteQuestion(Long questionId) {
        getQuestion(questionId);
        try {
            questionRepo.delete(questionId);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException("Failed to delete question id=" + questionId, e);
        }
    }
    @Override
    public List<AnswerResult> checkAnswers(List<UserAnswer> userAnswers) {
        return userAnswers.stream()
                .map(ua -> {
                    Question question = questionRepo.findById(ua.getQuestionId())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException("Question", ua.getQuestionId())
                            );
                    boolean correct = question.getOptions().stream()
                            .anyMatch(opt ->
                                    opt.getId().equals(ua.getSelectedOptionId())
                                            && Boolean.TRUE.equals(opt.isCorrect())
                            );
                    return new AnswerResult(question.getId(), correct);
                })
                .collect(Collectors.toList());
    }

}
