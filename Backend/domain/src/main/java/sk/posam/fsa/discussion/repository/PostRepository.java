package sk.posam.fsa.discussion.repository;

import sk.posam.fsa.discussion.Post;

import java.util.Collection;

public interface PostRepository {
    Post get(long id);
    Collection<Post> getRootsByTopic(long topicId, int page, int size);
    void create(Post post);
    void update(Post post);
    void delete(long id);
}