package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.ParticipantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Participant} and its DTO {@link ParticipantDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ParticipantMapper extends EntityMapper<ParticipantDTO, Participant> {

    @Mapping(source = "identifiableData.nhsNumber", target = "nhsNumber")
    @Mapping(source = "identifiableData.practiceName", target = "practiceName")
    @Mapping(source = "user.createdDate", target = "importedDatetime")
    @Mapping(source = "participant.registerDatetime", target = "registerDatetime")
    @Mapping(source = "participant.lastLoginDatetime", target = "lastLoginDatetime")
    @Mapping(source = "participant.procedure.consentResponse.state", target = "consentStatus")
    @Mapping(source = "participant.procedure.riskAssesmentResponse.state", target = "questionnaireStatus")
    ParticipantDTO toDto(Participant participant);

    @Mapping(target = "identifiableData", ignore=true)
    @Mapping(target = "registerDatetime", ignore=true)
    @Mapping(target = "lastLoginDatetime", ignore=true)
    Participant toEntity(ParticipantDTO participantDTO);

    default Participant fromId(Long id) {
        if (id == null) {
            return null;
        }
        Participant participant = new Participant();
        participant.setId(id);
        return participant;
    }
}
