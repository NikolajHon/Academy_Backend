package sk.posam.fsa.discussion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import sk.posam.fsa.discussion.service.UserFacade;
import sk.posam.fsa.discussion.service.UserService;

@Configuration
public class UserBeanConfiguration {

    @Bean
    public UserFacade userFacade(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 CourseRepository courseRepository) {
        return new UserService(userRepository, passwordEncoder, courseRepository);
    }
}
