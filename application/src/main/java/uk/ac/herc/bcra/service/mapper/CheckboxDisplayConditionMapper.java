package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.CheckboxDisplayConditionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CheckboxDisplayCondition} and its DTO {@link CheckboxDisplayConditionDTO}.
 */
@Mapper(componentModel = "spring", uses = {CheckboxQuestionItemMapper.class})
public interface CheckboxDisplayConditionMapper extends EntityMapper<CheckboxDisplayConditionDTO, CheckboxDisplayCondition> {

    @Mapping(source = "checkboxQuestionItem.id", target = "checkboxQuestionItemId")
    CheckboxDisplayConditionDTO toDto(CheckboxDisplayCondition checkboxDisplayCondition);

    @Mapping(source = "checkboxQuestionItemId", target = "checkboxQuestionItem")
    CheckboxDisplayCondition toEntity(CheckboxDisplayConditionDTO checkboxDisplayConditionDTO);

    default CheckboxDisplayCondition fromId(Long id) {
        if (id == null) {
            return null;
        }
        CheckboxDisplayCondition checkboxDisplayCondition = new CheckboxDisplayCondition();
        checkboxDisplayCondition.setId(id);
        return checkboxDisplayCondition;
    }
}
