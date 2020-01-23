package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;
import uk.ac.herc.bcra.service.dto.QuestionnaireDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.Questionnaire}.
 */
public interface QuestionnaireService {

    /**
     * Save a questionnaire.
     *
     * @param questionnaireDTO the entity to save.
     * @return the persisted entity.
     */
    QuestionnaireDTO save(QuestionnaireDTO questionnaireDTO);

    /**
     * Get all the questionnaires.
     *
     * @return the list of entities.
     */
    List<QuestionnaireDTO> findAll();

    /**
     * Get the "id" questionnaire.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuestionnaireDTO> findOne(Long id);
    Optional<QuestionnaireDTO> findOne(String login, QuestionnaireType questionnaireType);

    /**
     * Delete the "id" questionnaire.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
