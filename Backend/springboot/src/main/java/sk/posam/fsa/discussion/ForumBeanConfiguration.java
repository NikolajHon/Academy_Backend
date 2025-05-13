package sk.posam.fsa.discussion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.posam.fsa.discussion.repository.CourseRepository;
import sk.posam.fsa.discussion.repository.LessonRepository;
import sk.posam.fsa.discussion.repository.PostRepository;
import sk.posam.fsa.discussion.repository.TopicRepository;
import sk.posam.fsa.discussion.service.CurrentUserPort;
import sk.posam.fsa.discussion.service.ForumFacade;
import sk.posam.fsa.discussion.service.ForumService;

@Configuration
public class ForumBeanConfiguration {

    @Bean
    ForumFacade forumFacade(TopicRepository topicRepo,
                            PostRepository postRepo,
                            CourseRepository courseRepo,
                            CurrentUserPort currentUser) {
        return new ForumService(topicRepo, postRepo, courseRepo,  currentUser);
    }
}
