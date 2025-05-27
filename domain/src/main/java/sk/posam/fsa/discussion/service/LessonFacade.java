package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.Assignment;
import sk.posam.fsa.discussion.Lesson;
import sk.posam.fsa.discussion.VideoMaterial;

import java.util.List;
import java.util.Optional;

public interface LessonFacade {

    Assignment createAssignment(Long lessonId, Assignment assignment);
    List<Assignment> getAssignments(Long lessonId);

    VideoMaterial createVideoMaterial(Long lessonId, VideoMaterial videoMaterial);
    List<VideoMaterial> getVideoMaterials(Long lessonId);
    VideoMaterial updateVideoMaterial(Long lessonId, Long videoMaterialId, VideoMaterial videoMaterial);
    void deleteVideoMaterial(Long lessonId, Long videoMaterialId);
}
