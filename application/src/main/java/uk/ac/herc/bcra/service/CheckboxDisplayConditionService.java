package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.CheckboxDisplayConditionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.CheckboxDisplayCondition}.
 */
public interface CheckboxDisplayConditionService {

    /**
     * Save a checkboxDisplayCondition.
     *
     * @param checkboxDisplayConditionDTO the entity to save.
     * @return the persisted entity.
     */
    CheckboxDisplayConditionDTO save(CheckboxDisplayConditionDTO checkboxDisplayConditionDTO);

    /**
     * Get all the checkboxDisplayConditions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CheckboxDisplayConditionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" checkboxDisplayCondition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CheckboxDisplayConditionDTO> findOne(Long id);

    /**
     * Delete the "id" checkboxDisplayCondition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
