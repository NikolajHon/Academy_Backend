package sk.posam.fsa.discussion;

public record TestCaseResult(Long   testCaseId,
                             String input,
                             String expectedOutput,
                             String actualOutput,
                             boolean passed) {}
