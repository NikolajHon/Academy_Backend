package sk.posam.fsa.discussion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.posam.fsa.discussion.service.CourseFacade;
import sk.posam.fsa.discussion.service.CourseService;


@Configuration
public class CourseBeanConfiguration {

    @Bean
    public CourseFacade courseFacade(CourseRepository courseRepository) {
        return new CourseService(courseRepository);
    }
}
