package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.domain.RiskAssessmentResult;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link RiskAssessmentResult}.
 */
public interface RiskAssessmentResultService {

    /**
     * Save a riskAssessmentResult.
     *
     * @param riskAssessmentResult the entity to save.
     * @return the persisted entity.
     */
    RiskAssessmentResult save(RiskAssessmentResult riskAssessmentResult);

    /**
     * Get all the riskAssessmentResults.
     *
     * @return the list of entities.
     */
    List<RiskAssessmentResult> findAll();

    /**
     * Get the "id" riskAssessmentResult.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RiskAssessmentResult> findOne(Long id);

    /**
     * Delete the "id" riskAssessmentResult.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
