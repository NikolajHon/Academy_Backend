package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.CourseProgress;
import sk.posam.fsa.discussion.CourseProgressId;
import sk.posam.fsa.discussion.repository.ProgressRepository;

import java.util.Collections;
import java.util.List;

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

        // получить или создать новую сущность прогресса
        CourseProgress progress = progressRepository
                .findById(id)
                .orElseGet(() -> {
                    CourseProgress cp = new CourseProgress(id);
                    // изначально list пустой
                    return cp;
                });

        // добавить урок, если ещё не пройден
        if (!progress.getLessonIds().contains(lessonId)) {
            progress.getLessonIds().add(lessonId);
            progressRepository.save(progress);
        }
        // иначе — ничего не делаем
    }

    @Override
    public void removeLessonProgress(Long courseId, Long userId, Long lessonId) {
        CourseProgressId id = new CourseProgressId(courseId, userId);

        progressRepository.findById(id).ifPresent(progress -> {
            // убрать урок из списка
            boolean removed = progress.getLessonIds().remove(lessonId);
            if (removed) {
                if (progress.getLessonIds().isEmpty()) {
                    // если больше нет пройденных уроков — удалить запись совсем
                    progressRepository.delete(progress);
                } else {
                    // иначе — просто сохранить обновлённый список
                    progressRepository.save(progress);
                }
            }
        });
    }
}
