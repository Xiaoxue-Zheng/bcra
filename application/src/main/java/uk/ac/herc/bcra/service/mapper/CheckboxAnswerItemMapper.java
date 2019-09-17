package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.CheckboxAnswerItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CheckboxAnswerItem} and its DTO {@link CheckboxAnswerItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {CheckboxAnswerMapper.class, CheckboxQuestionItemMapper.class})
public interface CheckboxAnswerItemMapper extends EntityMapper<CheckboxAnswerItemDTO, CheckboxAnswerItem> {

    @Mapping(source = "checkboxAnswer.id", target = "checkboxAnswerId")
    @Mapping(source = "checkboxQuestionItem.id", target = "checkboxQuestionItemId")
    CheckboxAnswerItemDTO toDto(CheckboxAnswerItem checkboxAnswerItem);

    @Mapping(source = "checkboxAnswerId", target = "checkboxAnswer")
    @Mapping(source = "checkboxQuestionItemId", target = "checkboxQuestionItem")
    CheckboxAnswerItem toEntity(CheckboxAnswerItemDTO checkboxAnswerItemDTO);

    default CheckboxAnswerItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        CheckboxAnswerItem checkboxAnswerItem = new CheckboxAnswerItem();
        checkboxAnswerItem.setId(id);
        return checkboxAnswerItem;
    }
}
