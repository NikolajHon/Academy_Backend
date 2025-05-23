package sk.posam.fsa.discussion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.posam.fsa.discussion.repository.LessonRepository;
import sk.posam.fsa.discussion.repository.VideoMaterialRepository;
import sk.posam.fsa.discussion.service.VideoMaterialFacade;
import sk.posam.fsa.discussion.service.VideoMaterialService;

@Configuration
public class VideoMaterialBeanConfiguration {

    @Bean
    public VideoMaterialFacade videoMaterialFacade(
            LessonRepository lessonRepository,
            VideoMaterialRepository videoMaterialRepository) {
        return new VideoMaterialService(lessonRepository, videoMaterialRepository);
    }
}