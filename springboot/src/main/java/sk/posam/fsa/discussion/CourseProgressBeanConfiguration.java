package sk.posam.fsa.discussion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.posam.fsa.discussion.repository.ProgressRepository;
import sk.posam.fsa.discussion.service.CourseProgressFacade;
import sk.posam.fsa.discussion.service.CourseProgressService;

@Configuration
public class CourseProgressBeanConfiguration {

    @Bean
    public CourseProgressFacade courseProgressFacade(ProgressRepository progressRepository) {
        return new CourseProgressService(progressRepository);
    }
}
