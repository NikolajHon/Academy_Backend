
package sk.posam.fsa.discussion.repository;

import sk.posam.fsa.discussion.Assignment;

import java.util.List;
import java.util.Optional;

public interface AssignmentRepository {
    Assignment create(Assignment assignment);
    Optional<Assignment> findById(Long id);
    List<Assignment> getAssignmentsByCourse(Long courseId);
    Assignment update(Assignment assignment);
    void deleteById(Long id);
}
