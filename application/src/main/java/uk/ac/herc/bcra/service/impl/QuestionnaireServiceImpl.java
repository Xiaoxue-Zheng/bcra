package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.QuestionnaireService;
import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.domain.Questionnaire;
import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;
import uk.ac.herc.bcra.repository.ParticipantRepository;
import uk.ac.herc.bcra.repository.QuestionnaireRepository;
import uk.ac.herc.bcra.service.dto.QuestionnaireDTO;
import uk.ac.herc.bcra.service.mapper.QuestionnaireMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Questionnaire}.
 */
@Service
@Transactional
public class QuestionnaireServiceImpl implements QuestionnaireService {

    private final Logger log = LoggerFactory.getLogger(QuestionnaireServiceImpl.class);

    private final QuestionnaireRepository questionnaireRepository;

    private final QuestionnaireMapper questionnaireMapper;

    private final ParticipantRepository participantRepository;

    public QuestionnaireServiceImpl(
        QuestionnaireRepository questionnaireRepository,
        QuestionnaireMapper questionnaireMapper,
        ParticipantRepository participantRepository
    ) {
        this.questionnaireRepository = questionnaireRepository;
        this.questionnaireMapper = questionnaireMapper;
        this.participantRepository = participantRepository;
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
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<QuestionnaireDTO> findAll() {
        log.debug("Request to get all Questionnaires");
        return questionnaireRepository.findAll().stream()
            .map(questionnaireMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
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

    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionnaireDTO> getConsentQuestionnaire() {
        log.debug("Request to get Consent Questionnaire");
        return questionnaireRepository
            .findFirstByType(QuestionnaireType.CONSENT_FORM)
            .map(questionnaireMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionnaireDTO> findOne(String login, QuestionnaireType questionnaireType) {
        log.debug("Request to get Questionnaire");
        Optional<Participant> participantOptional = participantRepository.findOneByUserLogin(login);
        if (participantOptional.isPresent()) {
            if (questionnaireType == QuestionnaireType.CONSENT_FORM) {
                return
                    Optional
                    .of(participantOptional.get().getStudyId().getConsentResponse().getQuestionnaire())
                    .map(questionnaireMapper::toDto);
            } else if (questionnaireType == QuestionnaireType.RISK_ASSESSMENT) {
                return
                    Optional
                    .of(participantOptional.get().getStudyId().getRiskAssessmentResponse().getQuestionnaire())
                    .map(questionnaireMapper::toDto);
            }
        }
        return Optional.empty();
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
