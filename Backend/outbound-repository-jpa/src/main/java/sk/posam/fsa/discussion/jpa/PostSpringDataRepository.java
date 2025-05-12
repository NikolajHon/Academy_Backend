package sk.posam.fsa.discussion.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.posam.fsa.discussion.Post;

@Repository
public interface PostSpringDataRepository
        extends JpaRepository<Post, Long> {

    Page<Post> findByTopicIdAndParentIsNull(long topicId, Pageable p);
}