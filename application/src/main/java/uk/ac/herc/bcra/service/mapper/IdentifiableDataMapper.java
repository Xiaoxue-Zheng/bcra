package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.IdentifiableDataDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link IdentifiableData} and its DTO {@link IdentifiableDataDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IdentifiableDataMapper extends EntityMapper<IdentifiableDataDTO, IdentifiableData> {



    default IdentifiableData fromId(Long id) {
        if (id == null) {
            return null;
        }
        IdentifiableData identifiableData = new IdentifiableData();
        identifiableData.setId(id);
        return identifiableData;
    }
}
