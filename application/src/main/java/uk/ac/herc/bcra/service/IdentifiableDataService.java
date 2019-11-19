package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.IdentifiableDataDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.IdentifiableData}.
 */
public interface IdentifiableDataService {

    /**
     * Save a identifiableData.
     *
     * @param identifiableDataDTO the entity to save.
     * @return the persisted entity.
     */
    IdentifiableDataDTO save(IdentifiableDataDTO identifiableDataDTO);

    /**
     * Get all the identifiableData.
     *
     * @return the list of entities.
     */
    List<IdentifiableDataDTO> findAll();


    /**
     * Get the "id" identifiableData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IdentifiableDataDTO> findOne(Long id);

    /**
     * Delete the "id" identifiableData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
