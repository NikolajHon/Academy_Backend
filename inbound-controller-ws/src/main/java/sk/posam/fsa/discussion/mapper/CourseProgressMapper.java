// 2) Основной маппер "курспрогресса"
package sk.posam.fsa.discussion.mapper;

import org.mapstruct.Mapper;
import sk.posam.fsa.discussion.CourseProgress;
import sk.posam.fsa.discussion.rest.dto.CourseProgressDto;


import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = { CourseProgressIdMapper.class }
)
public interface CourseProgressMapper {

    CourseProgressDto toDto(CourseProgress domain);

    List<CourseProgressDto> toDto(List<CourseProgress> domainList);

    CourseProgress toDomain(CourseProgressDto dto);
    List<CourseProgress> toDomain(List<CourseProgressDto> dtoList);
}
