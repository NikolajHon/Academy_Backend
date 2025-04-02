package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.Course;
import sk.posam.fsa.discussion.CourseRepository;
import sk.posam.fsa.discussion.Lesson;
import sk.posam.fsa.discussion.LessonRepository;

public class LessonService implements LessonFacade {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    public LessonService(LessonRepository lessonRepository, CourseRepository courseRepository) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void createLesson(Lesson lesson) {
        Course course = courseRepository.findAll()
                .stream()
                .filter(c -> c.getId().equals(lesson.getCourse().getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        lesson.setCourse(course);
        course.getLessons().add(lesson);
        lessonRepository.save(lesson);
    }
}
