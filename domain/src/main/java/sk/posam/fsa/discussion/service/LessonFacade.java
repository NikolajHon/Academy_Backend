package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.Assignment;
import sk.posam.fsa.discussion.Lesson;

import java.util.List;
import java.util.Optional;

public interface LessonFacade {

    Assignment createAssignment(Long lessonId, Assignment assignment);
    List<Assignment> getAssignments(Long lessonId);
}
