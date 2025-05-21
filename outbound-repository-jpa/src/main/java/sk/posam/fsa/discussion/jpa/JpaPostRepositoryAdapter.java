package sk.posam.fsa.discussion.jpa;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import sk.posam.fsa.discussion.Post;
import sk.posam.fsa.discussion.repository.PostRepository;

import java.util.Collection;

@Repository
public class JpaPostRepositoryAdapter implements PostRepository {

    private final PostSpringDataRepository repo;

    public JpaPostRepositoryAdapter(PostSpringDataRepository repo) {
        this.repo = repo;
    }

    @Override
    public Post get(long id) { return repo.getReferenceById(id); }

    @Override
    public Collection<Post> getRootsByTopic(long topicId, int p, int s) {
        return repo.findByTopicIdAndParentIsNull(topicId,
                        PageRequest.of(p, s))
                .getContent();
    }

    @Override public void create(Post p) { repo.save(p); }
    @Override public void update(Post p) { repo.save(p); }
    @Override public void delete(long id) { repo.deleteById(id); }
}