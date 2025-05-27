package sk.posam.fsa.discussion.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import sk.posam.fsa.discussion.*;
import sk.posam.fsa.discussion.mapper.*;
import sk.posam.fsa.discussion.rest.api.CoursesApi;
import sk.posam.fsa.discussion.rest.dto.*;
import sk.posam.fsa.discussion.security.CurrentUserDetailService;
import sk.posam.fsa.discussion.service.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
public class CourseRestController implements CoursesApi {

    private final CourseFacade courseFacade;
    private final ForumFacade forumFacade;
    private final CourseMapper courseMapper;
    private final LessonMapper lessonMapper;
    private final TopicMapper topicMapper;
    private final CourseProgressFacade progressFacade;
    private final UserFacade userFacade;

    public CourseRestController(
            CourseFacade courseFacade,
            ForumFacade forumFacade,
            CourseMapper courseMapper,
            LessonMapper lessonMapper,
            TopicMapper topicMapper,
            CourseProgressFacade progressFacade, UserFacade userFacade
    ) {
        this.courseFacade = courseFacade;
        this.forumFacade = forumFacade;
        this.courseMapper = courseMapper;
        this.lessonMapper = lessonMapper;
        this.topicMapper = topicMapper;
        this.progressFacade = progressFacade;
        this.userFacade = userFacade;
    }


    @Override
    public ResponseEntity<LessonListResponseDto> getCompletedCourseLessons(
            Long courseId,
            String keycloakId
    ) {

        Long userId = userFacade.getByKeycloakId(keycloakId)
                .map(u -> u.getId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
                );

        List<Long> lessons = progressFacade.getCompletedLessonIds(courseId, userId);
        LessonListResponseDto resp = new LessonListResponseDto().lessons(lessons);
        return ResponseEntity.ok(resp);
    }

    @Override
    public ResponseEntity<Void> addCompletedLesson(
            Long courseId,
            String keycloakId,
            Long lessonId
    ) {
        Long userId = userFacade.getByKeycloakId(keycloakId)
                .map(u -> u.getId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
                );

        try {
            progressFacade.addLessonProgress(courseId, userId, lessonId);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> removeCompletedLesson(
            Long courseId,
            String keycloakId,
            Long lessonId
    ) {

        Long userId = userFacade.getByKeycloakId(keycloakId)
                .map(u -> u.getId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
                );

        try {
            progressFacade.removeLessonProgress(courseId, userId, lessonId);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }

        return ResponseEntity.noContent().build();
    }


    @Override
    public ResponseEntity<Void> createCourse(@Valid CreateCourseRequestDto body) {

        Course course = new Course();
        course.setName(body.getName());
        course.setDescription(body.getDescription());
        courseFacade.create(course);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> createLesson(CreateLessonRequestDto dto) {
        Lesson lesson = lessonMapper.toDomain(dto);

        courseFacade.addLesson(dto.getCourseId(), lesson);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<CoursesResponseDto> getAllCourses() {
        Collection<Course> courses = courseFacade.readAll();
        List<CourseDto> dtos = courseMapper.toDto(courses);

        CoursesResponseDto resp = new CoursesResponseDto();
        resp.setCourses(dtos);
        return ResponseEntity.ok(resp);
    }


    @Override
    public ResponseEntity<List<LessonDto>> getLessonsByCourseId(Long id) {
        Course course = courseFacade.readAll()
                .stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Course not found"));
        return ResponseEntity.ok(lessonMapper.toDto(course.getLessons()));
    }

    @Override
    public ResponseEntity<TopicDto> createTopic(Long courseId,
                                                @Valid CreateTopicRequestDto body) {

        Topic topic = topicMapper.toEntity(body);

        Course courseRef = new Course();
        courseRef.setId(courseId);
        topic.setCourse(courseRef);

        forumFacade.createTopic(topic);
        return ResponseEntity.status(HttpStatus.CREATED).body(topicMapper.toDto(topic));
    }

    @Override
    public ResponseEntity<TopicsResponseDto> getTopicsByCourse(Long courseId,
                                                               Integer page,
                                                               Integer size) {

        int p = (page == null || page < 0) ? 0 : page;
        int s = (size == null || size <= 0) ? 20 : size;

        Collection<Topic> topics = forumFacade.getTopics(courseId, p, s);

        TopicsResponseDto resp = new TopicsResponseDto()
                .page(p)
                .size(s)
                .topics(topicMapper.toDto(topics));

        return ResponseEntity.ok(resp);
    }
}
