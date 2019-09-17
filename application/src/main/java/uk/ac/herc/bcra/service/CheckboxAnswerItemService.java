package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.CheckboxAnswerItemDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.CheckboxAnswerItem}.
 */
public interface CheckboxAnswerItemService {

    /**
     * Save a checkboxAnswerItem.
     *
     * @param checkboxAnswerItemDTO the entity to save.
     * @return the persisted entity.
     */
    CheckboxAnswerItemDTO save(CheckboxAnswerItemDTO checkboxAnswerItemDTO);

    /**
     * Get all the checkboxAnswerItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CheckboxAnswerItemDTO> findAll(Pageable pageable);


    /**
     * Get the "id" checkboxAnswerItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CheckboxAnswerItemDTO> findOne(Long id);

    /**
     * Delete the "id" checkboxAnswerItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
