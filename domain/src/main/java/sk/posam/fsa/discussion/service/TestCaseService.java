package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.Assignment;
import sk.posam.fsa.discussion.TestCase;
import sk.posam.fsa.discussion.exceptions.EducationAppException;
import sk.posam.fsa.discussion.exceptions.ResourceNotFoundException;
import sk.posam.fsa.discussion.repository.AssignmentRepository;
import sk.posam.fsa.discussion.repository.TestCaseRepository;

import java.util.List;

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
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Assignment with id=" + assignmentId + " not found"
                ));
        try {
            return testCaseRepo.create(testCase);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException(
                    "Failed to create test case for assignmentId=" + assignmentId, e
            );
        }
    }


    @Override
    public void delete(Long assignmentId, Long testCaseId) {
        TestCase tc = testCaseRepo.findById(testCaseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "TestCase with id=" + testCaseId + " not found"
                ));
        try {
            testCaseRepo.delete(tc);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException(
                    "Failed to delete test case id=" + testCaseId, e
            );
        }
    }
}
