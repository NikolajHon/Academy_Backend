package sk.posam.fsa.discussion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.posam.fsa.discussion.repository.CourseRepository;
import sk.posam.fsa.discussion.repository.UserRepository;
import sk.posam.fsa.discussion.service.UserFacade;
import sk.posam.fsa.discussion.service.UserService;

@Configuration
public class UserBeanConfiguration {

    @Bean
    public UserFacade userFacade(UserRepository userRepository,
                                 CourseRepository courseRepository) {
        return new UserService(userRepository, courseRepository);
    }
}
