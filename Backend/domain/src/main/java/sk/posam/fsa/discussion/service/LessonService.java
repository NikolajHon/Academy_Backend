// sk.posam.fsa.discussion.service.LessonService

package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class LessonService implements LessonFacade {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final AssignmentRepository assignmentRepository;

    public LessonService(LessonRepository lessonRepository,
                         CourseRepository courseRepository,
                         AssignmentRepository assignmentRepository) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public void createLesson(Lesson lesson) {
        Course course = courseRepository.findAll().stream()
                .filter(c -> c.getId().equals(lesson.getCourse().getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        lesson.setCourse(course);
        course.getLessons().add(lesson);
        lessonRepository.save(lesson);
    }

    @Override
    public Assignment createAssignment(Long lessonId, Assignment assignment) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new NoSuchElementException("Lesson not found: " + lessonId));
        assignment.setLesson(lesson);

        if (assignment.getTestCases() != null) {
            assignment.getTestCases().forEach(tc -> tc.setAssignment(assignment));
        }

        return assignmentRepository.create(assignment);
    }


    @Override
    public List<Assignment> getAssignmentsByLesson(Long lessonId) {
        return assignmentRepository.getAssignmentsByCourse(lessonId);
    }
}
