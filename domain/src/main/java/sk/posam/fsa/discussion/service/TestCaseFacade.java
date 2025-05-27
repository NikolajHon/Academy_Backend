package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.TestCase;
import java.util.List;

public interface TestCaseFacade {
    TestCase create(Long assignmentId, TestCase testCase);
    void delete(Long assignmentId, Long testCaseId);
}
