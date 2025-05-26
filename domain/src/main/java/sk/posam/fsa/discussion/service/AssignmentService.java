package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.Assignment;
import sk.posam.fsa.discussion.exceptions.EducationAppException;
import sk.posam.fsa.discussion.exceptions.ResourceAlreadyExistsException;
import sk.posam.fsa.discussion.exceptions.ResourceNotFoundException;
import sk.posam.fsa.discussion.repository.AssignmentRepository;

import java.util.List;
import java.util.Optional;

public class AssignmentService implements AssignmentFacade {

    private final AssignmentRepository repo;

    public AssignmentService(AssignmentRepository repo) {
        this.repo = repo;
    }

    @Override
    public Assignment create(Assignment assignment) {
        if (assignment.getId() != null && repo.findById(assignment.getId()).isPresent()) {
            throw new ResourceAlreadyExistsException(
                    "Assignment with id=" + assignment.getId() + " already exists"
            );
        }
        try {
            return repo.create(assignment);
        } catch (ResourceAlreadyExistsException raee) {
            throw raee;
        } catch (RuntimeException | Error e) {
            throw new EducationAppException("Failed to create assignment", e);
        }
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
            throw new ResourceNotFoundException(
                    "Assignment with id=" + id + " not found"
            );
        }
        assignment.setId(id);
        try {
            return repo.update(assignment);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException("Failed to update assignment", e);
        }
    }

    @Override
    public void delete(Long id) {
        if (repo.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(
                    "Assignment with id=" + id + " not found"
            );
        }
        try {
            repo.deleteById(id);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException("Failed to delete assignment", e);
        }
    }
}
