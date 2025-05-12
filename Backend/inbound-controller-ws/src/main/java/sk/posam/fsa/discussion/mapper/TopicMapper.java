package sk.posam.fsa.discussion.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import sk.posam.fsa.discussion.Topic;
import sk.posam.fsa.discussion.rest.dto.CreateTopicRequestDto;
import sk.posam.fsa.discussion.rest.dto.UpdateTopicRequestDto;
import sk.posam.fsa.discussion.rest.dto.TopicDto;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TopicMapper {

    TopicDto toDto(Topic topic);
    List<TopicDto> toDto(Collection<Topic> topics);

    @Mapping(target = "id",  ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "status", constant = "OPEN")
    Topic toEntity(CreateTopicRequestDto dto);

    @Mapping(target = "course", ignore = true)
    Topic toEntity(UpdateTopicRequestDto dto);
}
