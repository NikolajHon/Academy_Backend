// 5. Реализация CourseProgressService
package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.CourseProgress;
import sk.posam.fsa.discussion.CourseProgressId;
import sk.posam.fsa.discussion.repository.ProgressRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CourseProgressService implements CourseProgressFacade {

    private final ProgressRepository progressRepository;

    public CourseProgressService(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    @Override
    public List<Long> getCompletedLessonIds(Long courseId, Long userId) {
        CourseProgressId id = new CourseProgressId(courseId, userId);
        return progressRepository
                .findById(id)
                .map(CourseProgress::getLessonIds)
                .orElseGet(Collections::emptyList);
    }

    @Override
    public void addLessonProgress(Long courseId, Long userId, Long lessonId) {
        CourseProgressId id = new CourseProgressId(courseId, userId);
        CourseProgress progress = progressRepository
                .findById(id)
                .orElseGet(() -> new CourseProgress(id));
        if (!progress.getLessonIds().contains(lessonId)) {
            progress.getLessonIds().add(lessonId);
            progressRepository.save(progress);
        }
    }

    @Override
    public void removeLessonProgress(Long courseId, Long userId, Long lessonId) {
        CourseProgressId id = new CourseProgressId(courseId, userId);
        progressRepository.findById(id).ifPresent(progress -> {
            if (progress.getLessonIds().remove(lessonId)) {
                if (progress.getLessonIds().isEmpty()) {
                    progressRepository.delete(progress);
                } else {
                    progressRepository.save(progress);
                }
            }
        });
    }

    @Override
    public void save(CourseProgress courseProgress) {
        progressRepository.save(courseProgress);
    }

    @Override
    public Optional<CourseProgress> findById(CourseProgressId id) {
        return progressRepository.findById(id);
    }

    @Override
    public Integer getCourseRating(Long courseId, Long userId) {
        CourseProgressId id = new CourseProgressId(courseId, userId);
        return progressRepository
                .findById(id)
                .map(CourseProgress::getRating)
                .orElse(0);
    }

    @Override
    public void setCourseRating(Long courseId, Long userId, Integer rating) {
        CourseProgressId id = new CourseProgressId(courseId, userId);
        CourseProgress progress = progressRepository
                .findById(id)
                .orElseGet(() -> new CourseProgress(id));
        progress.setRating(rating);
        progressRepository.save(progress);
    }

    @Override
    public List<CourseProgress> listCourseProgressByCourse(Long courseId) {
        return progressRepository.findAllByCourseId(courseId);
    }
    @Override
    public void deleteByCourseIdAndUserId(Long courseId, Long userId){
        progressRepository.deleteByCourseIdAndUserId(courseId, userId);
    }
}
