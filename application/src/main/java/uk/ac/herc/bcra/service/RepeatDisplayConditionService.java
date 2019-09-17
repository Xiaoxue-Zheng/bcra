package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.RepeatDisplayConditionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.RepeatDisplayCondition}.
 */
public interface RepeatDisplayConditionService {

    /**
     * Save a repeatDisplayCondition.
     *
     * @param repeatDisplayConditionDTO the entity to save.
     * @return the persisted entity.
     */
    RepeatDisplayConditionDTO save(RepeatDisplayConditionDTO repeatDisplayConditionDTO);

    /**
     * Get all the repeatDisplayConditions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RepeatDisplayConditionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" repeatDisplayCondition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RepeatDisplayConditionDTO> findOne(Long id);

    /**
     * Delete the "id" repeatDisplayCondition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
