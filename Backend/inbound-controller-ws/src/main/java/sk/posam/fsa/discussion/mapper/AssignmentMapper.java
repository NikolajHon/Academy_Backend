
package sk.posam.fsa.discussion.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sk.posam.fsa.discussion.Assignment;
import sk.posam.fsa.discussion.rest.dto.AssignmentDto;
import sk.posam.fsa.discussion.rest.dto.CreateAssignmentRequestDto;
import sk.posam.fsa.discussion.rest.dto.UpdateAssignmentRequestDto;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "testCases", source = "testCases")
    Assignment toDomain(CreateAssignmentRequestDto dto);

    Assignment toDomain(UpdateAssignmentRequestDto dto);

    AssignmentDto toDto(Assignment domain);
}
