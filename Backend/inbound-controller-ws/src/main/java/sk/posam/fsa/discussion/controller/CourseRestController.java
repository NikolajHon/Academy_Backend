package sk.posam.fsa.discussion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sk.posam.fsa.discussion.Course;
import sk.posam.fsa.discussion.mapper.CourseMapper;
import sk.posam.fsa.discussion.rest.api.CoursesApi;
import sk.posam.fsa.discussion.rest.dto.CourseDto;
import sk.posam.fsa.discussion.rest.dto.CoursesResponseDto;
import sk.posam.fsa.discussion.rest.dto.CreateCourseRequestDto;
import sk.posam.fsa.discussion.service.CourseFacade;
import sk.posam.fsa.discussion.service.CourseService;

import java.util.Collection;
import java.util.List;

@RestController
public class CourseRestController implements CoursesApi {

    private final CourseFacade courseFacade;
    private final CourseMapper courseMapper;

    public CourseRestController(CourseFacade courseFacade, CourseMapper courseMapper) {
        this.courseFacade = courseFacade;
        this.courseMapper = courseMapper;
    }

    @Override
    public ResponseEntity<Void> createCourse(CreateCourseRequestDto createCourseRequestDto) {
        Course course = new Course();
        course.setName(createCourseRequestDto.getName());
        course.setDescription(createCourseRequestDto.getDescription());
        courseFacade.create(course);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<CoursesResponseDto> getAllCourses() {
        Collection<Course> courses = courseFacade.readAll();
        List<CourseDto> courseDtos = courseMapper.toDto(courses);

        CoursesResponseDto response = new CoursesResponseDto();
        response.setCourses(courseDtos);

        return ResponseEntity.ok(response);
    }
}
