package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.*;
import sk.posam.fsa.discussion.repository.AssignmentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class SubmissionService implements SubmissionFacade {

    private final AssignmentRepository assignmentRepo;
    private final CodeExecutionPort    executor;

    public SubmissionService(AssignmentRepository assignmentRepo,
                             CodeExecutionPort executor) {
        this.assignmentRepo = assignmentRepo;
        this.executor       = executor;
    }

    @Override
    public SubmissionResult submit(Long assignmentId, String studentCode) {

        System.out.println("=== SUBMIT =========================================");
        System.out.println("assignmentId = " + assignmentId);
        System.out.println("studentCode:\n" + studentCode);

        Assignment assignment = assignmentRepo.findById(assignmentId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Assignment not found: " + assignmentId));

        List<TestCaseResult> results = new ArrayList<>();

        for (TestCase tc : assignment.getTestCases()) {

            System.out.println("\n--- Test-case " + tc.getId() + " ---");
            System.out.println("input           = " + tc.getInput());
            System.out.println("expectedOutput  = " + tc.getExpectedOutput());

            String merged = mergeSource(assignment.getTeacherCode(), studentCode, tc);
            System.out.println("mergedSource:\n" + merged);

            CodeExecutionResult exec = executor.execute(
                    new CodeExecutionRequest(merged, "c", "4")
            );

            System.out.println("JDoodle status  = " + exec.statusCode());
            System.out.println("stdout:\n" + exec.stdout());
            System.out.println("stderr:\n" + exec.stderr());

            boolean passed = exec.stdout().trim()
                    .equals(tc.getExpectedOutput().trim());


            System.out.println("passed          = " + passed);

            String actual = exec.stdout().trim();

            results.add(new TestCaseResult(
                    tc.getId(),
                    tc.getInput(),
                    tc.getExpectedOutput(),
                    actual,
                    passed));
        }

        boolean allPassed = results.stream().allMatch(TestCaseResult::passed);
        System.out.println("\n=== RESULT: allPassed = " + allPassed
                + "  (" + results.stream().filter(TestCaseResult::passed).count()
                + "/" + results.size() + ") =============================\n");

        return new SubmissionResult(allPassed, results);
    }


    /**
     * Teacher-template must contain two плейсхолдерa:
     *  {{STUDENT_CODE}} – блок с кодом студента
     *  {{INPUT}}        – входные данные тест-кейса
     */
    private String mergeSource(String teacherTemplate,
                               String studentCode,
                               TestCase tc) {

        return teacherTemplate
                .replace("{{STUDENT_CODE}}", studentCode)
                .replace("{{INPUT}}", tc.getInput());
    }
}