package uk.ac.herc.bcra.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import uk.ac.herc.bcra.domain.AnswerGroup;
import uk.ac.herc.bcra.service.dto.AnswerGroupDTO;

/**
 * Mapper for the entity {@link AnswerGroup} and its DTO {@link AnswerGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {AnswerSectionMapper.class, AnswerMapper.class})
public interface AnswerGroupMapper extends EntityMapper<AnswerGroupDTO, AnswerGroup> {

    @Mapping(source = "answerSection.id", target = "answerSectionId")
    AnswerGroupDTO toDto(AnswerGroup answerGroup);

    @Mapping(source = "answerSectionId", target = "answerSection")
    @Mapping(source = "answers", target = "answers")
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
