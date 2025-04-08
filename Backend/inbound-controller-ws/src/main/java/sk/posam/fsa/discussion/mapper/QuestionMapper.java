package sk.posam.fsa.discussion.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sk.posam.fsa.discussion.Question;
import sk.posam.fsa.discussion.rest.dto.CreateQuestionRequestDto;
import sk.posam.fsa.discussion.rest.dto.QuestionDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    @Mapping(source = "questionBody", target = "question_body")
    @Mapping(source = "answer.answerBody", target = "answer.answer_body")
    Question toDomain(CreateQuestionRequestDto dto);

    @Mapping(source = "question_body", target = "questionBody")
    @Mapping(source = "answer.answer_body", target = "answer.answerBody")
    QuestionDto toDto(Question question);
}


