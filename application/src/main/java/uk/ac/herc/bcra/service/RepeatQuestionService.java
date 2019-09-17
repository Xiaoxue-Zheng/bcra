package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.RepeatQuestionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.RepeatQuestion}.
 */
public interface RepeatQuestionService {

    /**
     * Save a repeatQuestion.
     *
     * @param repeatQuestionDTO the entity to save.
     * @return the persisted entity.
     */
    RepeatQuestionDTO save(RepeatQuestionDTO repeatQuestionDTO);

    /**
     * Get all the repeatQuestions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RepeatQuestionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" repeatQuestion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RepeatQuestionDTO> findOne(Long id);

    /**
     * Delete the "id" repeatQuestion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
