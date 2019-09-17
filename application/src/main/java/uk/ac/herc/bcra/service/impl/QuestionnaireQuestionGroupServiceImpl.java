package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.QuestionnaireQuestionGroupService;
import uk.ac.herc.bcra.domain.QuestionnaireQuestionGroup;
import uk.ac.herc.bcra.repository.QuestionnaireQuestionGroupRepository;
import uk.ac.herc.bcra.service.dto.QuestionnaireQuestionGroupDTO;
import uk.ac.herc.bcra.service.mapper.QuestionnaireQuestionGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link QuestionnaireQuestionGroup}.
 */
@Service
@Transactional
public class QuestionnaireQuestionGroupServiceImpl implements QuestionnaireQuestionGroupService {

    private final Logger log = LoggerFactory.getLogger(QuestionnaireQuestionGroupServiceImpl.class);

    private final QuestionnaireQuestionGroupRepository questionnaireQuestionGroupRepository;

    private final QuestionnaireQuestionGroupMapper questionnaireQuestionGroupMapper;

    public QuestionnaireQuestionGroupServiceImpl(QuestionnaireQuestionGroupRepository questionnaireQuestionGroupRepository, QuestionnaireQuestionGroupMapper questionnaireQuestionGroupMapper) {
        this.questionnaireQuestionGroupRepository = questionnaireQuestionGroupRepository;
        this.questionnaireQuestionGroupMapper = questionnaireQuestionGroupMapper;
    }

    /**
     * Save a questionnaireQuestionGroup.
     *
     * @param questionnaireQuestionGroupDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public QuestionnaireQuestionGroupDTO save(QuestionnaireQuestionGroupDTO questionnaireQuestionGroupDTO) {
        log.debug("Request to save QuestionnaireQuestionGroup : {}", questionnaireQuestionGroupDTO);
        QuestionnaireQuestionGroup questionnaireQuestionGroup = questionnaireQuestionGroupMapper.toEntity(questionnaireQuestionGroupDTO);
        questionnaireQuestionGroup = questionnaireQuestionGroupRepository.save(questionnaireQuestionGroup);
        return questionnaireQuestionGroupMapper.toDto(questionnaireQuestionGroup);
    }

    /**
     * Get all the questionnaireQuestionGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<QuestionnaireQuestionGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all QuestionnaireQuestionGroups");
        return questionnaireQuestionGroupRepository.findAll(pageable)
            .map(questionnaireQuestionGroupMapper::toDto);
    }


    /**
     * Get one questionnaireQuestionGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionnaireQuestionGroupDTO> findOne(Long id) {
        log.debug("Request to get QuestionnaireQuestionGroup : {}", id);
        return questionnaireQuestionGroupRepository.findById(id)
            .map(questionnaireQuestionGroupMapper::toDto);
    }

    /**
     * Delete the questionnaireQuestionGroup by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuestionnaireQuestionGroup : {}", id);
        questionnaireQuestionGroupRepository.deleteById(id);
    }
}
