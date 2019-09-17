package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.RepeatAnswerDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.RepeatAnswer}.
 */
public interface RepeatAnswerService {

    /**
     * Save a repeatAnswer.
     *
     * @param repeatAnswerDTO the entity to save.
     * @return the persisted entity.
     */
    RepeatAnswerDTO save(RepeatAnswerDTO repeatAnswerDTO);

    /**
     * Get all the repeatAnswers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RepeatAnswerDTO> findAll(Pageable pageable);


    /**
     * Get the "id" repeatAnswer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RepeatAnswerDTO> findOne(Long id);

    /**
     * Delete the "id" repeatAnswer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
