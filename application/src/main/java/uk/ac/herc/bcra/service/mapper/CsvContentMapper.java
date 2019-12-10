package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.CsvContentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CsvContent} and its DTO {@link CsvContentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CsvContentMapper extends EntityMapper<CsvContentDTO, CsvContent> {


    @Mapping(target = "csvFile", ignore = true)
    CsvContent toEntity(CsvContentDTO csvContentDTO);

    default CsvContent fromId(Long id) {
        if (id == null) {
            return null;
        }
        CsvContent csvContent = new CsvContent();
        csvContent.setId(id);
        return csvContent;
    }
}
