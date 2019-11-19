package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.CsvFileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CsvFile} and its DTO {@link CsvFileDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CsvFileMapper extends EntityMapper<CsvFileDTO, CsvFile> {



    default CsvFile fromId(Long id) {
        if (id == null) {
            return null;
        }
        CsvFile csvFile = new CsvFile();
        csvFile.setId(id);
        return csvFile;
    }
}
