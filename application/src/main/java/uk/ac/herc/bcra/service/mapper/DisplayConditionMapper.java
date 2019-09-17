package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.DisplayConditionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DisplayCondition} and its DTO {@link DisplayConditionDTO}.
 */
@Mapper(componentModel = "spring", uses = {QuestionGroupMapper.class})
public interface DisplayConditionMapper extends EntityMapper<DisplayConditionDTO, DisplayCondition> {

    @Mapping(source = "questionGroup.id", target = "questionGroupId")
    DisplayConditionDTO toDto(DisplayCondition displayCondition);

    @Mapping(source = "questionGroupId", target = "questionGroup")
    DisplayCondition toEntity(DisplayConditionDTO displayConditionDTO);

    default DisplayCondition fromId(Long id) {
        if (id == null) {
            return null;
        }
        DisplayCondition displayCondition = new DisplayCondition();
        displayCondition.setId(id);
        return displayCondition;
    }
}
