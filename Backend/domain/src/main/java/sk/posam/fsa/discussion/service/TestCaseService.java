package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.Assignment;
import sk.posam.fsa.discussion.AssignmentRepository;
import sk.posam.fsa.discussion.TestCase;
import sk.posam.fsa.discussion.TestCaseRepository;

import java.util.List;
import java.util.NoSuchElementException;

public class TestCaseService implements TestCaseFacade {

    private final AssignmentRepository assignmentRepo;
    private final TestCaseRepository testCaseRepo;

    public TestCaseService(AssignmentRepository assignmentRepo,
                           TestCaseRepository testCaseRepo) {
        this.assignmentRepo = assignmentRepo;
        this.testCaseRepo = testCaseRepo;
    }

    @Override
    public TestCase create(Long assignmentId, TestCase testCase) {
        Assignment a = assignmentRepo.findById(assignmentId)
                .orElseThrow(() -> new NoSuchElementException("Assignment not found: " + assignmentId));
        testCase.setAssignment(a);
        return testCaseRepo.create(testCase);
    }

    @Override
    public List<TestCase> getByAssignment(Long assignmentId) {
        return testCaseRepo.findByAssignmentId(assignmentId);
    }

    @Override
    public void delete(Long assignmentId, Long testCaseId) {
        TestCase tc = testCaseRepo.findById(testCaseId)
                .orElseThrow(() -> new NoSuchElementException("TestCase not found: " + testCaseId));
        if (!tc.getAssignment().getId().equals(assignmentId)) {
            throw new IllegalArgumentException("TestCase does not belong to assignment");
        }
        testCaseRepo.delete(tc);
    }
}
