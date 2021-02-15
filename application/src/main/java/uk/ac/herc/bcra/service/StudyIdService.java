package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.domain.StudyId;
import uk.ac.herc.bcra.service.dto.AnswerResponseDTO;

import java.util.Optional;

public interface StudyIdService {

    boolean isStudyCodeAvailable(String code);

    Optional<StudyId> getStudyIdByCode(String code);

    StudyId save(StudyId studyId);

    AnswerResponseDTO getConsentResponseFromStudyCode(String studyCode);

    AnswerResponseDTO getRiskAssessmentResponseFromStudyCode(String studyCode);

    String getStudyCodeByParticipantLogin(String login);
}
