package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.QuestionnaireDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Questionnaire} and its DTO {@link QuestionnaireDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface QuestionnaireMapper extends EntityMapper<QuestionnaireDTO, Questionnaire> {


    @Mapping(target = "questionnaireQuestionGroups", ignore = true)
    @Mapping(target = "removeQuestionnaireQuestionGroup", ignore = true)
    @Mapping(target = "answerResponses", ignore = true)
    @Mapping(target = "removeAnswerResponse", ignore = true)
    Questionnaire toEntity(QuestionnaireDTO questionnaireDTO);

    default Questionnaire fromId(Long id) {
        if (id == null) {
            return null;
        }
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setId(id);
        return questionnaire;
    }
}
