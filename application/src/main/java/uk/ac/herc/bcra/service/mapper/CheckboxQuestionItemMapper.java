package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.CheckboxQuestionItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CheckboxQuestionItem} and its DTO {@link CheckboxQuestionItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {CheckboxQuestionMapper.class})
public interface CheckboxQuestionItemMapper extends EntityMapper<CheckboxQuestionItemDTO, CheckboxQuestionItem> {

    @Mapping(source = "checkboxQuestion.id", target = "checkboxQuestionId")
    CheckboxQuestionItemDTO toDto(CheckboxQuestionItem checkboxQuestionItem);

    @Mapping(source = "checkboxQuestionId", target = "checkboxQuestion")
    @Mapping(target = "checkboxAnswerItems", ignore = true)
    @Mapping(target = "removeCheckboxAnswerItem", ignore = true)
    @Mapping(target = "checkboxDisplayConditions", ignore = true)
    @Mapping(target = "removeCheckboxDisplayCondition", ignore = true)
    CheckboxQuestionItem toEntity(CheckboxQuestionItemDTO checkboxQuestionItemDTO);

    default CheckboxQuestionItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        CheckboxQuestionItem checkboxQuestionItem = new CheckboxQuestionItem();
        checkboxQuestionItem.setId(id);
        return checkboxQuestionItem;
    }
}
