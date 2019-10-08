package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.AnswerItemDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.AnswerItem}.
 */
public interface AnswerItemService {

    /**
     * Save a answerItem.
     *
     * @param answerItemDTO the entity to save.
     * @return the persisted entity.
     */
    AnswerItemDTO save(AnswerItemDTO answerItemDTO);

    /**
     * Get all the answerItems.
     *
     * @return the list of entities.
     */
    List<AnswerItemDTO> findAll();


    /**
     * Get the "id" answerItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnswerItemDTO> findOne(Long id);

    /**
     * Delete the "id" answerItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
