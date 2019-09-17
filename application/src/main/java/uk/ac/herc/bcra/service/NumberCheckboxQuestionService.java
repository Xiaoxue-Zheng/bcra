package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.NumberCheckboxQuestionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.NumberCheckboxQuestion}.
 */
public interface NumberCheckboxQuestionService {

    /**
     * Save a numberCheckboxQuestion.
     *
     * @param numberCheckboxQuestionDTO the entity to save.
     * @return the persisted entity.
     */
    NumberCheckboxQuestionDTO save(NumberCheckboxQuestionDTO numberCheckboxQuestionDTO);

    /**
     * Get all the numberCheckboxQuestions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NumberCheckboxQuestionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" numberCheckboxQuestion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NumberCheckboxQuestionDTO> findOne(Long id);

    /**
     * Delete the "id" numberCheckboxQuestion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
