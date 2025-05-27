package sk.posam.fsa.discussion.repository;

import sk.posam.fsa.discussion.TestCase;

import java.util.List;
import java.util.Optional;

public interface TestCaseRepository {
    TestCase create(TestCase testCase);
    Optional<TestCase> findById(Long id);
    void delete(TestCase testCase);
}
