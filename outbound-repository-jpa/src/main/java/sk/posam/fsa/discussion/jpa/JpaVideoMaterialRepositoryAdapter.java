package sk.posam.fsa.discussion.jpa;

import org.springframework.stereotype.Repository;
import sk.posam.fsa.discussion.VideoMaterial;
import sk.posam.fsa.discussion.repository.VideoMaterialRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaVideoMaterialRepositoryAdapter implements VideoMaterialRepository {

    private final VideoMaterialSpringDataRepository spring;

    public JpaVideoMaterialRepositoryAdapter(VideoMaterialSpringDataRepository spring) {
        this.spring = spring;
    }

    @Override
    public VideoMaterial save(VideoMaterial vm) {
        return spring.save(vm);
    }

    @Override
    public List<VideoMaterial> findAllByLessonId(Long lessonId) {
        return spring.findAllByLessonId(lessonId);
    }

    @Override
    public Optional<VideoMaterial> findById(Long id) {
        return spring.findById(id);
    }
    @Override
    public void delete(VideoMaterial vm) {
        spring.delete(vm);
    }
}