package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.ReferralConditionService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.ReferralConditionDTO;

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
 * REST controller for managing {@link uk.ac.herc.bcra.domain.ReferralCondition}.
 */
@RestController
@RequestMapping("/api")
public class ReferralConditionResource {

    private final Logger log = LoggerFactory.getLogger(ReferralConditionResource.class);

    private static final String ENTITY_NAME = "referralCondition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReferralConditionService referralConditionService;

    public ReferralConditionResource(ReferralConditionService referralConditionService) {
        this.referralConditionService = referralConditionService;
    }

    /**
     * {@code POST  /referral-conditions} : Create a new referralCondition.
     *
     * @param referralConditionDTO the referralConditionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new referralConditionDTO, or with status {@code 400 (Bad Request)} if the referralCondition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/referral-conditions")
    public ResponseEntity<ReferralConditionDTO> createReferralCondition(@Valid @RequestBody ReferralConditionDTO referralConditionDTO) throws URISyntaxException {
        log.debug("REST request to save ReferralCondition : {}", referralConditionDTO);
        if (referralConditionDTO.getId() != null) {
            throw new BadRequestAlertException("A new referralCondition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReferralConditionDTO result = referralConditionService.save(referralConditionDTO);
        return ResponseEntity.created(new URI("/api/referral-conditions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /referral-conditions} : Updates an existing referralCondition.
     *
     * @param referralConditionDTO the referralConditionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated referralConditionDTO,
     * or with status {@code 400 (Bad Request)} if the referralConditionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the referralConditionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/referral-conditions")
    public ResponseEntity<ReferralConditionDTO> updateReferralCondition(@Valid @RequestBody ReferralConditionDTO referralConditionDTO) throws URISyntaxException {
        log.debug("REST request to update ReferralCondition : {}", referralConditionDTO);
        if (referralConditionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReferralConditionDTO result = referralConditionService.save(referralConditionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, referralConditionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /referral-conditions} : get all the referralConditions.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of referralConditions in body.
     */
    @GetMapping("/referral-conditions")
    public List<ReferralConditionDTO> getAllReferralConditions() {
        log.debug("REST request to get all ReferralConditions");
        return referralConditionService.findAll();
    }

    /**
     * {@code GET  /referral-conditions/:id} : get the "id" referralCondition.
     *
     * @param id the id of the referralConditionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the referralConditionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/referral-conditions/{id}")
    public ResponseEntity<ReferralConditionDTO> getReferralCondition(@PathVariable Long id) {
        log.debug("REST request to get ReferralCondition : {}", id);
        Optional<ReferralConditionDTO> referralConditionDTO = referralConditionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(referralConditionDTO);
    }

    /**
     * {@code DELETE  /referral-conditions/:id} : delete the "id" referralCondition.
     *
     * @param id the id of the referralConditionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/referral-conditions/{id}")
    public ResponseEntity<Void> deleteReferralCondition(@PathVariable Long id) {
        log.debug("REST request to delete ReferralCondition : {}", id);
        referralConditionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
