package sk.posam.fsa.discussion;

import java.util.List;
import java.util.Optional;

public interface AssignmentRepository {
    Assignment create(Assignment assignment);
    List<Assignment> getAssignmentsByCourse(Long courseId);
}
