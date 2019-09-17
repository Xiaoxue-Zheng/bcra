package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.RadioAnswerItemDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.RadioAnswerItem}.
 */
public interface RadioAnswerItemService {

    /**
     * Save a radioAnswerItem.
     *
     * @param radioAnswerItemDTO the entity to save.
     * @return the persisted entity.
     */
    RadioAnswerItemDTO save(RadioAnswerItemDTO radioAnswerItemDTO);

    /**
     * Get all the radioAnswerItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RadioAnswerItemDTO> findAll(Pageable pageable);


    /**
     * Get the "id" radioAnswerItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RadioAnswerItemDTO> findOne(Long id);

    /**
     * Delete the "id" radioAnswerItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
