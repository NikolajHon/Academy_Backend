package sk.posam.fsa.discussion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.posam.fsa.discussion.repository.UserRatingRepository;
import sk.posam.fsa.discussion.service.UserRatingFacade;
import sk.posam.fsa.discussion.service.UserRatingService;

@Configuration
public class UserRatingBeanConfiguration {

    @Bean
    public UserRatingFacade userRatingFacade(UserRatingRepository userRatingRepository) {
        return new UserRatingService(userRatingRepository);
    }
}
