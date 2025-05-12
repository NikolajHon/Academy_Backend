package sk.posam.fsa.discussion.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sk.posam.fsa.discussion.Lesson;
import sk.posam.fsa.discussion.Post;
import sk.posam.fsa.discussion.Topic;
import sk.posam.fsa.discussion.mapper.PostMapper;
import sk.posam.fsa.discussion.mapper.TopicMapper;
import sk.posam.fsa.discussion.rest.api.TopicsApi;
import sk.posam.fsa.discussion.rest.dto.*;
import sk.posam.fsa.discussion.service.ForumFacade;

import java.util.Collection;

@RestController
public class TopicRestController implements TopicsApi {

    private final ForumFacade forum;
    private final TopicMapper topicMapper;
    private final PostMapper  postMapper;

    public TopicRestController(ForumFacade forum,
                               TopicMapper topicMapper,
                               PostMapper postMapper) {
        this.forum       = forum;
        this.topicMapper = topicMapper;
        this.postMapper  = postMapper;
    }

    @Override
    public ResponseEntity<PostDto> createPost(Long topicId,
                                              @Valid CreatePostRequestDto body) {

        Post entity = postMapper.toEntity(body);

        Topic t = new Topic();
        t.setId(topicId);
        entity.setTopic(t);

        forum.createPost(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(postMapper.toDto(entity));
    }

    @Override
    public ResponseEntity<Void> deleteTopic(Long topicId) {
        forum.deleteTopic(topicId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PostsResponseDto> getPostsByTopic(Long topicId,
                                                            Integer page,
                                                            Integer size) {

        int p = (page == null || page < 0) ? 0  : page;
        int s = (size == null || size <= 0) ? 20 : size;

        Collection<Post> roots = forum.getRootPosts(topicId, p, s);

        PostsResponseDto resp = new PostsResponseDto()
                .page(p)
                .size(s)
                .posts(postMapper.toDto(roots));

        return ResponseEntity.ok(resp);
    }

    @Override
    public ResponseEntity<TopicDto> getTopicById(Long topicId) {
        Topic topic = forum.getTopic(topicId);
        return ResponseEntity.ok(topicMapper.toDto(topic));
    }

    @Override
    public ResponseEntity<TopicDto> updateTopic(Long topicId,
                                                @Valid UpdateTopicRequestDto body) {

        if (!topicId.equals(body.getId())) {
            return ResponseEntity.badRequest().build();
        }

        Topic entity = topicMapper.toEntity(body);
        entity.setId(topicId);


        forum.updateTopic(entity);
        return ResponseEntity.ok(topicMapper.toDto(entity));
    }
}
