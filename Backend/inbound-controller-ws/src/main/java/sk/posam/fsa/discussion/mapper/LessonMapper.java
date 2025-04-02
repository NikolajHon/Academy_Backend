package sk.posam.fsa.discussion.mapper;

import org.mapstruct.Mapper;
import sk.posam.fsa.discussion.Lesson;
import sk.posam.fsa.discussion.rest.dto.CreateLessonRequestDto;
import sk.posam.fsa.discussion.rest.dto.LessonDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LessonMapper {
    Lesson toDomain(CreateLessonRequestDto dto);
    List<LessonDto> toDto(List<Lesson> lessons);

}
