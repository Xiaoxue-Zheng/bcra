package uk.ac.herc.bcra.web.rest;

import org.springframework.security.access.annotation.Secured;

import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;
import uk.ac.herc.bcra.security.RoleManager;
import uk.ac.herc.bcra.service.AnswerResponseService;
import uk.ac.herc.bcra.service.StudyIdService;
import uk.ac.herc.bcra.service.dto.AnswerResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.herc.bcra.service.dto.AnswerSectionDTO;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.security.Principal;

/**
 * REST controller for managing {@link uk.ac.herc.bcra.domain.AnswerResponse}.
 */
@RestController
@RequestMapping("/api")
public class AnswerResponseResource {

    private final Logger log = LoggerFactory.getLogger(AnswerResponseResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnswerResponseService answerResponseService;

    private final StudyIdService studyIdService;

    public AnswerResponseResource(
        AnswerResponseService answerResponseService,
        StudyIdService studyIdService
    ) {
        this.answerResponseService = answerResponseService;
        this.studyIdService = studyIdService;
    }

    @GetMapping("/answer-responses/consent/{studyCode}")
    public AnswerResponseDTO getConsentAnswerResponseFromStudyCode(@PathVariable String studyCode) throws Exception {
        log.debug("REST request to get Consent AnswerResponse");
        throwExceptionIfAttemptToModifyAlreadyCompletedAnswerResponse(studyCode, QuestionnaireType.CONSENT_FORM);

        AnswerResponseDTO answerResponseDTO = studyIdService.getConsentResponseFromStudyCode(studyCode);
        return answerResponseDTO;
    }

    @GetMapping("/answer-responses/risk-assessment/")
    @Secured(RoleManager.PARTICIPANT)
    public AnswerResponseDTO getRiskAssessmentResponse(Principal principal) throws Exception {
        log.debug("REST request to get Risk Assessment AnswerResponse");
        throwExceptionIfAttemptToModifyAlreadyCompletedAnswerResponse(principal.getName(), QuestionnaireType.RISK_ASSESSMENT);

        AnswerResponseDTO answerResponseDTO = studyIdService.getRiskAssessmentResponseFromStudyCode(principal.getName());
        return answerResponseDTO;
    }

    @PutMapping("/answer-responses/risk-assessment/section/save")
    @Secured(RoleManager.PARTICIPANT)
    public ResponseEntity<String> saveRiskAssessmentAnswerSection(
        Principal principal,
        @Valid @RequestBody AnswerSectionDTO answerSectionDTO
    ) throws URISyntaxException, Exception {
        log.debug("REST request to save Risk Assessment AnswerSectionDTO: {}", answerSectionDTO);
        throwExceptionIfAttemptToModifyAlreadyCompletedAnswerResponse(principal.getName(), QuestionnaireType.RISK_ASSESSMENT);

        if(answerResponseService.save(principal.getName(), answerSectionDTO)) {
            return ResponseEntity.ok().body("SAVED");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("FAILED");
        }
    }

    @PutMapping("/answer-responses/risk-assessment/referral")
    @Secured(RoleManager.PARTICIPANT)
    public ResponseEntity<String> referralRiskAssessment(
        Principal principal,
        @Valid @RequestBody AnswerResponseDTO answerResponseDTO
    ) throws URISyntaxException {
        log.debug("REST request to save Risk Assessment AnswerResponse: {}", answerResponseDTO);
        if(answerResponseService.referralAnswerResponse(principal.getName(), answerResponseDTO)) {
            return ResponseEntity.ok().body("REFERRED");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("FAILED");
        }
    }

    @PutMapping("/answer-responses/risk-assessment/submit")
    @Secured(RoleManager.PARTICIPANT)
    public ResponseEntity<String> submitRiskAssessment(
            Principal principal,
            @Valid @RequestBody AnswerResponseDTO answerResponseDTO
    ) throws URISyntaxException {
        log.debug("REST request to submit Risk Assessment AnswerResponse: {}", answerResponseDTO);
        if(answerResponseService.submitAnswerResponse(principal.getName(), answerResponseDTO)) {
            return ResponseEntity.ok().body("SUBMITTED");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("FAILED");
        }
    }

    @GetMapping("/answer-responses/consent/complete")
    public boolean hasCompletedConsentQuestionnaire(Principal principal) {
        log.debug("REST request to determine if consent questionnaire is complete");
        return answerResponseService.isQuestionnaireComplete(principal.getName(), QuestionnaireType.CONSENT_FORM);
    }

    @GetMapping("/answer-responses/risk-assessment/complete")
    public boolean hasCompletedRiskAssessmentQuestionnaire(Principal principal) {
        log.debug("REST request to determine if consent questionnaire is complete");
        return answerResponseService.isQuestionnaireComplete(principal.getName(), QuestionnaireType.RISK_ASSESSMENT);
    }

    private void throwExceptionIfAttemptToModifyAlreadyCompletedAnswerResponse(String login, QuestionnaireType type) throws Exception {
        if (answerResponseService.isQuestionnaireComplete(login, type)) {
            throw new RuntimeException("Attempt to access already completed form of type: " + type.toString());
        }
    }
}
