package sk.posam.fsa.discussion.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.posam.fsa.discussion.Question;

public interface QuestionSpringDataRepository extends JpaRepository<Question, Long> {
}
