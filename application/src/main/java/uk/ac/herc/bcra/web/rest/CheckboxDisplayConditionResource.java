package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.CheckboxDisplayConditionService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.CheckboxDisplayConditionDTO;
import uk.ac.herc.bcra.service.dto.CheckboxDisplayConditionCriteria;
import uk.ac.herc.bcra.service.CheckboxDisplayConditionQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link uk.ac.herc.bcra.domain.CheckboxDisplayCondition}.
 */
@RestController
@RequestMapping("/api")
public class CheckboxDisplayConditionResource {

    private final Logger log = LoggerFactory.getLogger(CheckboxDisplayConditionResource.class);

    private static final String ENTITY_NAME = "checkboxDisplayCondition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CheckboxDisplayConditionService checkboxDisplayConditionService;

    private final CheckboxDisplayConditionQueryService checkboxDisplayConditionQueryService;

    public CheckboxDisplayConditionResource(CheckboxDisplayConditionService checkboxDisplayConditionService, CheckboxDisplayConditionQueryService checkboxDisplayConditionQueryService) {
        this.checkboxDisplayConditionService = checkboxDisplayConditionService;
        this.checkboxDisplayConditionQueryService = checkboxDisplayConditionQueryService;
    }

    /**
     * {@code POST  /checkbox-display-conditions} : Create a new checkboxDisplayCondition.
     *
     * @param checkboxDisplayConditionDTO the checkboxDisplayConditionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checkboxDisplayConditionDTO, or with status {@code 400 (Bad Request)} if the checkboxDisplayCondition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/checkbox-display-conditions")
    public ResponseEntity<CheckboxDisplayConditionDTO> createCheckboxDisplayCondition(@RequestBody CheckboxDisplayConditionDTO checkboxDisplayConditionDTO) throws URISyntaxException {
        log.debug("REST request to save CheckboxDisplayCondition : {}", checkboxDisplayConditionDTO);
        if (checkboxDisplayConditionDTO.getId() != null) {
            throw new BadRequestAlertException("A new checkboxDisplayCondition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CheckboxDisplayConditionDTO result = checkboxDisplayConditionService.save(checkboxDisplayConditionDTO);
        return ResponseEntity.created(new URI("/api/checkbox-display-conditions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /checkbox-display-conditions} : Updates an existing checkboxDisplayCondition.
     *
     * @param checkboxDisplayConditionDTO the checkboxDisplayConditionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkboxDisplayConditionDTO,
     * or with status {@code 400 (Bad Request)} if the checkboxDisplayConditionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the checkboxDisplayConditionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/checkbox-display-conditions")
    public ResponseEntity<CheckboxDisplayConditionDTO> updateCheckboxDisplayCondition(@RequestBody CheckboxDisplayConditionDTO checkboxDisplayConditionDTO) throws URISyntaxException {
        log.debug("REST request to update CheckboxDisplayCondition : {}", checkboxDisplayConditionDTO);
        if (checkboxDisplayConditionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CheckboxDisplayConditionDTO result = checkboxDisplayConditionService.save(checkboxDisplayConditionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, checkboxDisplayConditionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /checkbox-display-conditions} : get all the checkboxDisplayConditions.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkboxDisplayConditions in body.
     */
    @GetMapping("/checkbox-display-conditions")
    public ResponseEntity<List<CheckboxDisplayConditionDTO>> getAllCheckboxDisplayConditions(CheckboxDisplayConditionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CheckboxDisplayConditions by criteria: {}", criteria);
        Page<CheckboxDisplayConditionDTO> page = checkboxDisplayConditionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /checkbox-display-conditions/count} : count all the checkboxDisplayConditions.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/checkbox-display-conditions/count")
    public ResponseEntity<Long> countCheckboxDisplayConditions(CheckboxDisplayConditionCriteria criteria) {
        log.debug("REST request to count CheckboxDisplayConditions by criteria: {}", criteria);
        return ResponseEntity.ok().body(checkboxDisplayConditionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /checkbox-display-conditions/:id} : get the "id" checkboxDisplayCondition.
     *
     * @param id the id of the checkboxDisplayConditionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the checkboxDisplayConditionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/checkbox-display-conditions/{id}")
    public ResponseEntity<CheckboxDisplayConditionDTO> getCheckboxDisplayCondition(@PathVariable Long id) {
        log.debug("REST request to get CheckboxDisplayCondition : {}", id);
        Optional<CheckboxDisplayConditionDTO> checkboxDisplayConditionDTO = checkboxDisplayConditionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(checkboxDisplayConditionDTO);
    }

    /**
     * {@code DELETE  /checkbox-display-conditions/:id} : delete the "id" checkboxDisplayCondition.
     *
     * @param id the id of the checkboxDisplayConditionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/checkbox-display-conditions/{id}")
    public ResponseEntity<Void> deleteCheckboxDisplayCondition(@PathVariable Long id) {
        log.debug("REST request to delete CheckboxDisplayCondition : {}", id);
        checkboxDisplayConditionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
