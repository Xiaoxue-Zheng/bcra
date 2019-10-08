package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.QuestionSectionDTO;
import uk.ac.herc.bcra.service.dto.QuestionnaireDTO;

import java.util.Set;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Questionnaire} and its DTO {@link QuestionnaireDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface QuestionnaireMapper extends EntityMapper<QuestionnaireDTO, Questionnaire> {

    Set<QuestionSectionDTO> questionSectionsToQuestionSectionDTOs(Set<QuestionSection> questionSection);

    @Mapping(target = "questionSections", ignore = true)
    @Mapping(target = "removeQuestionSection", ignore = true)
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
