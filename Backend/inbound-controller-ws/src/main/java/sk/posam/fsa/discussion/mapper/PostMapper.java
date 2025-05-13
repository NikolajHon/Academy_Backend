package sk.posam.fsa.discussion.mapper;

import org.mapstruct.*;
import sk.posam.fsa.discussion.Post;
import sk.posam.fsa.discussion.rest.dto.*;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "topicId",  source = "topic.id")
    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "authorId", source = "author.id")
    PostDto toDto(Post entity);

    List<PostDto> toDto(Collection<Post> entities);

    @Mapping(target = "id",       ignore = true)
    @Mapping(target = "topic",    ignore = true)
    @Mapping(target = "parent",   ignore = true)
    @Mapping(target = "author",   ignore = true)
    @Mapping(target = "status",   ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Post toEntity(CreatePostRequestDto dto);

    @InheritConfiguration(name = "toEntity")
    Post toEntity(CreateReplyRequestDto dto);

    @InheritConfiguration(name = "toEntity")
    Post toEntity(UpdatePostRequestDto dto);
}
