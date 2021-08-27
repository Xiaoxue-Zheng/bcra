package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.ParticipantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Participant} and its DTO {@link ParticipantDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ParticipantMapper extends EntityMapper<ParticipantDTO, Participant> {

    @Mapping(source = "participant.registerDatetime", target = "registerDatetime")
    @Mapping(source = "participant.studyId.code", target = "studyID")
    @Mapping(source = "participant.dateOfBirth", target = "dateOfBirth")
    @Mapping(source = "participant.status", target = "status")
    ParticipantDTO toDto(Participant participant);

    @Mapping(target = "registerDatetime", ignore=true)
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
