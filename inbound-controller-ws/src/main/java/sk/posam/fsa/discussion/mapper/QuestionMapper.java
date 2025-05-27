package sk.posam.fsa.discussion.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sk.posam.fsa.discussion.Question;
import sk.posam.fsa.discussion.rest.dto.CreateQuestionRequestDto;
import sk.posam.fsa.discussion.rest.dto.QuestionDto;
import sk.posam.fsa.discussion.rest.dto.QuestionPublicDto;
import sk.posam.fsa.discussion.rest.dto.UpdateQuestionRequestDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lesson", ignore = true)
    Question toDomain(CreateQuestionRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lesson", ignore = true)
    Question toDomain(UpdateQuestionRequestDto dto);
    List<QuestionPublicDto> toPublicDto(List<Question> questions);

    QuestionDto toDto(Question question);
    List<QuestionDto> toDto(List<Question> questions);
}
