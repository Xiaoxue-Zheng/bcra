package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.DisplayConditionService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.DisplayConditionDTO;
import uk.ac.herc.bcra.service.dto.DisplayConditionCriteria;
import uk.ac.herc.bcra.service.DisplayConditionQueryService;

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
 * REST controller for managing {@link uk.ac.herc.bcra.domain.DisplayCondition}.
 */
@RestController
@RequestMapping("/api")
public class DisplayConditionResource {

    private final Logger log = LoggerFactory.getLogger(DisplayConditionResource.class);

    private static final String ENTITY_NAME = "displayCondition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DisplayConditionService displayConditionService;

    private final DisplayConditionQueryService displayConditionQueryService;

    public DisplayConditionResource(DisplayConditionService displayConditionService, DisplayConditionQueryService displayConditionQueryService) {
        this.displayConditionService = displayConditionService;
        this.displayConditionQueryService = displayConditionQueryService;
    }

    /**
     * {@code POST  /display-conditions} : Create a new displayCondition.
     *
     * @param displayConditionDTO the displayConditionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new displayConditionDTO, or with status {@code 400 (Bad Request)} if the displayCondition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/display-conditions")
    public ResponseEntity<DisplayConditionDTO> createDisplayCondition(@RequestBody DisplayConditionDTO displayConditionDTO) throws URISyntaxException {
        log.debug("REST request to save DisplayCondition : {}", displayConditionDTO);
        if (displayConditionDTO.getId() != null) {
            throw new BadRequestAlertException("A new displayCondition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DisplayConditionDTO result = displayConditionService.save(displayConditionDTO);
        return ResponseEntity.created(new URI("/api/display-conditions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /display-conditions} : Updates an existing displayCondition.
     *
     * @param displayConditionDTO the displayConditionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated displayConditionDTO,
     * or with status {@code 400 (Bad Request)} if the displayConditionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the displayConditionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/display-conditions")
    public ResponseEntity<DisplayConditionDTO> updateDisplayCondition(@RequestBody DisplayConditionDTO displayConditionDTO) throws URISyntaxException {
        log.debug("REST request to update DisplayCondition : {}", displayConditionDTO);
        if (displayConditionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DisplayConditionDTO result = displayConditionService.save(displayConditionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, displayConditionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /display-conditions} : get all the displayConditions.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of displayConditions in body.
     */
    @GetMapping("/display-conditions")
    public ResponseEntity<List<DisplayConditionDTO>> getAllDisplayConditions(DisplayConditionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DisplayConditions by criteria: {}", criteria);
        Page<DisplayConditionDTO> page = displayConditionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /display-conditions/count} : count all the displayConditions.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/display-conditions/count")
    public ResponseEntity<Long> countDisplayConditions(DisplayConditionCriteria criteria) {
        log.debug("REST request to count DisplayConditions by criteria: {}", criteria);
        return ResponseEntity.ok().body(displayConditionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /display-conditions/:id} : get the "id" displayCondition.
     *
     * @param id the id of the displayConditionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the displayConditionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/display-conditions/{id}")
    public ResponseEntity<DisplayConditionDTO> getDisplayCondition(@PathVariable Long id) {
        log.debug("REST request to get DisplayCondition : {}", id);
        Optional<DisplayConditionDTO> displayConditionDTO = displayConditionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(displayConditionDTO);
    }

    /**
     * {@code DELETE  /display-conditions/:id} : delete the "id" displayCondition.
     *
     * @param id the id of the displayConditionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/display-conditions/{id}")
    public ResponseEntity<Void> deleteDisplayCondition(@PathVariable Long id) {
        log.debug("REST request to delete DisplayCondition : {}", id);
        displayConditionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
