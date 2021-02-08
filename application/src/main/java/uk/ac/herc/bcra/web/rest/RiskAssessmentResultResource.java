package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.domain.RiskAssessmentResult;
import uk.ac.herc.bcra.service.RiskAssessmentResultService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link uk.ac.herc.bcra.domain.RiskAssessmentResult}.
 */
@RestController
@RequestMapping("/api")
public class RiskAssessmentResultResource {

    private final Logger log = LoggerFactory.getLogger(RiskAssessmentResultResource.class);

    private static final String ENTITY_NAME = "riskAssessmentResult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RiskAssessmentResultService riskAssessmentResultService;

    public RiskAssessmentResultResource(RiskAssessmentResultService riskAssessmentResultService) {
        this.riskAssessmentResultService = riskAssessmentResultService;
    }

    /**
     * {@code POST  /risk-assessment-results} : Create a new riskAssessmentResult.
     *
     * @param riskAssessmentResult the riskAssessmentResult to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new riskAssessmentResult, or with status {@code 400 (Bad Request)} if the riskAssessmentResult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/risk-assessment-results")
    public ResponseEntity<RiskAssessmentResult> createRiskAssessmentResult(@Valid @RequestBody RiskAssessmentResult riskAssessmentResult) throws URISyntaxException {
        log.debug("REST request to save RiskAssessmentResult : {}", riskAssessmentResult);
        if (riskAssessmentResult.getId() != null) {
            throw new BadRequestAlertException("A new riskAssessmentResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RiskAssessmentResult result = riskAssessmentResultService.save(riskAssessmentResult);
        return ResponseEntity.created(new URI("/api/risk-assessment-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /risk-assessment-results} : Updates an existing riskAssessmentResult.
     *
     * @param riskAssessmentResult the riskAssessmentResult to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riskAssessmentResult,
     * or with status {@code 400 (Bad Request)} if the riskAssessmentResult is not valid,
     * or with status {@code 500 (Internal Server Error)} if the riskAssessmentResult couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/risk-assessment-results")
    public ResponseEntity<RiskAssessmentResult> updateRiskAssessmentResult(@Valid @RequestBody RiskAssessmentResult riskAssessmentResult) throws URISyntaxException {
        log.debug("REST request to update RiskAssessmentResult : {}", riskAssessmentResult);
        if (riskAssessmentResult.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RiskAssessmentResult result = riskAssessmentResultService.save(riskAssessmentResult);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, riskAssessmentResult.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /risk-assessment-results} : get all the riskAssessmentResults.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of riskAssessmentResults in body.
     */
    @GetMapping("/risk-assessment-results")
    public List<RiskAssessmentResult> getAllRiskAssessmentResults() {
        log.debug("REST request to get all RiskAssessmentResults");
        return riskAssessmentResultService.findAll();
    }

    /**
     * {@code GET  /risk-assessment-results/:id} : get the "id" riskAssessmentResult.
     *
     * @param id the id of the riskAssessmentResult to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the riskAssessmentResult, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/risk-assessment-results/{id}")
    public ResponseEntity<RiskAssessmentResult> getRiskAssessmentResult(@PathVariable Long id) {
        log.debug("REST request to get RiskAssessmentResult : {}", id);
        Optional<RiskAssessmentResult> riskAssessmentResult = riskAssessmentResultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(riskAssessmentResult);
    }

    /**
     * {@code DELETE  /risk-assessment-results/:id} : delete the "id" riskAssessmentResult.
     *
     * @param id the id of the riskAssessmentResult to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/risk-assessment-results/{id}")
    public ResponseEntity<Void> deleteRiskAssessmentResult(@PathVariable Long id) {
        log.debug("REST request to delete RiskAssessmentResult : {}", id);
        riskAssessmentResultService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
