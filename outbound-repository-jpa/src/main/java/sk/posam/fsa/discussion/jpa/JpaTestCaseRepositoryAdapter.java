package sk.posam.fsa.discussion.jpa;

import org.springframework.stereotype.Repository;
import sk.posam.fsa.discussion.TestCase;
import sk.posam.fsa.discussion.repository.TestCaseRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaTestCaseRepositoryAdapter implements TestCaseRepository {

    private final TestCaseSpringDataRepository repo;

    public JpaTestCaseRepositoryAdapter(TestCaseSpringDataRepository repo) {
        this.repo = repo;
    }

    @Override
    public TestCase create(TestCase testCase) {
        return repo.save(testCase);
    }

    @Override
    public Optional<TestCase> findById(Long id) {
        return repo.findById(id);
    }


    @Override
    public void delete(TestCase testCase) {
        repo.delete(testCase);
    }
}
