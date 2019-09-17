package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.NumberCheckboxAnswerDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.NumberCheckboxAnswer}.
 */
public interface NumberCheckboxAnswerService {

    /**
     * Save a numberCheckboxAnswer.
     *
     * @param numberCheckboxAnswerDTO the entity to save.
     * @return the persisted entity.
     */
    NumberCheckboxAnswerDTO save(NumberCheckboxAnswerDTO numberCheckboxAnswerDTO);

    /**
     * Get all the numberCheckboxAnswers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NumberCheckboxAnswerDTO> findAll(Pageable pageable);


    /**
     * Get the "id" numberCheckboxAnswer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NumberCheckboxAnswerDTO> findOne(Long id);

    /**
     * Delete the "id" numberCheckboxAnswer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
