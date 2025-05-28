package sk.posam.fsa.discussion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.posam.fsa.discussion.repository.AssignmentRepository;
import sk.posam.fsa.discussion.repository.CourseRepository;
import sk.posam.fsa.discussion.repository.LessonRepository;
import sk.posam.fsa.discussion.service.LessonFacade;
import sk.posam.fsa.discussion.service.LessonService;

@Configuration
public class LessonBeanConfiguration {

    @Bean
    public LessonFacade lessonFacade(LessonRepository lessonRepository) {
        return new LessonService(lessonRepository);
    }
}
