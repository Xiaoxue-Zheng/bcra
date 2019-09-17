package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.CheckboxQuestionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CheckboxQuestion} and its DTO {@link CheckboxQuestionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CheckboxQuestionMapper extends EntityMapper<CheckboxQuestionDTO, CheckboxQuestion> {


    @Mapping(target = "checkboxQuestionItems", ignore = true)
    @Mapping(target = "removeCheckboxQuestionItem", ignore = true)
    CheckboxQuestion toEntity(CheckboxQuestionDTO checkboxQuestionDTO);

    default CheckboxQuestion fromId(Long id) {
        if (id == null) {
            return null;
        }
        CheckboxQuestion checkboxQuestion = new CheckboxQuestion();
        checkboxQuestion.setId(id);
        return checkboxQuestion;
    }
}
