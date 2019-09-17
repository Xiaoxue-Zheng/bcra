package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.RepeatQuestionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RepeatQuestion} and its DTO {@link RepeatQuestionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RepeatQuestionMapper extends EntityMapper<RepeatQuestionDTO, RepeatQuestion> {


    @Mapping(target = "repeatDisplayConditions", ignore = true)
    @Mapping(target = "removeRepeatDisplayCondition", ignore = true)
    RepeatQuestion toEntity(RepeatQuestionDTO repeatQuestionDTO);

    default RepeatQuestion fromId(Long id) {
        if (id == null) {
            return null;
        }
        RepeatQuestion repeatQuestion = new RepeatQuestion();
        repeatQuestion.setId(id);
        return repeatQuestion;
    }
}
