package sk.posam.fsa.discussion.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sk.posam.fsa.discussion.Course;
import sk.posam.fsa.discussion.Lesson;
import sk.posam.fsa.discussion.rest.dto.CourseDto;
import sk.posam.fsa.discussion.rest.dto.LessonDto;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    LessonDto toDto(Lesson lesson);

    List<LessonDto> toDto(List<Lesson> lessons);

    CourseDto toDto(Course course);

    List<CourseDto> toDto(Collection<Course> courses);
}
