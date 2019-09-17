package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.CheckboxQuestionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.CheckboxQuestion}.
 */
public interface CheckboxQuestionService {

    /**
     * Save a checkboxQuestion.
     *
     * @param checkboxQuestionDTO the entity to save.
     * @return the persisted entity.
     */
    CheckboxQuestionDTO save(CheckboxQuestionDTO checkboxQuestionDTO);

    /**
     * Get all the checkboxQuestions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CheckboxQuestionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" checkboxQuestion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CheckboxQuestionDTO> findOne(Long id);

    /**
     * Delete the "id" checkboxQuestion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
