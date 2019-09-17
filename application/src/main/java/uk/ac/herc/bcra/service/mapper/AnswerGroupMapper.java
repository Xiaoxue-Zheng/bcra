package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.AnswerGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnswerGroup} and its DTO {@link AnswerGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {AnswerResponseMapper.class, QuestionGroupMapper.class})
public interface AnswerGroupMapper extends EntityMapper<AnswerGroupDTO, AnswerGroup> {

    @Mapping(source = "answerResponse.id", target = "answerResponseId")
    @Mapping(source = "questionGroup.id", target = "questionGroupId")
    AnswerGroupDTO toDto(AnswerGroup answerGroup);

    @Mapping(source = "answerResponseId", target = "answerResponse")
    @Mapping(source = "questionGroupId", target = "questionGroup")
    @Mapping(target = "answers", ignore = true)
    @Mapping(target = "removeAnswer", ignore = true)
    AnswerGroup toEntity(AnswerGroupDTO answerGroupDTO);

    default AnswerGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        AnswerGroup answerGroup = new AnswerGroup();
        answerGroup.setId(id);
        return answerGroup;
    }
}
