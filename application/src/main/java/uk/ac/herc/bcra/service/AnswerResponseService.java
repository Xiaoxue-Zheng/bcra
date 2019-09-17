package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.AnswerResponseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.AnswerResponse}.
 */
public interface AnswerResponseService {

    /**
     * Save a answerResponse.
     *
     * @param answerResponseDTO the entity to save.
     * @return the persisted entity.
     */
    AnswerResponseDTO save(AnswerResponseDTO answerResponseDTO);

    /**
     * Get all the answerResponses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnswerResponseDTO> findAll(Pageable pageable);


    /**
     * Get the "id" answerResponse.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnswerResponseDTO> findOne(Long id);

    /**
     * Delete the "id" answerResponse.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
