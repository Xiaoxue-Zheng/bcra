package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.domain.RiskFactor;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link RiskFactor}.
 */
public interface RiskFactorService {

    /**
     * Save a riskFactor.
     *
     * @param riskFactor the entity to save.
     * @return the persisted entity.
     */
    RiskFactor save(RiskFactor riskFactor);

    /**
     * Get all the riskFactors.
     *
     * @return the list of entities.
     */
    List<RiskFactor> findAll();

    /**
     * Get the "id" riskFactor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RiskFactor> findOne(Long id);

    /**
     * Delete the "id" riskFactor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
