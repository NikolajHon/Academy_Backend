package sk.posam.fsa.discussion.service;


import sk.posam.fsa.discussion.Assignment;
import sk.posam.fsa.discussion.AssignmentRepository;

import java.util.List;
import java.util.Optional;

public class AssignmentService implements AssignmentFacade {

    private final AssignmentRepository assignmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public Assignment create(Assignment assignment) {
        return assignmentRepository.create(assignment);
    }

    @Override
    public List<Assignment> getAssignmentsByCourse(Long courseId) {
        return assignmentRepository.getAssignmentsByCourse(courseId);
    }
}
