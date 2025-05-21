package sk.posam.fsa.discussion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import sk.posam.fsa.discussion.Question;
import sk.posam.fsa.discussion.mapper.QuestionMapper;
import sk.posam.fsa.discussion.rest.api.QuestionsApi;
import sk.posam.fsa.discussion.rest.dto.CreateQuestionRequestDto;
import sk.posam.fsa.discussion.rest.dto.QuestionDto;
import sk.posam.fsa.discussion.rest.dto.UpdateQuestionRequestDto;
import sk.posam.fsa.discussion.rest.dto.UserDto;
import sk.posam.fsa.discussion.security.CurrentUserDetailService;
import sk.posam.fsa.discussion.service.QuestionFacade;
import sk.posam.fsa.discussion.service.QuestionService;

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
}
