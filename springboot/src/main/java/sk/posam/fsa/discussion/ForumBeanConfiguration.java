package sk.posam.fsa.discussion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.posam.fsa.discussion.repository.*;
import sk.posam.fsa.discussion.repository.CurrentUserRepository;
import sk.posam.fsa.discussion.service.ForumFacade;
import sk.posam.fsa.discussion.service.ForumService;

@Configuration
public class ForumBeanConfiguration {

    @Bean
    ForumFacade forumFacade(TopicRepository topicRepo,
                            PostRepository postRepo,
                            CourseRepository courseRepo,
                            CurrentUserRepository currentUser,
                            EmailSenderRepository mail) {
        return new ForumService(topicRepo, postRepo, courseRepo,  currentUser, mail);
    }
}
