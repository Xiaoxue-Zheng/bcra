package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.DisplayConditionDTO;
import uk.ac.herc.bcra.service.dto.QuestionGroupDTO;
import uk.ac.herc.bcra.service.dto.QuestionSectionDTO;
import uk.ac.herc.bcra.service.dto.ReferralConditionDTO;

import java.util.Set;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link QuestionSection} and its DTO {@link QuestionSectionDTO}.
 */
@Mapper(componentModel = "spring", uses = {QuestionnaireMapper.class, QuestionGroupMapper.class})
public interface QuestionSectionMapper extends EntityMapper<QuestionSectionDTO, QuestionSection> {

    QuestionGroupDTO questionGroupToQuestionGroupDTO(QuestionGroupDTO QuestionGroup);
    Set<DisplayConditionDTO> displayConditionsToDisplayConditionDTOs(Set<DisplayCondition> displayConditions);
    Set<ReferralConditionDTO> referralConditionsToReferralConditionDTOs(Set<ReferralCondition> referralConditions);

    QuestionSectionDTO toDto(QuestionSection questionSection);

    @Mapping(target = "displayConditions", ignore = true)
    @Mapping(target = "removeDisplayCondition", ignore = true)
    @Mapping(target = "referralConditions", ignore = true)
    @Mapping(target = "removeReferralCondition", ignore = true)
    QuestionSection toEntity(QuestionSectionDTO questionSectionDTO);

    default QuestionSection fromId(Long id) {
        if (id == null) {
            return null;
        }
        QuestionSection questionSection = new QuestionSection();
        questionSection.setId(id);
        return questionSection;
    }
}
