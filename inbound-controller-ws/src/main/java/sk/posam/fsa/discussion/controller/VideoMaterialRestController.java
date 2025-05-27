// sk/posam/fsa/discussion/controller/VideoApiController.java
package sk.posam.fsa.discussion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sk.posam.fsa.discussion.rest.api.VideoApi;
import sk.posam.fsa.discussion.rest.dto.VideoMaterialDto;
import sk.posam.fsa.discussion.rest.dto.CreateVideoMaterialRequestDto;
import sk.posam.fsa.discussion.service.LessonFacade;
import sk.posam.fsa.discussion.VideoMaterial;
import sk.posam.fsa.discussion.mapper.VideoMaterialMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class VideoMaterialRestController implements VideoApi {

    private final LessonFacade lessonFacade;
    private final VideoMaterialMapper mapper;

    public VideoMaterialRestController(LessonFacade lessonFacade, VideoMaterialMapper mapper) {
        this.lessonFacade = lessonFacade;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<VideoMaterialDto> createVideoMaterial(
            Long lessonId,
            CreateVideoMaterialRequestDto request) {
        VideoMaterial vm = mapper.fromDto(request);
        VideoMaterial created = lessonFacade.createVideoMaterial(lessonId, vm);
        return new ResponseEntity<>(mapper.toDto(created), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteVideoMaterial(
            Long lessonId,
            Long videoMaterialId) {
        lessonFacade.deleteVideoMaterial(lessonId, videoMaterialId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<VideoMaterialDto>> getVideoMaterialsByLesson(
            Long lessonId) {
        List<VideoMaterial> materials = lessonFacade.getVideoMaterials(lessonId);
        List<VideoMaterialDto> dtos = materials.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
