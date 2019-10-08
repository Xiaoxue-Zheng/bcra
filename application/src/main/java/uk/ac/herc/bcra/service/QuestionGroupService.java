package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.QuestionGroupDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.QuestionGroup}.
 */
public interface QuestionGroupService {

    /**
     * Save a questionGroup.
     *
     * @param questionGroupDTO the entity to save.
     * @return the persisted entity.
     */
    QuestionGroupDTO save(QuestionGroupDTO questionGroupDTO);

    /**
     * Get all the questionGroups.
     *
     * @return the list of entities.
     */
    List<QuestionGroupDTO> findAll();


    /**
     * Get the "id" questionGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuestionGroupDTO> findOne(Long id);

    /**
     * Delete the "id" questionGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
