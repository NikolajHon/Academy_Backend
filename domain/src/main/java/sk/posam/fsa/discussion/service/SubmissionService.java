package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.Assignment;
import sk.posam.fsa.discussion.SubmissionResult;
import sk.posam.fsa.discussion.TestCase;
import sk.posam.fsa.discussion.TestCaseResult;
import sk.posam.fsa.discussion.exceptions.EducationAppException;
import sk.posam.fsa.discussion.exceptions.ResourceNotFoundException;
import sk.posam.fsa.discussion.repository.AssignmentRepository;
import sk.posam.fsa.discussion.CodeExecutionRequest;
import sk.posam.fsa.discussion.CodeExecutionResult;

import java.util.ArrayList;
import java.util.List;

public class SubmissionService implements SubmissionFacade {

    private final AssignmentRepository assignmentRepo;
    private final CodeExecutionPort executor;

    public SubmissionService(AssignmentRepository assignmentRepo,
                             CodeExecutionPort executor) {
        this.assignmentRepo = assignmentRepo;
        this.executor       = executor;
    }

    @Override
    public SubmissionResult submit(Long assignmentId, String studentCode) {
        Assignment assignment = assignmentRepo.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Assignment with id=" + assignmentId + " not found"
                ));

        List<TestCaseResult> results = new ArrayList<>();

        for (TestCase tc : assignment.getTestCases()) {
            try {
                String merged = mergeSource(assignment.getTeacherCode(), studentCode, tc);
                CodeExecutionResult exec = executor.execute(
                        new CodeExecutionRequest(merged, assignment.getLanguage().toLowerCase(), "4")
                );
                boolean passed = exec.stdout().trim()
                        .equals(tc.getExpectedOutput().trim());
                results.add(new TestCaseResult(
                        tc.getId(),
                        tc.getInput(),
                        tc.getExpectedOutput(),
                        exec.stdout().trim(),
                        passed
                ));
            } catch (RuntimeException | Error e) {
                throw new EducationAppException(
                        "Failed to execute test-case id=" + tc.getId()
                                + " for assignmentId=" + assignmentId, e
                );
            }
        }

        boolean allPassed = results.stream().allMatch(TestCaseResult::passed);
        return new SubmissionResult(allPassed, results);
    }
    /**
     * Teacher-template must contain two placeholder:
     *  {{STUDENT_CODE}} – student code block
     *  {{INPUT}}        – test case input data
     */
    private String mergeSource(String teacherTemplate,
                               String studentCode,
                               TestCase tc) {
        return teacherTemplate
                .replace("{{STUDENT_CODE}}", studentCode)
                .replace("{{INPUT}}", tc.getInput());
    }
}
