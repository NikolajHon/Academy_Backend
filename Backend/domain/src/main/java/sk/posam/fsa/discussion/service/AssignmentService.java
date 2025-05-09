package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.Assignment;
import sk.posam.fsa.discussion.AssignmentRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class AssignmentService implements AssignmentFacade {

    private final AssignmentRepository repo;

    public AssignmentService(AssignmentRepository repo) {
        this.repo = repo;
    }

    @Override
    public Assignment create(Assignment assignment) {
        return repo.create(assignment);
    }

    @Override
    public Optional<Assignment> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public List<Assignment> getAssignmentsByCourse(Long courseId) {
        return repo.getAssignmentsByCourse(courseId);
    }

    @Override
    public Assignment update(Long id, Assignment assignment) {
        if (repo.findById(id).isEmpty()) {
            throw new NoSuchElementException("Assignment not found: " + id);
        }
        assignment.setId(id);
        return repo.update(assignment);
    }

    @Override
    public void delete(Long id) {
        if (repo.findById(id).isEmpty()) {
            throw new NoSuchElementException("Assignment not found: " + id);
        }
        repo.deleteById(id);
    }
}
