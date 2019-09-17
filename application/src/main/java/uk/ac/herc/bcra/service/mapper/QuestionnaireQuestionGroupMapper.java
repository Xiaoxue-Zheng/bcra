package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.QuestionnaireQuestionGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link QuestionnaireQuestionGroup} and its DTO {@link QuestionnaireQuestionGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {QuestionnaireMapper.class, QuestionGroupMapper.class})
public interface QuestionnaireQuestionGroupMapper extends EntityMapper<QuestionnaireQuestionGroupDTO, QuestionnaireQuestionGroup> {

    @Mapping(source = "questionnaire.id", target = "questionnaireId")
    @Mapping(source = "questionGroup.id", target = "questionGroupId")
    QuestionnaireQuestionGroupDTO toDto(QuestionnaireQuestionGroup questionnaireQuestionGroup);

    @Mapping(source = "questionnaireId", target = "questionnaire")
    @Mapping(source = "questionGroupId", target = "questionGroup")
    QuestionnaireQuestionGroup toEntity(QuestionnaireQuestionGroupDTO questionnaireQuestionGroupDTO);

    default QuestionnaireQuestionGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        QuestionnaireQuestionGroup questionnaireQuestionGroup = new QuestionnaireQuestionGroup();
        questionnaireQuestionGroup.setId(id);
        return questionnaireQuestionGroup;
    }
}
