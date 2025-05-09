package sk.posam.fsa.discussion.mapper;

import org.mapstruct.Mapper;
import sk.posam.fsa.discussion.TestCase;
import sk.posam.fsa.discussion.rest.dto.CreateTestCaseRequestDto;
import sk.posam.fsa.discussion.rest.dto.TestCaseDto;

@Mapper(componentModel = "spring")
public interface TestCaseMapper {
    TestCase toDomain(CreateTestCaseRequestDto dto);
    TestCaseDto toDto(TestCase domain);
}
