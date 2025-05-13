package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.Post;
import sk.posam.fsa.discussion.Topic;

import java.util.Collection;

public interface ForumFacade {
    Collection<Topic> getTopics(long courseId, int page, int size);
    Topic getTopic(long id);
    void createTopic(Topic topic);
    void updateTopic(Topic topic);
    void deleteTopic(long id);

    Collection<Post> getRootPosts(long topicId, int page, int size);
    Post getPost(long id);
    void createPost(Post post);
    Post reply(Long parentPostId, Post reply);
    void updatePost(Post post);
    void deletePost(long id);
}