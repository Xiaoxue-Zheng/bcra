package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.AnswerItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnswerItem} and its DTO {@link AnswerItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {AnswerMapper.class, QuestionItemMapper.class})
public interface AnswerItemMapper extends EntityMapper<AnswerItemDTO, AnswerItem> {

    @Mapping(source = "answer.id", target = "answerId")
    @Mapping(source = "questionItem.id", target = "questionItemId")
    AnswerItemDTO toDto(AnswerItem answerItem);

    @Mapping(source = "answerId", target = "answer")
    @Mapping(source = "questionItemId", target = "questionItem")
    AnswerItem toEntity(AnswerItemDTO answerItemDTO);

    default AnswerItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        AnswerItem answerItem = new AnswerItem();
        answerItem.setId(id);
        return answerItem;
    }
}
