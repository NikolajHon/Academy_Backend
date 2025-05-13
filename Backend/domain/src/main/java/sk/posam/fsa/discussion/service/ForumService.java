package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.*;
import sk.posam.fsa.discussion.repository.*;

import java.time.LocalDateTime;
import java.util.Collection;

public class ForumService implements ForumFacade {

    private final TopicRepository  topicRepository;
    private final PostRepository   postRepository;
    private final CourseRepository courseRepository;
    private final CurrentUserPort  currentUser;

    public ForumService(TopicRepository topicRepository,
                        PostRepository  postRepository,
                        CourseRepository courseRepository,
                        CurrentUserPort  currentUser) {

        this.topicRepository  = topicRepository;
        this.postRepository   = postRepository;
        this.courseRepository = courseRepository;
        this.currentUser      = currentUser;
    }


    @Override
    public Collection<Topic> getTopics(long courseId, int page, int size) {
        return topicRepository.getByCourse(courseId, page, size);
    }

    @Override public Topic getTopic(long id) { return topicRepository.get(id); }

    @Override
    public void createTopic(Topic topic) {

        Course course = courseRepository.getCourse(topic.getCourse().getId());
        topic.setCourse(course);

        topic.setCreatedBy(currentUser.getCurrentUser());
        topic.setCreatedAt(LocalDateTime.now());
        topic.setStatus(TopicStatus.OPEN);

        topicRepository.create(topic);
    }

    @Override public void updateTopic(Topic topic) { topicRepository.update(topic); }
    @Override public void deleteTopic(long id)      { topicRepository.delete(id); }


    @Override
    public Collection<Post> getRootPosts(long topicId, int page, int size) {
        return postRepository.getRootsByTopic(topicId, page, size);
    }

    @Override public Post getPost(long id) { return postRepository.get(id); }

    @Override
    public void createPost(Post post) {

        Topic topic = topicRepository.get(post.getTopic().getId());
        post.setTopic(topic);

        post.setAuthor(currentUser.getCurrentUser());
        post.setStatus(PostStatus.ACTIVE);
        post.setCreatedAt(LocalDateTime.now());

        postRepository.create(post);
    }

    @Override
    public Post reply(Long parentPostId, Post reply) {
        Post parent = postRepository.get(parentPostId);

        reply.setAuthor(currentUser.getCurrentUser());
        reply.setTopic(parent.getTopic());
        reply.setParent(parent);
        reply.setStatus(PostStatus.ACTIVE);
        reply.setCreatedAt(LocalDateTime.now());
        postRepository.create(reply);

        return reply;
    }

    @Override
    public void updatePost(Post post) {
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.update(post);
    }

    @Override public void deletePost(long id) { postRepository.delete(id); }
}
