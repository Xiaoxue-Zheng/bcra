package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.NumberCheckboxAnswerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link NumberCheckboxAnswer} and its DTO {@link NumberCheckboxAnswerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NumberCheckboxAnswerMapper extends EntityMapper<NumberCheckboxAnswerDTO, NumberCheckboxAnswer> {



    default NumberCheckboxAnswer fromId(Long id) {
        if (id == null) {
            return null;
        }
        NumberCheckboxAnswer numberCheckboxAnswer = new NumberCheckboxAnswer();
        numberCheckboxAnswer.setId(id);
        return numberCheckboxAnswer;
    }
}
