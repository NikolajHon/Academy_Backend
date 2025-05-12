package sk.posam.fsa.discussion.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import sk.posam.fsa.discussion.Post;
import sk.posam.fsa.discussion.rest.dto.CreatePostRequestDto;
import sk.posam.fsa.discussion.rest.dto.CreateReplyRequestDto;
import sk.posam.fsa.discussion.rest.dto.PostDto;
import sk.posam.fsa.discussion.rest.dto.UpdatePostRequestDto;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
    PostDto toDto(Post post);
    List<PostDto> toDto(Collection<Post> posts);

    Post toEntity(CreatePostRequestDto dto);
    Post toEntity(CreateReplyRequestDto dto);
    Post toEntity(UpdatePostRequestDto dto);
}
