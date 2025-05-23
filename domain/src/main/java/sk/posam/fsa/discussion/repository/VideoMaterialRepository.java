package sk.posam.fsa.discussion.repository;

import sk.posam.fsa.discussion.VideoMaterial;

import java.util.List;
import java.util.Optional;

public interface VideoMaterialRepository {
    VideoMaterial save(VideoMaterial vm);
    List<VideoMaterial> findAllByLessonId(Long lessonId);
    Optional<VideoMaterial> findById(Long id);
    void delete(VideoMaterial vm);
}
