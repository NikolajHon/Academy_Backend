package sk.posam.fsa.discussion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sk.posam.fsa.discussion.Assignment;
import sk.posam.fsa.discussion.Course;
import sk.posam.fsa.discussion.Lesson;
import sk.posam.fsa.discussion.Question;
import sk.posam.fsa.discussion.mapper.AssignmentMapper;
import sk.posam.fsa.discussion.mapper.LessonMapper;
import sk.posam.fsa.discussion.mapper.QuestionMapper;
import sk.posam.fsa.discussion.rest.api.LessonsApi;
import sk.posam.fsa.discussion.rest.dto.*;
import sk.posam.fsa.discussion.service.LessonFacade;
import sk.posam.fsa.discussion.service.QuestionFacade;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class LessonRestController implements LessonsApi {

    private final LessonFacade lessonFacade;
    private final AssignmentMapper assignmentMapper;
    private final QuestionMapper questionMapper;
    private final QuestionFacade questionFacade;

    public LessonRestController(LessonFacade lessonFacade,
                                AssignmentMapper assignmentMapper, QuestionMapper questionMapper, QuestionFacade questionFacade) {
        this.lessonFacade = lessonFacade;
        this.assignmentMapper = assignmentMapper;
        this.questionMapper = questionMapper;
        this.questionFacade = questionFacade;
    }

    @Override
    public ResponseEntity<AssignmentDto> createAssignment(
            Long lessonId,
            CreateAssignmentRequestDto createDto
    ) {
        System.out.println("createAssignment called with lessonId = " + lessonId);
        System.out.println("CreateAssignmentRequestDto:");
        System.out.println("  description    = " + createDto.getDescription());
        System.out.println("  teacherCode    = " + createDto.getTeacherCode());
        System.out.println("  templateCode   = " + createDto.getTemplateCode());
        System.out.println("  expectedOutput = " + createDto.getLanguage());
        System.out.println("  outputType     = " + createDto.getOutputType());
        System.out.println("  testCases      = " + createDto.getTestCases());

        Assignment domain = assignmentMapper.toDomain(createDto);
        Assignment saved = lessonFacade.createAssignment(lessonId, domain);

        AssignmentDto dto = assignmentMapper.toDto(saved);
        System.out.println("Returning AssignmentDto: " + dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dto);
    }


    @Override
    public ResponseEntity<List<AssignmentDto>> getAssignmentsByLesson(
            Long lessonId) {

        List<Assignment> list = lessonFacade.getAssignments(lessonId);
        List<AssignmentDto> dtoList = list.stream()
                .map(assignmentMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @Override
    public ResponseEntity<QuestionsPublicResponseDto> getQuestionsByLesson(
            Long lessonId,
            Integer page,
            Integer size
    ) {
        List<Question> list = questionFacade.getQuestionsByLesson(lessonId, page, size);

        List<QuestionPublicDto> questionDtos = questionMapper.toPublicDto(list);

        QuestionsPublicResponseDto resp = new QuestionsPublicResponseDto();
        resp.setQuestions(questionDtos);
        resp.setPage(page);
        resp.setSize(size);
        int total = questionDtos.size();
        resp.setTotalElements(total);
        resp.setTotalPages(size != null && size > 0
                ? (int) Math.ceil((double) total / size)
                : 1);

        return ResponseEntity.ok(resp);
    }


    @Override
    public ResponseEntity<QuestionDto> createQuestion(Long lessonId,
                                                      CreateQuestionRequestDto dto) {
        String optionsDesc = dto.getOptions().stream()
                .map(opt -> "{text=" + opt.getText() + ", correct=" + opt.getCorrect() + "}")
                .collect(Collectors.joining(", "));

        Question domain = questionMapper.toDomain(dto);
        Question saved = questionFacade.createQuestion(lessonId, domain);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(questionMapper.toDto(saved));
    }

    @Override
    public ResponseEntity<Void> deleteLesson(Long lessonId) {
        lessonFacade.deleteLesson(lessonId);
        return ResponseEntity.noContent().build();
    }



}
