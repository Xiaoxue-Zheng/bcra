package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.AnswerGroupDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.AnswerGroup}.
 */
public interface AnswerGroupService {

    /**
     * Save a answerGroup.
     *
     * @param answerGroupDTO the entity to save.
     * @return the persisted entity.
     */
    AnswerGroupDTO save(AnswerGroupDTO answerGroupDTO);

    /**
     * Get all the answerGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnswerGroupDTO> findAll(Pageable pageable);


    /**
     * Get the "id" answerGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnswerGroupDTO> findOne(Long id);

    /**
     * Delete the "id" answerGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
