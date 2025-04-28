package sk.posam.fsa.discussion.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.posam.fsa.discussion.Assignment;

import java.util.List;

public interface AssignmentSpringDataRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findAllByLesson_Id(Long lessonId);
}
