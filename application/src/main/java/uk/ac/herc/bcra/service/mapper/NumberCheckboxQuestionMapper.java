package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.NumberCheckboxQuestionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link NumberCheckboxQuestion} and its DTO {@link NumberCheckboxQuestionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NumberCheckboxQuestionMapper extends EntityMapper<NumberCheckboxQuestionDTO, NumberCheckboxQuestion> {



    default NumberCheckboxQuestion fromId(Long id) {
        if (id == null) {
            return null;
        }
        NumberCheckboxQuestion numberCheckboxQuestion = new NumberCheckboxQuestion();
        numberCheckboxQuestion.setId(id);
        return numberCheckboxQuestion;
    }
}
