package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;
import uk.ac.herc.bcra.service.AnswerResponseService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.AnswerResponseDTO;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

    private static final String ENTITY_NAME = "answerResponse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnswerResponseService answerResponseService;

    public AnswerResponseResource(
        AnswerResponseService answerResponseService
    ) {
        this.answerResponseService = answerResponseService;
    }

    @GetMapping("/answer-responses/consent")
    public ResponseEntity<AnswerResponseDTO> getConsentAnswerResponse(Principal principal) {
        log.debug("REST request to get Consent");
        Optional<AnswerResponseDTO> answerResponseDTO = 
            answerResponseService
                .findOne(
                    principal.getName(),
                    QuestionnaireType.CONSENT_FORM
            );
        return ResponseUtil.wrapOrNotFound(answerResponseDTO);
    }

    @GetMapping("/answer-responses/questionnaire")
    public ResponseEntity<AnswerResponseDTO> getRiskAssesmentAnswerResponse(Principal principal) {
        log.debug("REST request to get Risk Assesment");
        Optional<AnswerResponseDTO> answerResponseDTO = 
            answerResponseService
                .findOne(
                    principal.getName(),
                    QuestionnaireType.RISK_ASSESMENT
            );
        return ResponseUtil.wrapOrNotFound(answerResponseDTO);
    }

    /**
     * {@code PUT  /answer-responses} : Updates an existing answerResponse.
     *
     * @param answerResponseDTO the answerResponseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated answerResponseDTO,
     * or with status {@code 400 (Bad Request)} if the answerResponseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the answerResponseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/answer-responses")
    public ResponseEntity<AnswerResponseDTO> updateAnswerResponse(@Valid @RequestBody AnswerResponseDTO answerResponseDTO) throws URISyntaxException {
        log.debug("REST request to update AnswerResponse : {}", answerResponseDTO);
        if (answerResponseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnswerResponseDTO result = answerResponseService.save(answerResponseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, answerResponseDTO.getId().toString()))
            .body(result);
    }
}
