package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;
import uk.ac.herc.bcra.service.QuestionnaireService;
import uk.ac.herc.bcra.service.dto.QuestionnaireDTO;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link uk.ac.herc.bcra.domain.Questionnaire}.
 */
@RestController
@RequestMapping("/api")
public class QuestionnaireResource {

    private final Logger log = LoggerFactory.getLogger(QuestionnaireResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionnaireService questionnaireService;

    public QuestionnaireResource(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }

    @GetMapping("/questionnaires/consent")
    public ResponseEntity<QuestionnaireDTO> getConsent() {
        log.debug("REST request to get Consent Questionnaire");
        Optional<QuestionnaireDTO> questionnaireDTO = questionnaireService.getConsentQuestionnaire();
        return ResponseUtil.wrapOrNotFound(questionnaireDTO);
    }

    @GetMapping("/questionnaires/risk-assessment")
    public ResponseEntity<QuestionnaireDTO> getRiskAssessment(Principal principal) {
        log.debug("REST request to get Risk Assessment Questionnaire");
        Optional<QuestionnaireDTO> questionnaireDTO = 
            questionnaireService
                .findOne(
                    principal.getName(),
                    QuestionnaireType.RISK_ASSESSMENT
            );
        return ResponseUtil.wrapOrNotFound(questionnaireDTO);
    }

    /**
     * {@code GET  /questionnaires} : get all the questionnaires.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questionnaires in body.
     */
    @GetMapping("/questionnaires")
    public List<QuestionnaireDTO> getAllQuestionnaires() {
        log.debug("REST request to get all Questionnaires");
        return questionnaireService.findAll();
    }
}
