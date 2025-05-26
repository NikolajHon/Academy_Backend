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
    public void createLesson(Lesson lesson) {
        Course course = courseRepository.findAll().stream()
                .filter(c -> c.getId().equals(lesson.getCourse().getId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course with id=" + lesson.getCourse().getId() + " not found"
                ));

        lesson.setCourse(course);
        course.getLessons().add(lesson);

        try {
            lessonRepository.save(lesson);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException("Failed to create lesson", e);
        }
    }

    @Override
    public Assignment createAssignment(Long lessonId, Assignment assignment) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Lesson with id=" + lessonId + " not found"
                ));

        if (assignment.getId() != null
                && assignmentRepository.findById(assignment.getId()).isPresent()) {
            throw new ResourceAlreadyExistsException(
                    "Assignment with id=" + assignment.getId() + " already exists"
            );
        }

        assignment.setLesson(lesson);
        if (assignment.getTestCases() != null) {
            assignment.getTestCases().forEach(tc -> tc.setAssignment(assignment));
        }

        try {
            return assignmentRepository.create(assignment);
        } catch (ResourceAlreadyExistsException raee) {
            throw raee;
        } catch (RuntimeException | Error e) {
            throw new EducationAppException(
                    "Failed to create assignment for lessonId=" + lessonId, e
            );
        }
    }

    @Override
    public List<Assignment> getAssignmentsByLesson(Long lessonId) {
        try {
            return assignmentRepository.getAssignmentsByCourse(lessonId);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException(
                    "Failed to load assignments for lessonId=" + lessonId, e
            );
        }
    }
}
