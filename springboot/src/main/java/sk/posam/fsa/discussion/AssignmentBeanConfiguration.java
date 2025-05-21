package sk.posam.fsa.discussion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.posam.fsa.discussion.repository.AssignmentRepository;
import sk.posam.fsa.discussion.repository.TestCaseRepository;
import sk.posam.fsa.discussion.service.AssignmentFacade;
import sk.posam.fsa.discussion.service.AssignmentService;
import sk.posam.fsa.discussion.service.TestCaseFacade;
import sk.posam.fsa.discussion.service.TestCaseService;

@Configuration
public class AssignmentBeanConfiguration {

    @Bean
    public AssignmentFacade assignmentFacade(AssignmentRepository assignmentRepository) {
        return new AssignmentService(assignmentRepository);
    }

    @Bean
    public TestCaseFacade testCaseFacade(AssignmentRepository assignmentRepository,
                                         TestCaseRepository testCaseRepository) {
        return new TestCaseService(assignmentRepository, testCaseRepository);
    }
}
