package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.ProcedureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Procedure} and its DTO {@link ProcedureDTO}.
 */
@Mapper(componentModel = "spring", uses = {AnswerResponseMapper.class})
public interface ProcedureMapper extends EntityMapper<ProcedureDTO, Procedure> {

    @Mapping(source = "consentResponse.id", target = "consentResponseId")
    @Mapping(source = "riskAssesmentResponse.id", target = "riskAssesmentResponseId")
    ProcedureDTO toDto(Procedure procedure);

    @Mapping(source = "consentResponseId", target = "consentResponse")
    @Mapping(source = "riskAssesmentResponseId", target = "riskAssesmentResponse")
    @Mapping(target = "participant", ignore = true)
    Procedure toEntity(ProcedureDTO procedureDTO);

    default Procedure fromId(Long id) {
        if (id == null) {
            return null;
        }
        Procedure procedure = new Procedure();
        procedure.setId(id);
        return procedure;
    }
}
