package sk.posam.fsa.discussion.jpa;

import org.springframework.stereotype.Repository;
import sk.posam.fsa.discussion.Assignment;
import sk.posam.fsa.discussion.TestCase;
import sk.posam.fsa.discussion.exceptions.ResourceNotFoundException;
import sk.posam.fsa.discussion.repository.AssignmentRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaAssignmentRepositoryAdapter implements AssignmentRepository {

    private final AssignmentSpringDataRepository repo;

    public JpaAssignmentRepositoryAdapter(AssignmentSpringDataRepository repo) {
        this.repo = repo;
    }

    @Override
    public Assignment create(Assignment assignment) {
        return repo.save(assignment);
    }

    @Override
    public Optional<Assignment> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Assignment update(Assignment assignment) {
        return repo.save(assignment);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<TestCase> getTestCases(Long assignmentId) {
        Assignment assignment = repo.findById(assignmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Assignment with id=" + assignmentId + " not found")
                );
        return assignment.getTestCases();
    }
}
