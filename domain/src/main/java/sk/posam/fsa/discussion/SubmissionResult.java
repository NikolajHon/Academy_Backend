package sk.posam.fsa.discussion;

import java.util.List;

public record SubmissionResult(boolean allPassed,
                               List<TestCaseResult> testCaseResults) {}