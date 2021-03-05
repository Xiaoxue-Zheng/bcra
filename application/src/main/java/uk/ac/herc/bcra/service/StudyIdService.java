package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.domain.StudyId;
import uk.ac.herc.bcra.service.dto.AnswerResponseDTO;

import java.util.Optional;
import java.util.List;

public interface StudyIdService {

    List<StudyId> getStudyIds();

    void createStudyIdFromCode(String studyCode);
    void createStudyIdsFromCodes(List<String> studyCodes);

    boolean isStudyCodeAvailable(String code);

    Optional<StudyId> getStudyIdByCode(String code);

    StudyId save(StudyId studyId);

    AnswerResponseDTO getConsentResponseFromStudyCode(String studyCode);

    AnswerResponseDTO getRiskAssessmentResponseFromStudyCode(String studyCode);

    String getStudyCodeByParticipantLogin(String login);
}
