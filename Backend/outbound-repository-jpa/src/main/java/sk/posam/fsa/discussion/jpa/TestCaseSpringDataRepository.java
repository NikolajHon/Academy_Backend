package sk.posam.fsa.discussion.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.posam.fsa.discussion.TestCase;
import java.util.List;

public interface TestCaseSpringDataRepository extends JpaRepository<TestCase, Long> {
    List<TestCase> findAllByAssignment_Id(Long assignmentId);
}
