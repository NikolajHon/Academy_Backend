package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.*;
import sk.posam.fsa.discussion.repository.*;

public class ForumService implements ForumFacade {

    private final TopicRepository  topicRepository;
    private final PostRepository   postRepository;
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final CurrentUserPort  currentUser;      // ← новый порт

    public ForumService(TopicRepository topicRepository,
                        PostRepository postRepository,
                        CourseRepository courseRepository,
                        LessonRepository lessonRepository,
                        CurrentUserPort currentUser) {     // ← внедряем
        this.topicRepository  = topicRepository;
        this.postRepository   = postRepository;
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
        this.currentUser      = currentUser;
    }


    @Override
    public java.util.Collection<Topic> getTopics(long courseId, int page, int size) {
        return topicRepository.getByCourse(courseId, page, size);
    }

    @Override
    public Topic getTopic(long id) {
        return topicRepository.get(id);
    }

    @Override
    public void createTopic(Topic topic) {

        Course course = courseRepository.getCourse(topic.getCourse().getId());
        topic.setCourse(course);

        topic.setCreatedBy(currentUser.getCurrentUser());

        topic.setStatus(TopicStatus.OPEN);
        topic.setCreatedAt(java.time.LocalDateTime.now());

        topicRepository.create(topic);
    }

    @Override
    public void updateTopic(Topic topic) {
        topicRepository.update(topic);
    }

    @Override
    public void deleteTopic(long id) {
        topicRepository.delete(id);
    }

    @Override
    public java.util.Collection<Post> getRootPosts(long topicId, int page, int size) {
        return postRepository.getRootsByTopic(topicId, page, size);
    }

    @Override
    public Post getPost(long id) {
        return postRepository.get(id);
    }

    @Override
    public void createPost(Post post) {

        Topic topic = topicRepository.get(post.getTopic().getId());
        post.setTopic(topic);

        post.setAuthor(currentUser.getCurrentUser());

        post.setStatus(PostStatus.ACTIVE);
        post.setCreatedAt(java.time.LocalDateTime.now());

        postRepository.create(post);
    }

    @Override
    public void reply(Long parentPostId, Post reply) {

        Post parent = postRepository.get(parentPostId);

        reply.setAuthor(currentUser.getCurrentUser());

        reply.setTopic(parent.getTopic());
        reply.setStatus(PostStatus.ACTIVE);
        reply.setCreatedAt(java.time.LocalDateTime.now());

        parent.addReply(reply);
        postRepository.update(parent);
    }

    @Override
    public void updatePost(Post post) {
        post.setUpdatedAt(java.time.LocalDateTime.now());
        postRepository.update(post);
    }

    @Override
    public void deletePost(long id) {
        postRepository.delete(id);
    }
}
