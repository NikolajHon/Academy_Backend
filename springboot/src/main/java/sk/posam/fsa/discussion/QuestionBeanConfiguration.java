package sk.posam.fsa.discussion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.posam.fsa.discussion.repository.LessonRepository;
import sk.posam.fsa.discussion.repository.QuestionRepository;
import sk.posam.fsa.discussion.service.QuestionFacade;
import sk.posam.fsa.discussion.service.QuestionService;

@Configuration
public class QuestionBeanConfiguration {

    @Bean
    public QuestionFacade questionFacade(QuestionRepository qRepo,
                                         LessonRepository lessonRepo) {
        return new QuestionService(qRepo, lessonRepo);
    }

}
