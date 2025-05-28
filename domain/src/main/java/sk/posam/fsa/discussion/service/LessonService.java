package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.Lesson;
import sk.posam.fsa.discussion.Assignment;
import sk.posam.fsa.discussion.Course;
import sk.posam.fsa.discussion.VideoMaterial;
import sk.posam.fsa.discussion.exceptions.EducationAppException;
import sk.posam.fsa.discussion.exceptions.ResourceNotFoundException;
import sk.posam.fsa.discussion.exceptions.ResourceAlreadyExistsException;
import sk.posam.fsa.discussion.repository.LessonRepository;
import sk.posam.fsa.discussion.repository.CourseRepository;
import sk.posam.fsa.discussion.repository.AssignmentRepository;

import java.util.List;

public class LessonService implements LessonFacade {

    private final LessonRepository lessonRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public Assignment createAssignment(Long lessonId, Assignment assignment) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Lesson with id=" + lessonId + " not found"
                ));

        lesson.getAssignments().add(assignment);
        lessonRepository.save(lesson);

        return assignment;
    }

    @Override
    public List<Assignment> getAssignments(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lesson with id=" + lessonId + " not found")
                );
        return lesson.getAssignments();
    }

    @Override
    public void deleteLesson(Long lessonId) {
        lessonRepository.delete(lessonId);
    }

    @Override
    public VideoMaterial createVideoMaterial(Long lessonId, VideoMaterial vm) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson with id=" + lessonId + " not found"));
        lesson.addVideoMaterial(vm);
        lessonRepository.save(lesson);
        return vm;
    }

    @Override
    public List<VideoMaterial> getVideoMaterials(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson with id=" + lessonId + " not found"));
        return lesson.getVideoMaterials();
    }

    @Override
    public VideoMaterial updateVideoMaterial(Long lessonId, Long videoMaterialId, VideoMaterial vmData) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson with id=" + lessonId + " not found"));
        VideoMaterial vm = lesson.getVideoMaterials().stream()
                .filter(v -> v.getId().equals(videoMaterialId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "VideoMaterial with id=" + videoMaterialId + " not found in lesson " + lessonId));
        vm.setTitle(vmData.getTitle());
        vm.setUrl(vmData.getUrl());
        lessonRepository.save(lesson);
        return vm;
    }

    @Override
    public void deleteVideoMaterial(Long lessonId, Long videoMaterialId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson with id=" + lessonId + " not found"));
        VideoMaterial vm = lesson.getVideoMaterials().stream()
                .filter(v -> v.getId().equals(videoMaterialId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "VideoMaterial with id=" + videoMaterialId + " not found in lesson " + lessonId));
        lesson.removeVideoMaterial(vm);
        lessonRepository.save(lesson);
    }
}
