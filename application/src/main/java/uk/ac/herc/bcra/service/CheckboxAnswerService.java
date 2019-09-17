package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.CheckboxAnswerDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.CheckboxAnswer}.
 */
public interface CheckboxAnswerService {

    /**
     * Save a checkboxAnswer.
     *
     * @param checkboxAnswerDTO the entity to save.
     * @return the persisted entity.
     */
    CheckboxAnswerDTO save(CheckboxAnswerDTO checkboxAnswerDTO);

    /**
     * Get all the checkboxAnswers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CheckboxAnswerDTO> findAll(Pageable pageable);


    /**
     * Get the "id" checkboxAnswer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CheckboxAnswerDTO> findOne(Long id);

    /**
     * Delete the "id" checkboxAnswer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}