package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.CheckboxAnswerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CheckboxAnswer} and its DTO {@link CheckboxAnswerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CheckboxAnswerMapper extends EntityMapper<CheckboxAnswerDTO, CheckboxAnswer> {


    @Mapping(target = "checkboxAnswerItems", ignore = true)
    @Mapping(target = "removeCheckboxAnswerItem", ignore = true)
    CheckboxAnswer toEntity(CheckboxAnswerDTO checkboxAnswerDTO);

    default CheckboxAnswer fromId(Long id) {
        if (id == null) {
            return null;
        }
        CheckboxAnswer checkboxAnswer = new CheckboxAnswer();
        checkboxAnswer.setId(id);
        return checkboxAnswer;
    }
}
