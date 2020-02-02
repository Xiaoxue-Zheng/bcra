package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.DisplayConditionService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.DisplayConditionDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

    public DisplayConditionResource(DisplayConditionService displayConditionService) {
        this.displayConditionService = displayConditionService;
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

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of displayConditions in body.
     */
    @GetMapping("/display-conditions")
    public List<DisplayConditionDTO> getAllDisplayConditions() {
        log.debug("REST request to get all DisplayConditions");
        return displayConditionService.findAll();
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
