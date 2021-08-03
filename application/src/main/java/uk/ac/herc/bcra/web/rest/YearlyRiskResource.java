package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.domain.YearlyRisk;
import uk.ac.herc.bcra.service.YearlyRiskService;
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
 * REST controller for managing {@link uk.ac.herc.bcra.domain.YearlyRisk}.
 */
@RestController
@RequestMapping("/api")
public class YearlyRiskResource {

    private final Logger log = LoggerFactory.getLogger(YearlyRiskResource.class);

    private static final String ENTITY_NAME = "yearlyRisk";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final YearlyRiskService yearlyRiskService;

    public YearlyRiskResource(YearlyRiskService yearlyRiskService) {
        this.yearlyRiskService = yearlyRiskService;
    }

    /**
     * {@code POST  /risk-factors} : Create a new yearlyRisk.
     *
     * @param yearlyRisk the yearlyRisk to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new yearlyRisk, or with status {@code 400 (Bad Request)} if the yearlyRisk has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/risk-factors")
    public ResponseEntity<YearlyRisk> createRiskFactor(@Valid @RequestBody YearlyRisk yearlyRisk) throws URISyntaxException {
        log.debug("REST request to save RiskFactor : {}", yearlyRisk);
        if (yearlyRisk.getId() != null) {
            throw new BadRequestAlertException("A new yearlyRisk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        YearlyRisk result = yearlyRiskService.save(yearlyRisk);
        return ResponseEntity.created(new URI("/api/risk-factors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /risk-factors} : Updates an existing yearlyRisk.
     *
     * @param yearlyRisk the yearlyRisk to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated yearlyRisk,
     * or with status {@code 400 (Bad Request)} if the yearlyRisk is not valid,
     * or with status {@code 500 (Internal Server Error)} if the yearlyRisk couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/risk-factors")
    public ResponseEntity<YearlyRisk> updateRiskFactor(@Valid @RequestBody YearlyRisk yearlyRisk) throws URISyntaxException {
        log.debug("REST request to update RiskFactor : {}", yearlyRisk);
        if (yearlyRisk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        YearlyRisk result = yearlyRiskService.save(yearlyRisk);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, yearlyRisk.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /risk-factors} : get all the yearlyRisks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of yearlyRisks in body.
     */
    @GetMapping("/risk-factors")
    public List<YearlyRisk> getAllRiskFactors() {
        log.debug("REST request to get all RiskFactors");
        return yearlyRiskService.findAll();
    }

    /**
     * {@code GET  /risk-factors/:id} : get the "id" yearlyRisk.
     *
     * @param id the id of the yearlyRisk to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the yearlyRisk, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/risk-factors/{id}")
    public ResponseEntity<YearlyRisk> getRiskFactor(@PathVariable Long id) {
        log.debug("REST request to get RiskFactor : {}", id);
        Optional<YearlyRisk> yearlyRisk = yearlyRiskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(yearlyRisk);
    }

    /**
     * {@code DELETE  /risk-factors/:id} : delete the "id" yearlyRisk.
     *
     * @param id the id of the yearlyRisk to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/risk-factors/{id}")
    public ResponseEntity<Void> deleteRiskFactor(@PathVariable Long id) {
        log.debug("REST request to delete RiskFactor : {}", id);
        yearlyRiskService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
