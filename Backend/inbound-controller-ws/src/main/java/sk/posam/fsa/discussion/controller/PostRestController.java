package sk.posam.fsa.discussion.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sk.posam.fsa.discussion.Post;
import sk.posam.fsa.discussion.mapper.PostMapper;
import sk.posam.fsa.discussion.rest.api.PostsApi;
import sk.posam.fsa.discussion.rest.dto.CreateReplyRequestDto;
import sk.posam.fsa.discussion.rest.dto.PostDto;
import sk.posam.fsa.discussion.rest.dto.UpdatePostRequestDto;
import sk.posam.fsa.discussion.service.ForumFacade;

@RestController
public class PostRestController implements PostsApi {

    private final ForumFacade forumFacade;
    private final PostMapper  mapper;

    public PostRestController(ForumFacade forumFacade, PostMapper mapper) {
        this.forumFacade = forumFacade;
        this.mapper      = mapper;
    }

    @Override
    public ResponseEntity<PostDto> createReply(Long postId,
                                               @Valid CreateReplyRequestDto createReplyRequestDto) {

        Post reply = mapper.toEntity(createReplyRequestDto);
        Post savedReply = forumFacade.reply(postId, reply);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.toDto(savedReply));
    }


    @Override
    public ResponseEntity<Void> deletePost(Long postId) {
        forumFacade.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PostDto> getPostById(Long postId) {
        Post post = forumFacade.getPost(postId);
        return ResponseEntity.ok(mapper.toDto(post));
    }

    @Override
    public ResponseEntity<PostDto> updatePost(Long postId,
                                              @Valid UpdatePostRequestDto updatePostRequestDto) {

        if (!postId.equals(updatePostRequestDto.getId())) {
            return ResponseEntity.badRequest().build();
        }

        Post entity = mapper.toEntity(updatePostRequestDto);
        entity.setId(postId);

        forumFacade.updatePost(entity);
        return ResponseEntity.ok(mapper.toDto(entity));
    }
}
