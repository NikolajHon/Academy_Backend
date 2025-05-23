package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.VideoMaterial;

import java.util.List;

public interface VideoMaterialFacade {
    VideoMaterial createVideoMaterial(Long lessonId, VideoMaterial vm);
    List<VideoMaterial> getByLesson(Long lessonId);
    void deleteVideoMaterial(Long videoMaterialId);
}