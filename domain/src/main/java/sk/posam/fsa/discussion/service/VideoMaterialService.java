package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.Lesson;
import sk.posam.fsa.discussion.VideoMaterial;
import sk.posam.fsa.discussion.repository.LessonRepository;
import sk.posam.fsa.discussion.repository.VideoMaterialRepository;

import java.util.List;
import java.util.NoSuchElementException;

public class VideoMaterialService implements VideoMaterialFacade {

    private final LessonRepository lessonRepo;
    private final VideoMaterialRepository vmRepo;

    public VideoMaterialService(LessonRepository lessonRepo,
                                VideoMaterialRepository vmRepo) {
        this.lessonRepo = lessonRepo;
        this.vmRepo = vmRepo;
    }

    @Override
    public VideoMaterial createVideoMaterial(Long lessonId, VideoMaterial vm) {
        Lesson lesson = lessonRepo.findById(lessonId)
                .orElseThrow(() -> new NoSuchElementException("Lesson not found: " + lessonId));
        vm.setLesson(lesson);
        lesson.getVideoMaterials().add(vm);
        return vmRepo.save(vm);
    }

    @Override
    public List<VideoMaterial> getByLesson(Long lessonId) {
        return vmRepo.findAllByLessonId(lessonId);
    }
    @Override
    public void deleteVideoMaterial(Long videoMaterialId) {
        VideoMaterial vm = vmRepo.findById(videoMaterialId)
                .orElseThrow(() -> new NoSuchElementException("Video not found: " + videoMaterialId));
        vm.getLesson().getVideoMaterials().remove(vm);
        vmRepo.delete(vm);
    }
}
