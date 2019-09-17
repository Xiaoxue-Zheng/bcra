package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.DisplayConditionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DisplayConditionDTO> findAll(Pageable pageable);


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
