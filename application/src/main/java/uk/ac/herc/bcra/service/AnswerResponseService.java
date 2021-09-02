package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;
import uk.ac.herc.bcra.service.dto.AnswerResponseDTO;
import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.service.dto.AnswerSectionDTO;

import java.util.List;
import java.util.Optional;


/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.AnswerResponse}.
 */
public interface AnswerResponseService {


    boolean save(String login, AnswerSectionDTO answerSectionDTO);

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
    Optional<AnswerResponseDTO> getConsentAnswerResponses();
    Optional<AnswerResponseDTO> findOne(String login, QuestionnaireType questionnaireType);

    /**
     * Delete the "id" answerResponse.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    boolean isQuestionnaireComplete(String login, QuestionnaireType type);

    void populateAnswerResponse(AnswerResponse answerResponse);

    boolean referralAnswerResponse(String login, AnswerResponseDTO answerResponseDTO);

    boolean submitAnswerResponse(String login, AnswerResponseDTO answerResponseDTO);
}
