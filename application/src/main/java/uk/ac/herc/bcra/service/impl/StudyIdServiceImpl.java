package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.StudyIdService;
import uk.ac.herc.bcra.service.mapper.AnswerResponseMapper;
import uk.ac.herc.bcra.service.dto.AnswerResponseDTO;
import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.domain.StudyId;
import uk.ac.herc.bcra.repository.ParticipantRepository;
import uk.ac.herc.bcra.repository.StudyIdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class StudyIdServiceImpl implements StudyIdService {

    private final Logger log = LoggerFactory.getLogger(AnswerGroupServiceImpl.class);

    private final StudyIdRepository studyIdRepository;
    private final AnswerResponseMapper answerResponseMapper;
    private final ParticipantRepository participantRepository;

    public StudyIdServiceImpl(StudyIdRepository studyIdRepository, AnswerResponseMapper answerResponseMapper, ParticipantRepository participantRepository) {
        this.studyIdRepository = studyIdRepository;
        this.answerResponseMapper = answerResponseMapper;
        this.participantRepository = participantRepository;
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
}
