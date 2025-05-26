package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.Lesson;
import sk.posam.fsa.discussion.VideoMaterial;
import sk.posam.fsa.discussion.exceptions.EducationAppException;
import sk.posam.fsa.discussion.exceptions.ResourceNotFoundException;
import sk.posam.fsa.discussion.exceptions.ResourceAlreadyExistsException;
import sk.posam.fsa.discussion.repository.LessonRepository;
import sk.posam.fsa.discussion.repository.VideoMaterialRepository;

import java.util.List;

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
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Lesson with id=" + lessonId + " not found"
                ));
        if (vm.getId() != null && vmRepo.findById(vm.getId()).isPresent()) {
            throw new ResourceAlreadyExistsException(
                    "VideoMaterial with id=" + vm.getId() + " already exists"
            );
        }
        vm.setLesson(lesson);
        lesson.getVideoMaterials().add(vm);
        try {
            return vmRepo.save(vm);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException(
                    "Failed to create video material for lessonId=" + lessonId, e
            );
        }
    }

    @Override
    public List<VideoMaterial> getByLesson(Long lessonId) {
        try {
            return vmRepo.findAllByLessonId(lessonId);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException(
                    "Failed to load video materials for lessonId=" + lessonId, e
            );
        }
    }

    @Override
    public void deleteVideoMaterial(Long videoMaterialId) {
        VideoMaterial vm = vmRepo.findById(videoMaterialId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "VideoMaterial with id=" + videoMaterialId + " not found"
                ));
        vm.getLesson().getVideoMaterials().remove(vm);
        try {
            vmRepo.delete(vm);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException(
                    "Failed to delete video material id=" + videoMaterialId, e
            );
        }
    }
}
