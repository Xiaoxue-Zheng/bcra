package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.QuestionGroupDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<QuestionGroupDTO> findAll(Pageable pageable);


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
