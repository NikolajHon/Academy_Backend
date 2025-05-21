package sk.posam.fsa.discussion.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sk.posam.fsa.discussion.SubmissionResult;
import sk.posam.fsa.discussion.TestCaseResult;
import sk.posam.fsa.discussion.rest.dto.SubmissionResponseDto;
import sk.posam.fsa.discussion.rest.dto.TestCaseResultDto;

@Mapper(componentModel = "spring")
public interface SubmissionMapper {
    @Mapping(target = "results", source = "testCaseResults")
    SubmissionResponseDto toDto(SubmissionResult domain);
    TestCaseResultDto toDto(TestCaseResult domain);
}
