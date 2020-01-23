package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;
import uk.ac.herc.bcra.domain.enumeration.ResponseState;
import uk.ac.herc.bcra.service.AnswerResponseService;
import uk.ac.herc.bcra.service.dto.AnswerResponseDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Optional;

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

    public AnswerResponseResource(
        AnswerResponseService answerResponseService
    ) {
        this.answerResponseService = answerResponseService;
    }

    @GetMapping("/answer-responses/consent")
    public ResponseEntity<AnswerResponseDTO> getConsent(Principal principal) {
        log.debug("REST request to get Consent AnswerResponse");
        Optional<AnswerResponseDTO> answerResponseDTO = 
            answerResponseService
                .findOne(
                    principal.getName(),
                    QuestionnaireType.CONSENT_FORM
            );
        return ResponseUtil.wrapOrNotFound(answerResponseDTO);
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

    @GetMapping("/answer-responses/risk-assesment")
    public ResponseEntity<AnswerResponseDTO> getRiskAssesment(Principal principal) {
        log.debug("REST request to get Risk Assesment AnswerResponse");
        Optional<AnswerResponseDTO> answerResponseDTO = 
            answerResponseService.findOne(
                principal.getName(),
                QuestionnaireType.RISK_ASSESMENT
            );
        return ResponseUtil.wrapOrNotFound(answerResponseDTO);
    }

    @PutMapping("/answer-responses/risk-assesment/save")
    public ResponseEntity<String> saveRiskAssesment(
            Principal principal,
            @Valid @RequestBody AnswerResponseDTO answerResponseDTO
    ) throws URISyntaxException {
        log.debug("REST request to save Risk Assesment AnswerResponse: {}", answerResponseDTO);
        if(answerResponseService.save(
            principal.getName(),
            answerResponseDTO,
            QuestionnaireType.RISK_ASSESMENT,
            ResponseState.IN_PROGRESS
        )) {
            return ResponseEntity.ok().body("SAVED");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("FAILED");
        }
    }

    @PutMapping("/answer-responses/risk-assesment/submit")
    public ResponseEntity<String> submitRiskAssesment(
            Principal principal,
            @Valid @RequestBody AnswerResponseDTO answerResponseDTO
    ) throws URISyntaxException {
        log.debug("REST request to submit Risk Assesment AnswerResponse: {}", answerResponseDTO);
        if(answerResponseService.save(
            principal.getName(),
            answerResponseDTO,
            QuestionnaireType.RISK_ASSESMENT,
            ResponseState.SUBMITTED
        )) {
            return ResponseEntity.ok().body("SUBMITTED");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("FAILED");
        }
    }
}
