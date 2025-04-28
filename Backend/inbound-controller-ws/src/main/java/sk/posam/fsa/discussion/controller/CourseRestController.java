package sk.posam.fsa.discussion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import sk.posam.fsa.discussion.Course;
import sk.posam.fsa.discussion.Lesson;
import sk.posam.fsa.discussion.mapper.CourseMapper;
import sk.posam.fsa.discussion.mapper.LessonMapper;
import sk.posam.fsa.discussion.rest.api.CoursesApi;
import sk.posam.fsa.discussion.rest.dto.*;
import sk.posam.fsa.discussion.security.CurrentUserDetailService;
import sk.posam.fsa.discussion.service.CourseFacade;

import java.util.Collection;
import java.util.List;

@RestController
public class CourseRestController implements CoursesApi {

    private final CourseFacade courseFacade;
    private final CourseMapper courseMapper;
    private final LessonMapper lessonMapper;
    private final CurrentUserDetailService currentUserDetailService;

    public CourseRestController(CourseFacade courseFacade,
                                CourseMapper courseMapper,
                                LessonMapper lessonMapper,
                                CurrentUserDetailService currentUserDetailService) {
        this.courseFacade = courseFacade;
        this.courseMapper = courseMapper;
        this.lessonMapper = lessonMapper;
        this.currentUserDetailService = currentUserDetailService;
    }

    @Override
    public ResponseEntity<Void> createCourse(CreateCourseRequestDto createCourseRequestDto) {
        UserDto user = currentUserDetailService.getCurrentUser();


        if (!UserDto.RoleEnum.TEACHER.equals(user.getRole())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only teachers can create courses.");
        }
        System.out.println("Creating course by user: " + user.getEmail());

        Course course = new Course();
        course.setName(createCourseRequestDto.getName());
        course.setDescription(createCourseRequestDto.getDescription());
        courseFacade.create(course);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<CoursesResponseDto> getAllCourses() {
        Collection<Course> courses = courseFacade.readAll();

        System.out.println("=== Loaded Courses ===");
        for (Course course : courses) {
            System.out.println("Course ID: " + course.getId() + ", Name: " + course.getName());
            if (course.getLessons() != null) {
                for (Lesson lesson : course.getLessons()) {
                    System.out.println("  -> Lesson ID: " + lesson.getId() +
                            ", Title: " + lesson.getTitle() +
                            ", Course ID inside lesson: " +
                            (lesson.getCourse() != null ? lesson.getCourse().getId() : "NULL"));
                }
            } else {
                System.out.println("  -> No lessons found.");
            }
        }
        System.out.println("=======================");

        List<CourseDto> courseDto = courseMapper.toDto(courses);

        System.out.println("=== Mapped Course DTOs ===");
        for (CourseDto dto : courseDto) {
            System.out.println("CourseDto ID: " + dto.getId() + ", Name: " + dto.getName());
            if (dto.getLessons() != null) {
                for (var lessonDto : dto.getLessons()) {
                    System.out.println("  -> LessonDto ID: " + lessonDto.getId() +
                            ", Title: " + lessonDto.getTitle() +
                            ", courseId: " + lessonDto.getCourseId());
                }
            } else {
                System.out.println("  -> No lessons found in DTO.");
            }
        }
        System.out.println("==========================");

        CoursesResponseDto response = new CoursesResponseDto();
        response.setCourses(courseDto);

        return ResponseEntity.ok(response);
    }


    @Override
    public ResponseEntity<List<LessonDto>> getLessonsByCourseId(Long id) {
        Course course = courseFacade.readAll()
                .stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        List<Lesson> lessons = course.getLessons();

        List<LessonDto> lessonDtos = lessonMapper.toDto(lessons);

        return ResponseEntity.ok(lessonDtos);
    }
}
