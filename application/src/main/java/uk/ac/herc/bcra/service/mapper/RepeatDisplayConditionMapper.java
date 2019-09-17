package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.RepeatDisplayConditionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RepeatDisplayCondition} and its DTO {@link RepeatDisplayConditionDTO}.
 */
@Mapper(componentModel = "spring", uses = {RepeatQuestionMapper.class})
public interface RepeatDisplayConditionMapper extends EntityMapper<RepeatDisplayConditionDTO, RepeatDisplayCondition> {

    @Mapping(source = "repeatQuestion.id", target = "repeatQuestionId")
    RepeatDisplayConditionDTO toDto(RepeatDisplayCondition repeatDisplayCondition);

    @Mapping(source = "repeatQuestionId", target = "repeatQuestion")
    RepeatDisplayCondition toEntity(RepeatDisplayConditionDTO repeatDisplayConditionDTO);

    default RepeatDisplayCondition fromId(Long id) {
        if (id == null) {
            return null;
        }
        RepeatDisplayCondition repeatDisplayCondition = new RepeatDisplayCondition();
        repeatDisplayCondition.setId(id);
        return repeatDisplayCondition;
    }
}
