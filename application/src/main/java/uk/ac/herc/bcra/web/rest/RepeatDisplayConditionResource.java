package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.RepeatDisplayConditionService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.RepeatDisplayConditionDTO;
import uk.ac.herc.bcra.service.dto.RepeatDisplayConditionCriteria;
import uk.ac.herc.bcra.service.RepeatDisplayConditionQueryService;

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
 * REST controller for managing {@link uk.ac.herc.bcra.domain.RepeatDisplayCondition}.
 */
@RestController
@RequestMapping("/api")
public class RepeatDisplayConditionResource {

    private final Logger log = LoggerFactory.getLogger(RepeatDisplayConditionResource.class);

    private static final String ENTITY_NAME = "repeatDisplayCondition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RepeatDisplayConditionService repeatDisplayConditionService;

    private final RepeatDisplayConditionQueryService repeatDisplayConditionQueryService;

    public RepeatDisplayConditionResource(RepeatDisplayConditionService repeatDisplayConditionService, RepeatDisplayConditionQueryService repeatDisplayConditionQueryService) {
        this.repeatDisplayConditionService = repeatDisplayConditionService;
        this.repeatDisplayConditionQueryService = repeatDisplayConditionQueryService;
    }

    /**
     * {@code POST  /repeat-display-conditions} : Create a new repeatDisplayCondition.
     *
     * @param repeatDisplayConditionDTO the repeatDisplayConditionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new repeatDisplayConditionDTO, or with status {@code 400 (Bad Request)} if the repeatDisplayCondition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/repeat-display-conditions")
    public ResponseEntity<RepeatDisplayConditionDTO> createRepeatDisplayCondition(@RequestBody RepeatDisplayConditionDTO repeatDisplayConditionDTO) throws URISyntaxException {
        log.debug("REST request to save RepeatDisplayCondition : {}", repeatDisplayConditionDTO);
        if (repeatDisplayConditionDTO.getId() != null) {
            throw new BadRequestAlertException("A new repeatDisplayCondition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RepeatDisplayConditionDTO result = repeatDisplayConditionService.save(repeatDisplayConditionDTO);
        return ResponseEntity.created(new URI("/api/repeat-display-conditions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /repeat-display-conditions} : Updates an existing repeatDisplayCondition.
     *
     * @param repeatDisplayConditionDTO the repeatDisplayConditionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated repeatDisplayConditionDTO,
     * or with status {@code 400 (Bad Request)} if the repeatDisplayConditionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the repeatDisplayConditionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/repeat-display-conditions")
    public ResponseEntity<RepeatDisplayConditionDTO> updateRepeatDisplayCondition(@RequestBody RepeatDisplayConditionDTO repeatDisplayConditionDTO) throws URISyntaxException {
        log.debug("REST request to update RepeatDisplayCondition : {}", repeatDisplayConditionDTO);
        if (repeatDisplayConditionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RepeatDisplayConditionDTO result = repeatDisplayConditionService.save(repeatDisplayConditionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, repeatDisplayConditionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /repeat-display-conditions} : get all the repeatDisplayConditions.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of repeatDisplayConditions in body.
     */
    @GetMapping("/repeat-display-conditions")
    public ResponseEntity<List<RepeatDisplayConditionDTO>> getAllRepeatDisplayConditions(RepeatDisplayConditionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RepeatDisplayConditions by criteria: {}", criteria);
        Page<RepeatDisplayConditionDTO> page = repeatDisplayConditionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /repeat-display-conditions/count} : count all the repeatDisplayConditions.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/repeat-display-conditions/count")
    public ResponseEntity<Long> countRepeatDisplayConditions(RepeatDisplayConditionCriteria criteria) {
        log.debug("REST request to count RepeatDisplayConditions by criteria: {}", criteria);
        return ResponseEntity.ok().body(repeatDisplayConditionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /repeat-display-conditions/:id} : get the "id" repeatDisplayCondition.
     *
     * @param id the id of the repeatDisplayConditionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the repeatDisplayConditionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/repeat-display-conditions/{id}")
    public ResponseEntity<RepeatDisplayConditionDTO> getRepeatDisplayCondition(@PathVariable Long id) {
        log.debug("REST request to get RepeatDisplayCondition : {}", id);
        Optional<RepeatDisplayConditionDTO> repeatDisplayConditionDTO = repeatDisplayConditionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(repeatDisplayConditionDTO);
    }

    /**
     * {@code DELETE  /repeat-display-conditions/:id} : delete the "id" repeatDisplayCondition.
     *
     * @param id the id of the repeatDisplayConditionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/repeat-display-conditions/{id}")
    public ResponseEntity<Void> deleteRepeatDisplayCondition(@PathVariable Long id) {
        log.debug("REST request to delete RepeatDisplayCondition : {}", id);
        repeatDisplayConditionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
