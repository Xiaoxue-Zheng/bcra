package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.CheckboxQuestionItemDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.CheckboxQuestionItem}.
 */
public interface CheckboxQuestionItemService {

    /**
     * Save a checkboxQuestionItem.
     *
     * @param checkboxQuestionItemDTO the entity to save.
     * @return the persisted entity.
     */
    CheckboxQuestionItemDTO save(CheckboxQuestionItemDTO checkboxQuestionItemDTO);

    /**
     * Get all the checkboxQuestionItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CheckboxQuestionItemDTO> findAll(Pageable pageable);


    /**
     * Get the "id" checkboxQuestionItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CheckboxQuestionItemDTO> findOne(Long id);

    /**
     * Delete the "id" checkboxQuestionItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
