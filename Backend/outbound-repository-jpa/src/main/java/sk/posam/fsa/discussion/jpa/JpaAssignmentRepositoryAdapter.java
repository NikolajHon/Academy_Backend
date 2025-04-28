package sk.posam.fsa.discussion.jpa;

import org.springframework.stereotype.Repository;
import sk.posam.fsa.discussion.Assignment;
import sk.posam.fsa.discussion.AssignmentRepository;

import java.util.List;
import java.util.Optional;
@Repository
public class JpaAssignmentRepositoryAdapter implements AssignmentRepository{

    private final AssignmentSpringDataRepository assignmentSpringDataRepository;

    public JpaAssignmentRepositoryAdapter(AssignmentSpringDataRepository assignmentSpringDataRepository) {
        this.assignmentSpringDataRepository = assignmentSpringDataRepository;
    }

    @Override
    public Assignment create(Assignment assignment) {
        return assignmentSpringDataRepository.save(assignment);
    }

    @Override
    public List<Assignment> getAssignmentsByCourse(Long courseId) {
        return assignmentSpringDataRepository.findAllByLesson_Id(courseId);
    }
}
