package sk.posam.fsa.discussion.jpa;

import org.springframework.stereotype.Repository;
import sk.posam.fsa.discussion.Lesson;
import sk.posam.fsa.discussion.repository.LessonRepository;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<Lesson> findAll() {
        return springDataRepository.findAll();
    }

    @Override
    public Optional<Lesson> findById(Long id) {
        return springDataRepository.findById(id);
    }

    @Override
    public Lesson get(long id) {
        return springDataRepository.getReferenceById(id);
    }

}
