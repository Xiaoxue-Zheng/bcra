package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.QuestionItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link QuestionItem} and its DTO {@link QuestionItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {QuestionMapper.class})
public interface QuestionItemMapper extends EntityMapper<QuestionItemDTO, QuestionItem> {

    QuestionItemDTO toDto(QuestionItem questionItem);

    QuestionItem toEntity(QuestionItemDTO questionItemDTO);

    default QuestionItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        QuestionItem questionItem = new QuestionItem();
        questionItem.setId(id);
        return questionItem;
    }
}
