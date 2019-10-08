package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.AnswerResponseDTO;

import java.util.List;
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
     * @return the list of entities.
     */
    List<AnswerResponseDTO> findAll();


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
