package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.questionnaire.AnswerCheck;
import uk.ac.herc.bcra.questionnaire.AnswerChecker;
import uk.ac.herc.bcra.repository.*;
import uk.ac.herc.bcra.service.AnswerResponseService;
import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;
import uk.ac.herc.bcra.domain.enumeration.ResponseState;
import uk.ac.herc.bcra.service.dto.AnswerResponseDTO;
import uk.ac.herc.bcra.service.dto.AnswerSectionDTO;
import uk.ac.herc.bcra.service.mapper.AnswerResponseMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.ac.herc.bcra.service.mapper.AnswerSectionMapper;
import uk.ac.herc.bcra.web.rest.errors.InvalidAnswerException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AnswerResponse}.
 */
@Service
@Transactional
public class AnswerResponseServiceImpl implements AnswerResponseService {

    private final Logger log = LoggerFactory.getLogger(AnswerResponseServiceImpl.class);

    private final AnswerResponseRepository answerResponseRepository;

    private final AnswerResponseMapper answerResponseMapper;

    private final AnswerSectionMapper answerSectionMapper;

    private final ParticipantRepository participantRepository;

    private final StudyIdRepository studyIdRepository;

    private final QuestionRepository questionRepository;

    private final QuestionSectionRepository questionSectionRepository;

    private final QuestionItemRepository questionItemRepository;


    public AnswerResponseServiceImpl(
        AnswerResponseRepository answerResponseRepository,
        AnswerResponseMapper answerResponseMapper,
        AnswerSectionMapper answerSectionMapper,
        ParticipantRepository participantRepository,
        StudyIdRepository studyIdRepository,
        QuestionRepository questionRepository,
        QuestionItemRepository questionItemRepository,
        QuestionSectionRepository questionSectionRepository
    ) {
        this.answerResponseRepository = answerResponseRepository;
        this.answerResponseMapper = answerResponseMapper;
        this.participantRepository = participantRepository;
        this.studyIdRepository = studyIdRepository;
        this.answerSectionMapper = answerSectionMapper;
        this.questionSectionRepository = questionSectionRepository;
        this.questionItemRepository = questionItemRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public boolean save(String login, AnswerSectionDTO answerSectionDTO) {
        log.debug("Request to save AnswerResponse");
        Optional<Participant> participantOptional = participantRepository.findOneByUserLogin(login);
        if (participantOptional.isPresent()) {
            AnswerResponse answerResponse = participantOptional.get().getStudyId().getRiskAssessmentResponse();
            AnswerSection answerSection = answerSectionMapper.toEntity(answerSectionDTO);
            answerResponse.getAnswerSections().removeIf(it-> Objects.equals(it.getId(), answerSection.getId()));
            answerResponse.getAnswerSections().add(answerSection);
            populateAnswerSection(answerSection);
            AnswerCheck answerCheck = AnswerChecker.checkAnswerSection(answerSection, answerResponse);
            if(!answerCheck.isValid()){
                throw new InvalidAnswerException(answerCheck.getReason());
            }
            answerResponse.setState(ResponseState.IN_PROGRESS);
            answerResponseRepository.save(answerResponse);
            return true;
        }
        return false;
    }

    /**
     * Get all the answerResponses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AnswerResponseDTO> findAll() {
        log.debug("Request to get all AnswerResponses");
        return answerResponseRepository.findAll().stream()
            .map(answerResponseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one answerResponse by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AnswerResponseDTO> findOne(Long id) {
        log.debug("Request to get AnswerResponse : {}", id);
        return answerResponseRepository.findById(id)
            .map(answerResponseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnswerResponseDTO> findOne(String login, QuestionnaireType questionnaireType) {
        log.debug("Request to get AnswerResponse");
        Optional<Participant> participantOptional = participantRepository.findOneByUserLogin(login);
        if (participantOptional.isPresent()) {
            if (questionnaireType == QuestionnaireType.CONSENT_FORM) {
                return
                    Optional
                    .of(participantOptional.get().getStudyId().getConsentResponse())
                    .map(answerResponseMapper::toDto);
            } else if (questionnaireType == QuestionnaireType.RISK_ASSESSMENT) {
                return
                    Optional
                    .of(participantOptional.get().getStudyId().getRiskAssessmentResponse())
                    .map(answerResponseMapper::toDto);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<AnswerResponseDTO> getConsentAnswerResponses() {
        log.debug("Request to get Consent Questionnaire AnswerResponse");
        return answerResponseRepository
            .findOneByQuestionnaireType(QuestionnaireType.CONSENT_FORM)
            .map(answerResponseMapper::toDto);
    }

    /**
     * Delete the answerResponse by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnswerResponse : {}", id);
        answerResponseRepository.deleteById(id);
    }

    @Override
    public boolean isQuestionnaireComplete(String login, QuestionnaireType type) {
        Optional<StudyId> studyIdOptional = studyIdRepository.findOneByCode(login);
        if (studyIdOptional.isPresent()) {
            StudyId studyId = studyIdOptional.get();
            AnswerResponse answerResponse = null;

            if (type == QuestionnaireType.CONSENT_FORM) {
                answerResponse = studyId.getConsentResponse();
            } else if (type == QuestionnaireType.RISK_ASSESSMENT) {
                answerResponse = studyId.getRiskAssessmentResponse();
            }

            if (answerResponse.getState() == ResponseState.IN_PROGRESS ||
                answerResponse.getState() == ResponseState.NOT_STARTED) {
                return false;
            } else {
                return true;
            }
        }

        return false;
    }

    public void populateAnswerResponse(AnswerResponse answerResponse){
        answerResponse.getAnswerSections()
            .forEach(this::populateAnswerSection);
    }

    public void populateAnswerSection(AnswerSection answerSection){
        answerSection.setQuestionSection(questionSectionRepository.getOne(answerSection.getQuestionSection().getId()));
        answerSection.getAnswerGroups().forEach(g->g.getAnswers()
            .forEach(a->{
                a.setQuestion(questionRepository.getOne(a.getQuestion().getId()));
                a.getAnswerItems()
                    .forEach(i->i.setQuestionItem(questionItemRepository.getOne(i.getQuestionItem().getId())));
            })
        );
    }

    @Override
    public boolean referralAnswerResponse(String login, AnswerResponseDTO answerResponseDTO) {
        Optional<Participant> participantOptional = participantRepository.findOneByUserLogin(login);
        if (participantOptional.isPresent()) {
            Participant participant = participantOptional.get();
            Long answerResponseId = participant.getStudyId().getRiskAssessmentResponse().getId();
            save(answerResponseDTO, answerResponseId, ResponseState.REFERRED);
            participant.setStatus(ResponseState.REFERRED.name());
            participantRepository.save(participant);
            return true;
        }
        return false;
    }

    @Override
    public boolean submitAnswerResponse(String login, AnswerResponseDTO answerResponseDTO) {
        Optional<Participant> participantOptional = participantRepository.findOneByUserLogin(login);
        if (participantOptional.isPresent()) {
            Participant participant = participantOptional.get();
            Long answerResponseId = participant.getStudyId().getRiskAssessmentResponse().getId();
            save(answerResponseDTO, answerResponseId, ResponseState.VALIDATED);
            participant.setStatus(ResponseState.SUBMITTED.name());
            participantRepository.save(participant);
            return true;
        }
        return false;
    }


    private void save(AnswerResponseDTO answerResponseDTO, Long answerResponseId, ResponseState responseState) {
        AnswerResponse answerResponse = answerResponseMapper.toEntity(answerResponseDTO);
        answerResponse.setId(answerResponseId);
        answerResponse.setState(responseState);
        answerResponse.setStatus("");
        answerResponseRepository.save(answerResponse);
    }

    @Override
    public boolean isParticipantReferred(String login) {
        Optional<Participant> participantOptional = participantRepository.findOneByUserLogin(login);
        if (participantOptional.isPresent()) {
            Participant participant = participantOptional.get();
            return participant.getStudyId().getRiskAssessmentResponse().getState() == ResponseState.REFERRED;
        }

        return false;
    }
}
