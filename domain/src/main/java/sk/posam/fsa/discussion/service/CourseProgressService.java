package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.CourseProgress;
import sk.posam.fsa.discussion.CourseProgressId;
import sk.posam.fsa.discussion.exceptions.EducationAppException;
import sk.posam.fsa.discussion.exceptions.ResourceAlreadyExistsException;
import sk.posam.fsa.discussion.exceptions.ResourceNotFoundException;
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
        try {
            CourseProgressId id = new CourseProgressId(courseId, userId);
            return progressRepository
                    .findById(id)
                    .map(CourseProgress::getLessonIds)
                    .orElseGet(Collections::emptyList);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException(
                    "Failed to fetch completed lessons for course=" + courseId + ", user=" + userId, e
            );
        }
    }

    @Override
    public void addLessonProgress(Long courseId, Long userId, Long lessonId) {
        CourseProgressId id = new CourseProgressId(courseId, userId);
        CourseProgress progress = progressRepository
                .findById(id)
                .orElseGet(() -> new CourseProgress(id));

        if (progress.getLessonIds().contains(lessonId)) {
            throw new ResourceAlreadyExistsException(
                    "Lesson " + lessonId + " already marked complete for course=" + courseId + ", user=" + userId
            );
        }

        try {
            progress.getLessonIds().add(lessonId);
            progressRepository.save(progress);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException(
                    "Failed to add lesson progress for lesson=" + lessonId
                            + ", course=" + courseId + ", user=" + userId, e
            );
        }
    }

    @Override
    public void removeLessonProgress(Long courseId, Long userId, Long lessonId) {
        CourseProgressId id = new CourseProgressId(courseId, userId);
        Optional<CourseProgress> found = progressRepository.findById(id);

        if (found.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No progress record found for course=" + courseId + ", user=" + userId
            );
        }

        CourseProgress progress = found.get();
        if (!progress.getLessonIds().contains(lessonId)) {
            throw new ResourceNotFoundException(
                    "Lesson " + lessonId + " is not marked complete for course=" + courseId + ", user=" + userId
            );
        }

        try {
            progress.getLessonIds().remove(lessonId);
            if (progress.getLessonIds().isEmpty()) {
                progressRepository.delete(progress);
            } else {
                progressRepository.save(progress);
            }
        } catch (RuntimeException | Error e) {
            throw new EducationAppException(
                    "Failed to remove lesson progress for lesson=" + lessonId
                            + ", course=" + courseId + ", user=" + userId, e
            );
        }
    }

    @Override
    public void save(CourseProgress courseProgress) {
        try {
            progressRepository.save(courseProgress);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException(
                    "Failed to save course progress for " + courseProgress.getCourseProgressId(), e
            );
        }
    }

    @Override
    public Optional<CourseProgress> findById(CourseProgressId id) {
        try {
            return progressRepository.findById(id);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException(
                    "Failed to fetch course progress for " + id, e
            );
        }
    }

    @Override
    public Integer getCourseRating(Long courseId, Long userId) {
        try {
            CourseProgressId id = new CourseProgressId(courseId, userId);
            return progressRepository
                    .findById(id)
                    .map(CourseProgress::getRating)
                    .orElse(0);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException(
                    "Failed to fetch course rating for course=" + courseId + ", user=" + userId, e
            );
        }
    }

    @Override
    public void setCourseRating(Long courseId, Long userId, Integer rating) {
        CourseProgressId id = new CourseProgressId(courseId, userId);
        CourseProgress progress = progressRepository
                .findById(id)
                .orElseGet(() -> new CourseProgress(id));
        progress.setRating(rating);

        try {
            progressRepository.save(progress);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException(
                    "Failed to set course rating for course=" + courseId + ", user=" + userId, e
            );
        }
    }

    @Override
    public List<CourseProgress> listCourseProgressByCourse(Long courseId) {
        try {
            return progressRepository.findAllByCourseId(courseId);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException(
                    "Failed to list course progress for course=" + courseId, e
            );
        }
    }

    @Override
    public void deleteByCourseIdAndUserId(Long courseId, Long userId) {
        CourseProgressId id = new CourseProgressId(courseId, userId);
        if (progressRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(
                    "No progress record to delete for course=" + courseId + ", user=" + userId
            );
        }
        try {
            progressRepository.deleteByCourseIdAndUserId(courseId, userId);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException(
                    "Failed to delete course progress for course=" + courseId + ", user=" + userId, e
            );
        }
    }
}
