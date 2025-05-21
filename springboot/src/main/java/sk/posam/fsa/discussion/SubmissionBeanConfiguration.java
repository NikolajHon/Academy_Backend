package sk.posam.fsa.discussion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.posam.fsa.discussion.repository.AssignmentRepository;
import sk.posam.fsa.discussion.service.CodeExecutionPort;
import sk.posam.fsa.discussion.service.SubmissionFacade;
import sk.posam.fsa.discussion.service.SubmissionService;

@Configuration
public class SubmissionBeanConfiguration {

    @Bean
    public SubmissionFacade submissionFacade(AssignmentRepository assignmentRepository,
                                             CodeExecutionPort codeExecutionPort) {

        return new SubmissionService(assignmentRepository, codeExecutionPort);
    }
}