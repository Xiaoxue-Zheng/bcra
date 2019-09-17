package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.CheckboxAnswerItemService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.CheckboxAnswerItemDTO;
import uk.ac.herc.bcra.service.dto.CheckboxAnswerItemCriteria;
import uk.ac.herc.bcra.service.CheckboxAnswerItemQueryService;

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
 * REST controller for managing {@link uk.ac.herc.bcra.domain.CheckboxAnswerItem}.
 */
@RestController
@RequestMapping("/api")
public class CheckboxAnswerItemResource {

    private final Logger log = LoggerFactory.getLogger(CheckboxAnswerItemResource.class);

    private static final String ENTITY_NAME = "checkboxAnswerItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CheckboxAnswerItemService checkboxAnswerItemService;

    private final CheckboxAnswerItemQueryService checkboxAnswerItemQueryService;

    public CheckboxAnswerItemResource(CheckboxAnswerItemService checkboxAnswerItemService, CheckboxAnswerItemQueryService checkboxAnswerItemQueryService) {
        this.checkboxAnswerItemService = checkboxAnswerItemService;
        this.checkboxAnswerItemQueryService = checkboxAnswerItemQueryService;
    }

    /**
     * {@code POST  /checkbox-answer-items} : Create a new checkboxAnswerItem.
     *
     * @param checkboxAnswerItemDTO the checkboxAnswerItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checkboxAnswerItemDTO, or with status {@code 400 (Bad Request)} if the checkboxAnswerItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/checkbox-answer-items")
    public ResponseEntity<CheckboxAnswerItemDTO> createCheckboxAnswerItem(@RequestBody CheckboxAnswerItemDTO checkboxAnswerItemDTO) throws URISyntaxException {
        log.debug("REST request to save CheckboxAnswerItem : {}", checkboxAnswerItemDTO);
        if (checkboxAnswerItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new checkboxAnswerItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CheckboxAnswerItemDTO result = checkboxAnswerItemService.save(checkboxAnswerItemDTO);
        return ResponseEntity.created(new URI("/api/checkbox-answer-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /checkbox-answer-items} : Updates an existing checkboxAnswerItem.
     *
     * @param checkboxAnswerItemDTO the checkboxAnswerItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkboxAnswerItemDTO,
     * or with status {@code 400 (Bad Request)} if the checkboxAnswerItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the checkboxAnswerItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/checkbox-answer-items")
    public ResponseEntity<CheckboxAnswerItemDTO> updateCheckboxAnswerItem(@RequestBody CheckboxAnswerItemDTO checkboxAnswerItemDTO) throws URISyntaxException {
        log.debug("REST request to update CheckboxAnswerItem : {}", checkboxAnswerItemDTO);
        if (checkboxAnswerItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CheckboxAnswerItemDTO result = checkboxAnswerItemService.save(checkboxAnswerItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, checkboxAnswerItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /checkbox-answer-items} : get all the checkboxAnswerItems.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkboxAnswerItems in body.
     */
    @GetMapping("/checkbox-answer-items")
    public ResponseEntity<List<CheckboxAnswerItemDTO>> getAllCheckboxAnswerItems(CheckboxAnswerItemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CheckboxAnswerItems by criteria: {}", criteria);
        Page<CheckboxAnswerItemDTO> page = checkboxAnswerItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /checkbox-answer-items/count} : count all the checkboxAnswerItems.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/checkbox-answer-items/count")
    public ResponseEntity<Long> countCheckboxAnswerItems(CheckboxAnswerItemCriteria criteria) {
        log.debug("REST request to count CheckboxAnswerItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(checkboxAnswerItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /checkbox-answer-items/:id} : get the "id" checkboxAnswerItem.
     *
     * @param id the id of the checkboxAnswerItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the checkboxAnswerItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/checkbox-answer-items/{id}")
    public ResponseEntity<CheckboxAnswerItemDTO> getCheckboxAnswerItem(@PathVariable Long id) {
        log.debug("REST request to get CheckboxAnswerItem : {}", id);
        Optional<CheckboxAnswerItemDTO> checkboxAnswerItemDTO = checkboxAnswerItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(checkboxAnswerItemDTO);
    }

    /**
     * {@code DELETE  /checkbox-answer-items/:id} : delete the "id" checkboxAnswerItem.
     *
     * @param id the id of the checkboxAnswerItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/checkbox-answer-items/{id}")
    public ResponseEntity<Void> deleteCheckboxAnswerItem(@PathVariable Long id) {
        log.debug("REST request to delete CheckboxAnswerItem : {}", id);
        checkboxAnswerItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
