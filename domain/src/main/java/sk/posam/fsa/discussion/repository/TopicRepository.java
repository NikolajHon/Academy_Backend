package sk.posam.fsa.discussion.repository;

import sk.posam.fsa.discussion.Topic;

import java.util.Collection;

public interface TopicRepository {
    Topic get(long id);
    Collection<Topic> getByCourse(long courseId, int page, int size);
    void create(Topic topic);
    void update(Topic topic);
    void delete(long id);
}