package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.Assignment;

import java.util.List;
import java.util.Optional;

public interface AssignmentFacade {
    Assignment create(Assignment assignment);
    Optional<Assignment> findById(Long id);
    List<Assignment> getAssignmentsByCourse(Long courseId);
    Assignment update(Long id, Assignment assignment);
    void delete(Long id);
}
