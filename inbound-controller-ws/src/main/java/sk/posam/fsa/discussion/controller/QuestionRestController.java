package sk.posam.fsa.discussion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import sk.posam.fsa.discussion.AnswerResult;
import sk.posam.fsa.discussion.Question;
import sk.posam.fsa.discussion.UserAnswer;
import sk.posam.fsa.discussion.mapper.QuestionMapper;
import sk.posam.fsa.discussion.rest.api.QuestionsApi;
import sk.posam.fsa.discussion.rest.dto.*;
import sk.posam.fsa.discussion.security.CurrentUserDetailService;
import sk.posam.fsa.discussion.service.QuestionFacade;
import sk.posam.fsa.discussion.service.QuestionService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class QuestionRestController implements QuestionsApi {
    private final QuestionFacade questionFacade;
    private final QuestionMapper questionMapper;

    public QuestionRestController(QuestionFacade questionFacade,
                                  QuestionMapper questionMapper) {
        this.questionFacade = questionFacade;
        this.questionMapper = questionMapper;
    }

    @Override
    public ResponseEntity<Void> deleteQuestion(Long questionId) {
        questionFacade.deleteQuestion(questionId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<QuestionDto> getQuestionById(Long questionId) {
        Question q = questionFacade.getQuestion(questionId);
        return ResponseEntity.ok(questionMapper.toDto(q));
    }


    @Override
    public ResponseEntity<QuestionDto> updateQuestion(
            Long questionId,
            UpdateQuestionRequestDto dto) {

        Question domain = questionMapper.toDomain(dto);
        Question updated = questionFacade.updateQuestion(questionId, domain);
        return ResponseEntity.ok(questionMapper.toDto(updated));
    }


    @Override
    public ResponseEntity<CheckAnswersResponseDto> questionsAnswersCheckPost(
            CheckAnswersRequestDto request) {
        List<UserAnswer> domainAnswers = request.getAnswers().stream()
                .map(dto -> new UserAnswer(
                        dto.getQuestionId().longValue(),
                        dto.getSelectedOptionIds().stream()
                                .map(Integer::longValue)
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        List<AnswerResult> domainResults = questionFacade.checkAnswers(domainAnswers);

        List<AnswerResultDto> resultDtos = domainResults.stream()
                .map(r -> {
                    AnswerResultDto dto = new AnswerResultDto();
                    dto.setQuestionId(r.getQuestionId().intValue());
                    dto.setCorrect(r.isCorrect());
                    return dto;
                })
                .collect(Collectors.toList());

        CheckAnswersResponseDto resp = new CheckAnswersResponseDto();
        resp.setResults(resultDtos);
        return ResponseEntity.ok(resp);
    }

}
