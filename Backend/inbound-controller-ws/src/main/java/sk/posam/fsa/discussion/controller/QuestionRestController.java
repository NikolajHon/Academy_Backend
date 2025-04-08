package sk.posam.fsa.discussion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import sk.posam.fsa.discussion.mapper.QuestionMapper;
import sk.posam.fsa.discussion.rest.api.QuestionsApi;
import sk.posam.fsa.discussion.rest.dto.CreateQuestionRequestDto;
import sk.posam.fsa.discussion.rest.dto.QuestionDto;
import sk.posam.fsa.discussion.rest.dto.UserDto;
import sk.posam.fsa.discussion.security.CurrentUserDetailService;
import sk.posam.fsa.discussion.service.QuestionFacade;
import sk.posam.fsa.discussion.service.QuestionService;

@RestController
public class QuestionRestController implements QuestionsApi {

    private final QuestionFacade questionFacade;
    private final QuestionMapper questionMapper;
    private final CurrentUserDetailService currentUserDetailService;


    public QuestionRestController(QuestionFacade questionFacade, QuestionMapper questionMapper, CurrentUserDetailService currentUserDetailService) {
        this.questionFacade = questionFacade;
        this.questionMapper = questionMapper;
        this.currentUserDetailService = currentUserDetailService;
    }

    @Override
    public ResponseEntity<QuestionDto> createQuestion(CreateQuestionRequestDto createQuestionRequestDto) {
        UserDto user = currentUserDetailService.getCurrentUser();

        if (!UserDto.RoleEnum.TEACHER.equals(user.getRole())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only teachers can create questions.");
        }

        System.out.println("Creating question by user: " + user.getEmail());

        var question = questionMapper.toDomain(createQuestionRequestDto);
        var savedQuestion = questionFacade.create(question);
        var questionDto = questionMapper.toDto(savedQuestion);

        return ResponseEntity.status(HttpStatus.CREATED).body(questionDto);
    }

}
