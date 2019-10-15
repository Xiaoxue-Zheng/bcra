package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.ReferralConditionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.ReferralCondition}.
 */
public interface ReferralConditionService {

    /**
     * Save a referralCondition.
     *
     * @param referralConditionDTO the entity to save.
     * @return the persisted entity.
     */
    ReferralConditionDTO save(ReferralConditionDTO referralConditionDTO);

    /**
     * Get all the referralConditions.
     *
     * @return the list of entities.
     */
    List<ReferralConditionDTO> findAll();


    /**
     * Get the "id" referralCondition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReferralConditionDTO> findOne(Long id);

    /**
     * Delete the "id" referralCondition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
