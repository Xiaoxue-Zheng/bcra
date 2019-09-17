package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.QuestionGroupQuestionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.QuestionGroupQuestion}.
 */
public interface QuestionGroupQuestionService {

    /**
     * Save a questionGroupQuestion.
     *
     * @param questionGroupQuestionDTO the entity to save.
     * @return the persisted entity.
     */
    QuestionGroupQuestionDTO save(QuestionGroupQuestionDTO questionGroupQuestionDTO);

    /**
     * Get all the questionGroupQuestions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<QuestionGroupQuestionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" questionGroupQuestion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuestionGroupQuestionDTO> findOne(Long id);

    /**
     * Delete the "id" questionGroupQuestion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
