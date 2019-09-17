package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.RadioQuestionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RadioQuestion} and its DTO {@link RadioQuestionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RadioQuestionMapper extends EntityMapper<RadioQuestionDTO, RadioQuestion> {


    @Mapping(target = "radioQuestionItems", ignore = true)
    @Mapping(target = "removeRadioQuestionItem", ignore = true)
    RadioQuestion toEntity(RadioQuestionDTO radioQuestionDTO);

    default RadioQuestion fromId(Long id) {
        if (id == null) {
            return null;
        }
        RadioQuestion radioQuestion = new RadioQuestion();
        radioQuestion.setId(id);
        return radioQuestion;
    }
}
