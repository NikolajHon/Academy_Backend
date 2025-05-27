
package sk.posam.fsa.discussion.repository;

import sk.posam.fsa.discussion.Assignment;
import sk.posam.fsa.discussion.TestCase;

import java.util.List;
import java.util.Optional;

public interface AssignmentRepository {
    Assignment create(Assignment assignment);
    Optional<Assignment> findById(Long id);
    Assignment update(Assignment assignment);
    void deleteById(Long id);
    List<TestCase> getTestCases(Long assignmentId);
}
