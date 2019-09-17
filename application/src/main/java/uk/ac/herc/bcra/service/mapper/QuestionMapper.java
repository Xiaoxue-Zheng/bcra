package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.QuestionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Question} and its DTO {@link QuestionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface QuestionMapper extends EntityMapper<QuestionDTO, Question> {


    @Mapping(target = "questionGroupQuestions", ignore = true)
    @Mapping(target = "removeQuestionGroupQuestion", ignore = true)
    @Mapping(target = "answers", ignore = true)
    @Mapping(target = "removeAnswer", ignore = true)
    Question toEntity(QuestionDTO questionDTO);

    default Question fromId(Long id) {
        if (id == null) {
            return null;
        }
        Question question = new Question();
        question.setId(id);
        return question;
    }
}
