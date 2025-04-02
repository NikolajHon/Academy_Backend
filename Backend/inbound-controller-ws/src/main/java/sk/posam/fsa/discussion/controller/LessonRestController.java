package sk.posam.fsa.discussion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sk.posam.fsa.discussion.Course;
import sk.posam.fsa.discussion.Lesson;
import sk.posam.fsa.discussion.mapper.LessonMapper;
import sk.posam.fsa.discussion.rest.api.LessonsApi;
import sk.posam.fsa.discussion.rest.dto.CreateLessonRequestDto;
import sk.posam.fsa.discussion.service.LessonFacade;

@RestController
public class LessonRestController implements LessonsApi {

    private final LessonFacade lessonFacade;
    private final LessonMapper lessonMapper;

    public LessonRestController(LessonFacade lessonFacade, LessonMapper lessonMapper) {
        this.lessonFacade = lessonFacade;
        this.lessonMapper = lessonMapper;
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
