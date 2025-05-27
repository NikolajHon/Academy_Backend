package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.Lesson;
import sk.posam.fsa.discussion.Assignment;
import sk.posam.fsa.discussion.Course;
import sk.posam.fsa.discussion.exceptions.EducationAppException;
import sk.posam.fsa.discussion.exceptions.ResourceNotFoundException;
import sk.posam.fsa.discussion.exceptions.ResourceAlreadyExistsException;
import sk.posam.fsa.discussion.repository.LessonRepository;
import sk.posam.fsa.discussion.repository.CourseRepository;
import sk.posam.fsa.discussion.repository.AssignmentRepository;

import java.util.List;

public class LessonService implements LessonFacade {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final AssignmentRepository assignmentRepository;

    public LessonService(LessonRepository lessonRepository,
                         CourseRepository courseRepository,
                         AssignmentRepository assignmentRepository) {
        this.lessonRepository      = lessonRepository;
        this.courseRepository      = courseRepository;
        this.assignmentRepository  = assignmentRepository;
    }
@Override
    public Assignment createAssignment(Long lessonId, Assignment assignment) {
        // 1) Найти урок по его ID или выбросить ошибку, если не найден
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Lesson with id=" + lessonId + " not found"
                ));

        // 2) Добавить новое задание в коллекцию урока
        lesson.getAssignments().add(assignment);

        // 3) Сохранить урок — благодаря cascade ALL Hibernate автоматически вставит
        //    запись в таблицу assignment и заполнит lesson_id
        lessonRepository.save(lesson);

        // 4) Вернуть только что сохранённый объект Assignment (его ID уже проставлен)
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
}
