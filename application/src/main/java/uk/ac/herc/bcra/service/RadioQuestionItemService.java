package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.RadioQuestionItemDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.RadioQuestionItem}.
 */
public interface RadioQuestionItemService {

    /**
     * Save a radioQuestionItem.
     *
     * @param radioQuestionItemDTO the entity to save.
     * @return the persisted entity.
     */
    RadioQuestionItemDTO save(RadioQuestionItemDTO radioQuestionItemDTO);

    /**
     * Get all the radioQuestionItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RadioQuestionItemDTO> findAll(Pageable pageable);


    /**
     * Get the "id" radioQuestionItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RadioQuestionItemDTO> findOne(Long id);

    /**
     * Delete the "id" radioQuestionItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
