package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.DisplayConditionDTO;
import uk.ac.herc.bcra.service.dto.QuestionDTO;
import uk.ac.herc.bcra.service.dto.QuestionItemDTO;
import uk.ac.herc.bcra.service.dto.ReferralConditionDTO;

import java.util.Set;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Question} and its DTO {@link QuestionDTO}.
 */
@Mapper(componentModel = "spring", uses = {QuestionGroupMapper.class})
public interface QuestionMapper extends EntityMapper<QuestionDTO, Question> {

    Set<QuestionItemDTO> questionItemsToQuestionItemDTOs(Set<QuestionItem> questionItems);
    Set<DisplayConditionDTO> displayConditionsToDisplayConditionDTOs(Set<DisplayCondition> displayConditions);
    Set<ReferralConditionDTO> referralConditionsToReferralConditionDTOs(Set<ReferralCondition> referralConditions);

    QuestionDTO toDto(Question question);

    @Mapping(target = "displayConditions", ignore = true)
    @Mapping(target = "removeDisplayCondition", ignore = true)
    @Mapping(target = "referralConditions", ignore = true)
    @Mapping(target = "removeReferralCondition", ignore = true)
    @Mapping(target = "questionItems", ignore = true)
    @Mapping(target = "removeQuestionItem", ignore = true)
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
