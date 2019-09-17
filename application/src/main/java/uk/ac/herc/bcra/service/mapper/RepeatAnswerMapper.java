package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.RepeatAnswerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RepeatAnswer} and its DTO {@link RepeatAnswerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RepeatAnswerMapper extends EntityMapper<RepeatAnswerDTO, RepeatAnswer> {



    default RepeatAnswer fromId(Long id) {
        if (id == null) {
            return null;
        }
        RepeatAnswer repeatAnswer = new RepeatAnswer();
        repeatAnswer.setId(id);
        return repeatAnswer;
    }
}
