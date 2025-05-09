package sk.posam.fsa.discussion;

import java.util.List;
import java.util.Optional;

public interface TestCaseRepository {
    TestCase create(TestCase testCase);
    Optional<TestCase> findById(Long id);
    List<TestCase> findByAssignmentId(Long assignmentId);
    void delete(TestCase testCase);
}
