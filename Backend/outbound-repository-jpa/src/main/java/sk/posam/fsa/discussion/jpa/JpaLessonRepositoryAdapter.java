package sk.posam.fsa.discussion.jpa;

import org.springframework.stereotype.Repository;
import sk.posam.fsa.discussion.Lesson;
import sk.posam.fsa.discussion.LessonRepository;

@Repository
public class JpaLessonRepositoryAdapter implements LessonRepository {

    private final LessonSpringDataRepository springDataRepository;

    public JpaLessonRepositoryAdapter(LessonSpringDataRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Lesson save(Lesson lesson) {
        return springDataRepository.save(lesson);
    }
}
