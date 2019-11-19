package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.IdentifiableDataDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link IdentifiableData} and its DTO {@link IdentifiableDataDTO}.
 */
@Mapper(componentModel = "spring", uses = {ParticipantMapper.class, CsvFileMapper.class})
public interface IdentifiableDataMapper extends EntityMapper<IdentifiableDataDTO, IdentifiableData> {

    @Mapping(source = "participant.id", target = "participantId")
    @Mapping(source = "csvFile.id", target = "csvFileId")
    @Mapping(source = "csvFile.fileName", target = "csvFileFileName")
    IdentifiableDataDTO toDto(IdentifiableData identifiableData);

    @Mapping(source = "participantId", target = "participant")
    @Mapping(source = "csvFileId", target = "csvFile")
    IdentifiableData toEntity(IdentifiableDataDTO identifiableDataDTO);

    default IdentifiableData fromId(Long id) {
        if (id == null) {
            return null;
        }
        IdentifiableData identifiableData = new IdentifiableData();
        identifiableData.setId(id);
        return identifiableData;
    }
}
