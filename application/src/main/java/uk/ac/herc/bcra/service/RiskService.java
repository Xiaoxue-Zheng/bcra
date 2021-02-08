package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.domain.Risk;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Risk}.
 */
public interface RiskService {

    /**
     * Save a risk.
     *
     * @param risk the entity to save.
     * @return the persisted entity.
     */
    Risk save(Risk risk);

    /**
     * Get all the risks.
     *
     * @return the list of entities.
     */
    List<Risk> findAll();

    /**
     * Get the "id" risk.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Risk> findOne(Long id);

    /**
     * Delete the "id" risk.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
