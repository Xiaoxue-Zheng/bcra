package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.QuestionnaireService;
import uk.ac.herc.bcra.domain.Questionnaire;
import uk.ac.herc.bcra.repository.QuestionnaireRepository;
import uk.ac.herc.bcra.service.dto.QuestionnaireDTO;
import uk.ac.herc.bcra.service.mapper.QuestionnaireMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Questionnaire}.
 */
@Service
@Transactional
public class QuestionnaireServiceImpl implements QuestionnaireService {

    private final Logger log = LoggerFactory.getLogger(QuestionnaireServiceImpl.class);

    private final QuestionnaireRepository questionnaireRepository;

    private final QuestionnaireMapper questionnaireMapper;

    public QuestionnaireServiceImpl(QuestionnaireRepository questionnaireRepository, QuestionnaireMapper questionnaireMapper) {
        this.questionnaireRepository = questionnaireRepository;
        this.questionnaireMapper = questionnaireMapper;
    }

    /**
     * Save a questionnaire.
     *
     * @param questionnaireDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public QuestionnaireDTO save(QuestionnaireDTO questionnaireDTO) {
        log.debug("Request to save Questionnaire : {}", questionnaireDTO);
        Questionnaire questionnaire = questionnaireMapper.toEntity(questionnaireDTO);
        questionnaire = questionnaireRepository.save(questionnaire);
        return questionnaireMapper.toDto(questionnaire);
    }

    /**
     * Get all the questionnaires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<QuestionnaireDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Questionnaires");
        return questionnaireRepository.findAll(pageable)
            .map(questionnaireMapper::toDto);
    }


    /**
     * Get one questionnaire by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionnaireDTO> findOne(Long id) {
        log.debug("Request to get Questionnaire : {}", id);
        return questionnaireRepository.findById(id)
            .map(questionnaireMapper::toDto);
    }

    /**
     * Delete the questionnaire by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Questionnaire : {}", id);
        questionnaireRepository.deleteById(id);
    }
}
