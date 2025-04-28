package sk.posam.fsa.discussion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sk.posam.fsa.discussion.Assignment;
import sk.posam.fsa.discussion.Course;
import sk.posam.fsa.discussion.Lesson;
import sk.posam.fsa.discussion.mapper.AssignmentMapper;
import sk.posam.fsa.discussion.mapper.LessonMapper;
import sk.posam.fsa.discussion.rest.api.LessonsApi;
import sk.posam.fsa.discussion.rest.dto.AssignmentDto;
import sk.posam.fsa.discussion.rest.dto.CreateAssignmentRequestDto;
import sk.posam.fsa.discussion.rest.dto.CreateLessonRequestDto;
import sk.posam.fsa.discussion.service.LessonFacade;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class LessonRestController implements LessonsApi {

    private final LessonFacade lessonFacade;
    private final LessonMapper lessonMapper;
    private final AssignmentMapper assignmentMapper;

    public LessonRestController(LessonFacade lessonFacade,
                                LessonMapper lessonMapper,
                                AssignmentMapper assignmentMapper) {
        this.lessonFacade       = lessonFacade;
        this.lessonMapper       = lessonMapper;
        this.assignmentMapper   = assignmentMapper;
    }

    @Override
    public ResponseEntity<AssignmentDto> createAssignment(
            Long lessonId,
            CreateAssignmentRequestDto createDto) {

        Assignment domain = assignmentMapper.toDomain(createDto);
        Assignment saved = lessonFacade.createAssignment(lessonId, domain);
        AssignmentDto dto = assignmentMapper.toDto(saved);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dto);
    }

    @Override
    public ResponseEntity<List<AssignmentDto>> getAssignmentsByLesson(
            Long lessonId) {

        List<Assignment> list = lessonFacade.getAssignmentsByLesson(lessonId);
        List<AssignmentDto> dtoList = list.stream()
                .map(assignmentMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @Override
    public ResponseEntity<Void> createLesson(CreateLessonRequestDto dto) {
        Lesson lesson = lessonMapper.toDomain(dto);
        Course course = new Course();
        course.setId(dto.getCourseId());
        lesson.setCourse(course);
        lessonFacade.createLesson(lesson);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
