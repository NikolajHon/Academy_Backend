package sk.posam.fsa.discussion.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sk.posam.fsa.discussion.VideoMaterial;
import sk.posam.fsa.discussion.rest.dto.CreateVideoMaterialRequestDto;
import sk.posam.fsa.discussion.rest.dto.VideoMaterialDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VideoMaterialMapper {
    VideoMaterial fromDto(CreateVideoMaterialRequestDto dto);
    VideoMaterialDto toDto(VideoMaterial vm);
    List<VideoMaterialDto> toDto(List<VideoMaterial> vms);
}
