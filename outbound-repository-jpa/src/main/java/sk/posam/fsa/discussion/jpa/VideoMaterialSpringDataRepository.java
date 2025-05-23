package sk.posam.fsa.discussion.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.posam.fsa.discussion.VideoMaterial;

import java.util.List;

public interface VideoMaterialSpringDataRepository
        extends JpaRepository<VideoMaterial, Long> {
    List<VideoMaterial> findAllByLessonId(Long lessonId);
}