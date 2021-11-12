package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.MailService;
import uk.ac.herc.bcra.service.StudyIdService;
import uk.ac.herc.bcra.service.mapper.AnswerResponseMapper;
import uk.ac.herc.bcra.service.dto.AnswerResponseDTO;
import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.domain.CanRiskReport;
import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.domain.StudyId;
import uk.ac.herc.bcra.domain.User;
import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;
import uk.ac.herc.bcra.questionnaire.AnswerResponseGenerator;
import uk.ac.herc.bcra.repository.CanRiskReportRepository;
import uk.ac.herc.bcra.repository.ParticipantRepository;
import uk.ac.herc.bcra.repository.StudyIdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.herc.bcra.web.rest.errors.DuplicateStudyIdException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudyIdServiceImpl implements StudyIdService {

    private final Logger log = LoggerFactory.getLogger(AnswerGroupServiceImpl.class);

    private final StudyIdRepository studyIdRepository;
    private final AnswerResponseMapper answerResponseMapper;
    private final ParticipantRepository participantRepository;
    private final AnswerResponseGenerator answerResponseGenerator;
    private final CanRiskReportRepository canRiskReportRepository;
    private final MailService mailService;

    public StudyIdServiceImpl(StudyIdRepository studyIdRepository,
        AnswerResponseMapper answerResponseMapper,
        ParticipantRepository participantRepository,
        AnswerResponseGenerator answerResponseGenerator,
        CanRiskReportRepository canRiskReportRepository,
        MailService mailService) {

        this.studyIdRepository = studyIdRepository;
        this.answerResponseMapper = answerResponseMapper;
        this.participantRepository = participantRepository;
        this.answerResponseGenerator = answerResponseGenerator;
        this.canRiskReportRepository = canRiskReportRepository;
        this.mailService = mailService;
    }

    @Override
    public List<StudyId> getStudyIds() {
        log.debug("Request to get all study ids");
        return studyIdRepository.findAll();
    }

    @Override
    @Async
    public void createStudyIdsFromCodes(User requestBy, List<String> studyCodes) {
        log.debug("Creating multiple study ids from study code");
        List<String> failedStudyCodes = new ArrayList<String>();
        for (String studyCode : studyCodes) {
            try {
                createStudyIdFromCode(studyCode);
            } catch(Exception ex) {
                failedStudyCodes.add(studyCode);
            }
        }

        if (failedStudyCodes.size() == 0) {
            mailService.sendStudyCodeCreationSuccessEmail(requestBy, studyCodes);
        } else {
            mailService.sendStudyCodeCreationFailureEmail(requestBy, studyCodes, failedStudyCodes);
        }
    }

    @Override
    public void createStudyIdFromCode(String studyCode) {
        log.debug("Creating study id with code: {}", studyCode);
        studyIdRepository.findOneByCode(studyCode)
            .ifPresent(studyId -> {throw new DuplicateStudyIdException("StudyID already exists "+studyCode);});

        AnswerResponse consentForm = answerResponseGenerator.generateAnswerResponseToQuestionnaire(
            QuestionnaireType.CONSENT_FORM
        );

        AnswerResponse riskAssessment = answerResponseGenerator.generateAnswerResponseToQuestionnaire(
            QuestionnaireType.RISK_ASSESSMENT
        );

        StudyId studyId = new StudyId();
        studyId.setCode(studyCode);
        studyId.setConsentResponse(consentForm);
        studyId.setRiskAssessmentResponse(riskAssessment);
        save(studyId);
    }

    @Override
    public Optional<StudyId> getStudyIdByCode(String code) {
        log.debug("Request to get study id by code: {}", code);
        return studyIdRepository.findOneByCode(code);
    }

    @Override
    public StudyId save(StudyId studyId) {
        return studyIdRepository.save(studyId);
    }

    @Override
    public boolean isStudyCodeAvailable(String code) {
        log.debug("Request check whether study code is available : {}", code);
        Optional<StudyId> studyIdOptional = studyIdRepository.findOneByCode(code);
        if (studyIdOptional.isPresent()) {
            StudyId studyId = studyIdOptional.get();

            return studyId.getParticipant() == null;
        }

        return false;
    }

    @Override
    public AnswerResponseDTO getConsentResponseFromStudyCode(String studyCode) {
        Optional<StudyId> studyIdOptional = getStudyIdByCode(studyCode);
        if (studyIdOptional.isPresent()) {
            StudyId studyId = studyIdOptional.get();
            return answerResponseMapper.toDto(studyId.getConsentResponse());
        }

        return null;
    }

    @Override
    public AnswerResponseDTO getRiskAssessmentResponseFromStudyCode(String studyCode) {
        Optional<StudyId> studyIdOptional = getStudyIdByCode(studyCode);
        if (studyIdOptional.isPresent()) {
            StudyId studyId = studyIdOptional.get();
            return answerResponseMapper.toDto(studyId.getRiskAssessmentResponse());
        }

        return null;
    }

    @Override
    public String getStudyCodeByParticipantLogin(String login) {
        Optional<Participant> participantOptional = participantRepository.findOneByUserLogin(login);
        if (participantOptional.isPresent()) {
            Participant participant = participantOptional.get();
            Optional<StudyId> studyIdOptional = studyIdRepository.findOneByParticipant(participant);
            if (studyIdOptional.isPresent()) {
                return studyIdOptional.get().getCode();
            }
        }

        return null;
    }

    @Override
    public boolean doesStudyIdExistAndNotAssignedToCanRiskReport(String code) {
        Optional<StudyId> studyIdOptional = studyIdRepository.findOneByCode(code);
        if (studyIdOptional.isPresent()) {
            StudyId studyId = studyIdOptional.get();
            Optional<CanRiskReport> reportOptional = canRiskReportRepository.findOneByAssociatedStudyId(studyId);
            return !reportOptional.isPresent();
        }

        return false;
    }
}
