package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.RadioQuestionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.RadioQuestion}.
 */
public interface RadioQuestionService {

    /**
     * Save a radioQuestion.
     *
     * @param radioQuestionDTO the entity to save.
     * @return the persisted entity.
     */
    RadioQuestionDTO save(RadioQuestionDTO radioQuestionDTO);

    /**
     * Get all the radioQuestions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RadioQuestionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" radioQuestion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RadioQuestionDTO> findOne(Long id);

    /**
     * Delete the "id" radioQuestion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
