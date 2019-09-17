package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.AnswerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Answer} and its DTO {@link AnswerDTO}.
 */
@Mapper(componentModel = "spring", uses = {AnswerGroupMapper.class, QuestionMapper.class})
public interface AnswerMapper extends EntityMapper<AnswerDTO, Answer> {

    @Mapping(source = "answerGroup.id", target = "answerGroupId")
    @Mapping(source = "question.id", target = "questionId")
    AnswerDTO toDto(Answer answer);

    @Mapping(source = "answerGroupId", target = "answerGroup")
    @Mapping(source = "questionId", target = "question")
    Answer toEntity(AnswerDTO answerDTO);

    default Answer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Answer answer = new Answer();
        answer.setId(id);
        return answer;
    }
}
