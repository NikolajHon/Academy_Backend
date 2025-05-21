package sk.posam.fsa.discussion.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.posam.fsa.discussion.Lesson;

public interface LessonSpringDataRepository extends JpaRepository<Lesson, Long> {
}
