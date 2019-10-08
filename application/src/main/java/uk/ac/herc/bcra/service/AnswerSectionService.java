package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.AnswerSectionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.AnswerSection}.
 */
public interface AnswerSectionService {

    /**
     * Save a answerSection.
     *
     * @param answerSectionDTO the entity to save.
     * @return the persisted entity.
     */
    AnswerSectionDTO save(AnswerSectionDTO answerSectionDTO);

    /**
     * Get all the answerSections.
     *
     * @return the list of entities.
     */
    List<AnswerSectionDTO> findAll();


    /**
     * Get the "id" answerSection.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnswerSectionDTO> findOne(Long id);

    /**
     * Delete the "id" answerSection.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
