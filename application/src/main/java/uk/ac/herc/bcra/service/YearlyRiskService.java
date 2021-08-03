package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.domain.YearlyRisk;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link YearlyRiskFactor}.
 */
public interface YearlyRiskService {

    /**
     * Save a riskFactor.
     *
     * @param yearlyRisk the entity to save.
     * @return the persisted entity.
     */
    YearlyRisk save(YearlyRisk yearlyRisk);

    /**
     * Get all the yearlyRisks.
     *
     * @return the list of entities.
     */
    List<YearlyRisk> findAll();

    /**
     * Get the "id" yearlyRisk.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<YearlyRisk> findOne(Long id);

    /**
     * Delete the "id" yearlyRisk.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
