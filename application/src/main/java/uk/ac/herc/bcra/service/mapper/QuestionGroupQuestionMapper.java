package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.QuestionGroupQuestionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link QuestionGroupQuestion} and its DTO {@link QuestionGroupQuestionDTO}.
 */
@Mapper(componentModel = "spring", uses = {QuestionGroupMapper.class, QuestionMapper.class})
public interface QuestionGroupQuestionMapper extends EntityMapper<QuestionGroupQuestionDTO, QuestionGroupQuestion> {

    QuestionGroupQuestionDTO toDto(QuestionGroupQuestion questionGroupQuestion);

    QuestionGroupQuestion toEntity(QuestionGroupQuestionDTO questionGroupQuestionDTO);

    default QuestionGroupQuestion fromId(Long id) {
        if (id == null) {
            return null;
        }
        QuestionGroupQuestion questionGroupQuestion = new QuestionGroupQuestion();
        questionGroupQuestion.setId(id);
        return questionGroupQuestion;
    }
}
