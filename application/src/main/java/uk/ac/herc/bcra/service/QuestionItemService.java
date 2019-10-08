package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.QuestionItemDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.QuestionItem}.
 */
public interface QuestionItemService {

    /**
     * Save a questionItem.
     *
     * @param questionItemDTO the entity to save.
     * @return the persisted entity.
     */
    QuestionItemDTO save(QuestionItemDTO questionItemDTO);

    /**
     * Get all the questionItems.
     *
     * @return the list of entities.
     */
    List<QuestionItemDTO> findAll();


    /**
     * Get the "id" questionItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuestionItemDTO> findOne(Long id);

    /**
     * Delete the "id" questionItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
