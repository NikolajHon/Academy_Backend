package sk.posam.fsa.discussion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sk.posam.fsa.discussion.VideoMaterial;
import sk.posam.fsa.discussion.mapper.VideoMaterialMapper;
import sk.posam.fsa.discussion.rest.api.VideoApi;
import sk.posam.fsa.discussion.rest.dto.CreateVideoMaterialRequestDto;
import sk.posam.fsa.discussion.rest.dto.VideoMaterialDto;
import sk.posam.fsa.discussion.service.VideoMaterialFacade;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class VideoMaterialRestController implements VideoApi {

    private final VideoMaterialFacade facade;
    private final VideoMaterialMapper mapper;

    public VideoMaterialRestController(VideoMaterialFacade facade,
                                       VideoMaterialMapper mapper) {
        this.facade = facade;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<VideoMaterialDto> createVideoMaterial(
            Long lessonId,
            CreateVideoMaterialRequestDto dto) {

        VideoMaterial domain = mapper.toDomain(dto);
        VideoMaterial saved = facade.createVideoMaterial(lessonId, domain);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.toDto(saved));
    }

    @Override
    public ResponseEntity<List<VideoMaterialDto>> getVideoMaterialsByLesson(
            Long lessonId) {

        List<VideoMaterial> list = facade.getByLesson(lessonId);
        List<VideoMaterialDto> dtos = list.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    @Override
    public ResponseEntity<Void> deleteVideoMaterial(
            Long lessonId,
            Long videoMaterialId) {
        facade.deleteVideoMaterial(videoMaterialId);
        return ResponseEntity.noContent().build();
    }
}