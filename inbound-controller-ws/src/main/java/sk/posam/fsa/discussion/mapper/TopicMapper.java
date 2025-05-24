package sk.posam.fsa.discussion.mapper;

import org.mapstruct.*;
import sk.posam.fsa.discussion.Topic;
import sk.posam.fsa.discussion.rest.dto.*;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TopicMapper {

    @Mapping(target = "courseId",     source = "course.id")
    TopicDto toDto(Topic entity);

    List<TopicDto> toDto(Collection<Topic> entities);

    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "course",    ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "status",    constant = "OPEN")
    Topic toEntity(CreateTopicRequestDto dto);

    @InheritConfiguration(name = "toEntity")
    Topic toEntity(UpdateTopicRequestDto dto);
}
