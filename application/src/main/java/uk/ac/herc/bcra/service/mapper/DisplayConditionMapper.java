package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.DisplayConditionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DisplayCondition} and its DTO {@link DisplayConditionDTO}.
 */
@Mapper(componentModel = "spring", uses = {QuestionSectionMapper.class, QuestionMapper.class})
public interface DisplayConditionMapper extends EntityMapper<DisplayConditionDTO, DisplayCondition> {

    DisplayConditionDTO toDto(DisplayCondition displayCondition);

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
