package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.RadioQuestionItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RadioQuestionItem} and its DTO {@link RadioQuestionItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {RadioQuestionMapper.class})
public interface RadioQuestionItemMapper extends EntityMapper<RadioQuestionItemDTO, RadioQuestionItem> {

    @Mapping(source = "radioQuestion.id", target = "radioQuestionId")
    RadioQuestionItemDTO toDto(RadioQuestionItem radioQuestionItem);

    @Mapping(source = "radioQuestionId", target = "radioQuestion")
    RadioQuestionItem toEntity(RadioQuestionItemDTO radioQuestionItemDTO);

    default RadioQuestionItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        RadioQuestionItem radioQuestionItem = new RadioQuestionItem();
        radioQuestionItem.setId(id);
        return radioQuestionItem;
    }
}
