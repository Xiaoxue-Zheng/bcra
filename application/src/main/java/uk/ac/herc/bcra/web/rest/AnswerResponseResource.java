package uk.ac.herc.bcra.web.rest;

import org.springframework.security.access.annotation.Secured;
import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;
import uk.ac.herc.bcra.domain.enumeration.ResponseState;
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
    public AnswerResponseDTO getConsentAnswerResponseFromStudyCode(@PathVariable String studyCode) {
        log.debug("REST request to get Consent AnswerResponse");
        AnswerResponseDTO answerResponseDTO = studyIdService.getConsentResponseFromStudyCode(studyCode);
        return answerResponseDTO;
    }

    @PutMapping("/answer-responses/consent/save")
    public ResponseEntity<String> saveConsent(
            Principal principal,
            @Valid @RequestBody AnswerResponseDTO answerResponseDTO
    ) throws URISyntaxException {
        log.debug("REST request to save Consent AnswerResponse: {}", answerResponseDTO);
        if(answerResponseService.save(
            principal.getName(),
            answerResponseDTO,
            QuestionnaireType.CONSENT_FORM,
            ResponseState.IN_PROGRESS
        )) {
            return ResponseEntity.ok().body("SAVED");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("FAILED");
        }
    }

    @PutMapping("/answer-responses/consent/submit")
    public ResponseEntity<String> submitConsent(
            Principal principal,
            @Valid @RequestBody AnswerResponseDTO answerResponseDTO
    ) throws URISyntaxException {
        log.debug("REST request to submit Consent AnswerResponse: {}", answerResponseDTO);
        if(answerResponseService.save(
            principal.getName(),
            answerResponseDTO,
            QuestionnaireType.CONSENT_FORM,
            ResponseState.SUBMITTED
        )) {
            return ResponseEntity.ok().body("SUBMITTED");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("FAILED");
        }
    }

    @GetMapping("/answer-responses/risk-assessment/{studyCode}")
    @Secured(RoleManager.PARTICIPANT)
    public AnswerResponseDTO getRiskAssessmentResponseFromStudyCode(@PathVariable String studyCode) {
        log.debug("REST request to get Risk Assessment AnswerResponse");
        AnswerResponseDTO answerResponseDTO = studyIdService.getRiskAssessmentResponseFromStudyCode(studyCode);
        return answerResponseDTO;
    }

    @PutMapping("/answer-responses/risk-assessment/save")
    @Secured(RoleManager.PARTICIPANT)
    public ResponseEntity<String> saveRiskAssessment(
            Principal principal,
            @Valid @RequestBody AnswerResponseDTO answerResponseDTO
    ) throws URISyntaxException {
        log.debug("REST request to save Risk Assessment AnswerResponse: {}", answerResponseDTO);
        if(answerResponseService.save(
            principal.getName(),
            answerResponseDTO,
            QuestionnaireType.RISK_ASSESSMENT,
            ResponseState.IN_PROGRESS
        )) {
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
        if(answerResponseService.save(
            principal.getName(),
            answerResponseDTO,
            QuestionnaireType.RISK_ASSESSMENT,
            ResponseState.REFERRED
        )) {
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
        if(answerResponseService.save(
            principal.getName(),
            answerResponseDTO,
            QuestionnaireType.RISK_ASSESSMENT,
            ResponseState.VALIDATED
        )) {
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
}
