package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.QuestionnaireQuestionGroupDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.QuestionnaireQuestionGroup}.
 */
public interface QuestionnaireQuestionGroupService {

    /**
     * Save a questionnaireQuestionGroup.
     *
     * @param questionnaireQuestionGroupDTO the entity to save.
     * @return the persisted entity.
     */
    QuestionnaireQuestionGroupDTO save(QuestionnaireQuestionGroupDTO questionnaireQuestionGroupDTO);

    /**
     * Get all the questionnaireQuestionGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<QuestionnaireQuestionGroupDTO> findAll(Pageable pageable);


    /**
     * Get the "id" questionnaireQuestionGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuestionnaireQuestionGroupDTO> findOne(Long id);

    /**
     * Delete the "id" questionnaireQuestionGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
