package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.RadioAnswerItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RadioAnswerItem} and its DTO {@link RadioAnswerItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {RadioAnswerMapper.class})
public interface RadioAnswerItemMapper extends EntityMapper<RadioAnswerItemDTO, RadioAnswerItem> {

    @Mapping(source = "radioAnswer.id", target = "radioAnswerId")
    RadioAnswerItemDTO toDto(RadioAnswerItem radioAnswerItem);

    @Mapping(source = "radioAnswerId", target = "radioAnswer")
    RadioAnswerItem toEntity(RadioAnswerItemDTO radioAnswerItemDTO);

    default RadioAnswerItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        RadioAnswerItem radioAnswerItem = new RadioAnswerItem();
        radioAnswerItem.setId(id);
        return radioAnswerItem;
    }
}
