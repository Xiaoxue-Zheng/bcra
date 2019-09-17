package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.RadioAnswerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RadioAnswer} and its DTO {@link RadioAnswerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RadioAnswerMapper extends EntityMapper<RadioAnswerDTO, RadioAnswer> {


    @Mapping(target = "radioAnswerItem", ignore = true)
    RadioAnswer toEntity(RadioAnswerDTO radioAnswerDTO);

    default RadioAnswer fromId(Long id) {
        if (id == null) {
            return null;
        }
        RadioAnswer radioAnswer = new RadioAnswer();
        radioAnswer.setId(id);
        return radioAnswer;
    }
}
