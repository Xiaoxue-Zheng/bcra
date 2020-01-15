package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.ProcedureDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.Procedure}.
 */
public interface ProcedureService {

    /**
     * Save a procedure.
     *
     * @param procedureDTO the entity to save.
     * @return the persisted entity.
     */
    ProcedureDTO save(ProcedureDTO procedureDTO);

    /**
     * Get all the procedures.
     *
     * @return the list of entities.
     */
    List<ProcedureDTO> findAll();
    /**
     * Get all the ProcedureDTO where Participant is {@code null}.
     *
     * @return the list of entities.
     */
    List<ProcedureDTO> findAllWhereParticipantIsNull();


    /**
     * Get the "id" procedure.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProcedureDTO> findOne(Long id);

    /**
     * Delete the "id" procedure.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
