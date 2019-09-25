package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.QuestionGroupDTO;
import uk.ac.herc.bcra.service.dto.QuestionGroupQuestionDTO;

import java.util.Set;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link QuestionGroup} and its DTO {@link QuestionGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface QuestionGroupMapper extends EntityMapper<QuestionGroupDTO, QuestionGroup> {

    Set<QuestionGroupQuestionDTO> questionGroupQuestionToQuestionGroupQuestionDTOs(Set<QuestionGroupQuestion> questionGroupQuestions);

    @Mapping(target = "displayConditions", ignore = true)
    @Mapping(target = "removeDisplayCondition", ignore = true)
    @Mapping(target = "questionnaireQuestionGroups", ignore = true)
    @Mapping(target = "removeQuestionnaireQuestionGroup", ignore = true)
    @Mapping(target = "questionGroupQuestions", ignore = true)
    @Mapping(target = "removeQuestionGroupQuestion", ignore = true)
    @Mapping(target = "answerGroups", ignore = true)
    @Mapping(target = "removeAnswerGroup", ignore = true)
    QuestionGroup toEntity(QuestionGroupDTO questionGroupDTO);

    default QuestionGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        QuestionGroup questionGroup = new QuestionGroup();
        questionGroup.setId(id);
        return questionGroup;
    }
}
