package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.domain.RiskFactor;
import uk.ac.herc.bcra.service.RiskFactorService;
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
 * REST controller for managing {@link uk.ac.herc.bcra.domain.RiskFactor}.
 */
@RestController
@RequestMapping("/api")
public class RiskFactorResource {

    private final Logger log = LoggerFactory.getLogger(RiskFactorResource.class);

    private static final String ENTITY_NAME = "riskFactor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RiskFactorService riskFactorService;

    public RiskFactorResource(RiskFactorService riskFactorService) {
        this.riskFactorService = riskFactorService;
    }

    /**
     * {@code POST  /risk-factors} : Create a new riskFactor.
     *
     * @param riskFactor the riskFactor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new riskFactor, or with status {@code 400 (Bad Request)} if the riskFactor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/risk-factors")
    public ResponseEntity<RiskFactor> createRiskFactor(@Valid @RequestBody RiskFactor riskFactor) throws URISyntaxException {
        log.debug("REST request to save RiskFactor : {}", riskFactor);
        if (riskFactor.getId() != null) {
            throw new BadRequestAlertException("A new riskFactor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RiskFactor result = riskFactorService.save(riskFactor);
        return ResponseEntity.created(new URI("/api/risk-factors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /risk-factors} : Updates an existing riskFactor.
     *
     * @param riskFactor the riskFactor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riskFactor,
     * or with status {@code 400 (Bad Request)} if the riskFactor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the riskFactor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/risk-factors")
    public ResponseEntity<RiskFactor> updateRiskFactor(@Valid @RequestBody RiskFactor riskFactor) throws URISyntaxException {
        log.debug("REST request to update RiskFactor : {}", riskFactor);
        if (riskFactor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RiskFactor result = riskFactorService.save(riskFactor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, riskFactor.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /risk-factors} : get all the riskFactors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of riskFactors in body.
     */
    @GetMapping("/risk-factors")
    public List<RiskFactor> getAllRiskFactors() {
        log.debug("REST request to get all RiskFactors");
        return riskFactorService.findAll();
    }

    /**
     * {@code GET  /risk-factors/:id} : get the "id" riskFactor.
     *
     * @param id the id of the riskFactor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the riskFactor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/risk-factors/{id}")
    public ResponseEntity<RiskFactor> getRiskFactor(@PathVariable Long id) {
        log.debug("REST request to get RiskFactor : {}", id);
        Optional<RiskFactor> riskFactor = riskFactorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(riskFactor);
    }

    /**
     * {@code DELETE  /risk-factors/:id} : delete the "id" riskFactor.
     *
     * @param id the id of the riskFactor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/risk-factors/{id}")
    public ResponseEntity<Void> deleteRiskFactor(@PathVariable Long id) {
        log.debug("REST request to delete RiskFactor : {}", id);
        riskFactorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
