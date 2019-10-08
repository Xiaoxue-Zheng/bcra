package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.DisplayConditionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.DisplayCondition}.
 */
public interface DisplayConditionService {

    /**
     * Save a displayCondition.
     *
     * @param displayConditionDTO the entity to save.
     * @return the persisted entity.
     */
    DisplayConditionDTO save(DisplayConditionDTO displayConditionDTO);

    /**
     * Get all the displayConditions.
     *
     * @return the list of entities.
     */
    List<DisplayConditionDTO> findAll();


    /**
     * Get the "id" displayCondition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DisplayConditionDTO> findOne(Long id);

    /**
     * Delete the "id" displayCondition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
