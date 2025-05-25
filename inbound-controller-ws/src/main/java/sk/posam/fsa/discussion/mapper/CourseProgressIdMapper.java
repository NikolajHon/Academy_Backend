
package sk.posam.fsa.discussion.mapper;

import org.mapstruct.Mapper;
import sk.posam.fsa.discussion.CourseProgressId;
import sk.posam.fsa.discussion.rest.dto.CourseProgressIdDto;

@Mapper(componentModel = "spring")
public interface CourseProgressIdMapper {
    CourseProgressIdDto toDto(CourseProgressId domain);
    CourseProgressId toDomain(CourseProgressIdDto dto);
}
