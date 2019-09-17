package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.RadioAnswerDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.RadioAnswer}.
 */
public interface RadioAnswerService {

    /**
     * Save a radioAnswer.
     *
     * @param radioAnswerDTO the entity to save.
     * @return the persisted entity.
     */
    RadioAnswerDTO save(RadioAnswerDTO radioAnswerDTO);

    /**
     * Get all the radioAnswers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RadioAnswerDTO> findAll(Pageable pageable);
    /**
     * Get all the RadioAnswerDTO where RadioAnswerItem is {@code null}.
     *
     * @return the list of entities.
     */
    List<RadioAnswerDTO> findAllWhereRadioAnswerItemIsNull();


    /**
     * Get the "id" radioAnswer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RadioAnswerDTO> findOne(Long id);

    /**
     * Delete the "id" radioAnswer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
