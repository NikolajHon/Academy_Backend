package sk.posam.fsa.discussion.jpa;

import org.springframework.stereotype.Repository;
import sk.posam.fsa.discussion.CourseProgress;
import sk.posam.fsa.discussion.CourseProgressId;
import sk.posam.fsa.discussion.repository.ProgressRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaCourseProgressRepositoryAdapter implements ProgressRepository {

    private final CourseProgressSpringDataRepository repository;

    public JpaCourseProgressRepositoryAdapter(CourseProgressSpringDataRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<CourseProgress> findById(CourseProgressId id) {
        return repository.findById(id);
    }

    @Override
    public CourseProgress save(CourseProgress progress) {
        return repository.save(progress);
    }

    @Override
    public void delete(CourseProgress progress) {
        repository.delete(progress);
    }

    @Override
    public List<CourseProgress> findAllByCourseId(Long courseId) {
        return repository.findAllByCourseProgressIdCourseId(courseId);
    }

    @Override
    public void deleteByCourseIdAndUserId(Long courseId, Long userId) {
        repository.deleteByCourseProgressIdCourseIdAndCourseProgressIdUserId(courseId, userId);
    }


}
