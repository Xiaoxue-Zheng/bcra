package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.QuestionSectionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.QuestionSection}.
 */
public interface QuestionSectionService {

    /**
     * Save a questionSection.
     *
     * @param questionSectionDTO the entity to save.
     * @return the persisted entity.
     */
    QuestionSectionDTO save(QuestionSectionDTO questionSectionDTO);

    /**
     * Get all the questionSections.
     *
     * @return the list of entities.
     */
    List<QuestionSectionDTO> findAll();


    /**
     * Get the "id" questionSection.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuestionSectionDTO> findOne(Long id);

    /**
     * Delete the "id" questionSection.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
