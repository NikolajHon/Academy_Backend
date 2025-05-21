package sk.posam.fsa.discussion.jpa;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import sk.posam.fsa.discussion.Topic;
import sk.posam.fsa.discussion.repository.TopicRepository;

import java.util.Collection;

@Repository
public class JpaTopicRepositoryAdapter implements TopicRepository {

    private final TopicSpringDataRepository repo;

    public JpaTopicRepositoryAdapter(TopicSpringDataRepository repo) {
        this.repo = repo;
    }

    @Override
    public Topic get(long id) { return repo.getReferenceById(id); }

    @Override
    public Collection<Topic> getByCourse(long courseId, int page, int size) {
        return repo.findByCourseId(courseId, PageRequest.of(page, size))
                .getContent();
    }

    @Override public void create(Topic t) { repo.save(t); }
    @Override public void update(Topic t) { repo.save(t); }
    @Override public void delete(long id) { repo.deleteById(id); }
}