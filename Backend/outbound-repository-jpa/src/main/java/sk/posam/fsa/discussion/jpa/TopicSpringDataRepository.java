package sk.posam.fsa.discussion.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.posam.fsa.discussion.Topic;

@Repository
public interface TopicSpringDataRepository
        extends JpaRepository<Topic, Long> {

    Page<Topic> findByCourseId(long courseId, Pageable pageable);
}