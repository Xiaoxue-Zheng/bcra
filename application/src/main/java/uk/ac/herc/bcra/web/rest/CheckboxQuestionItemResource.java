package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.CheckboxQuestionItemService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.CheckboxQuestionItemDTO;
import uk.ac.herc.bcra.service.dto.CheckboxQuestionItemCriteria;
import uk.ac.herc.bcra.service.CheckboxQuestionItemQueryService;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link uk.ac.herc.bcra.domain.CheckboxQuestionItem}.
 */
@RestController
@RequestMapping("/api")
public class CheckboxQuestionItemResource {

    private final Logger log = LoggerFactory.getLogger(CheckboxQuestionItemResource.class);

    private static final String ENTITY_NAME = "checkboxQuestionItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CheckboxQuestionItemService checkboxQuestionItemService;

    private final CheckboxQuestionItemQueryService checkboxQuestionItemQueryService;

    public CheckboxQuestionItemResource(CheckboxQuestionItemService checkboxQuestionItemService, CheckboxQuestionItemQueryService checkboxQuestionItemQueryService) {
        this.checkboxQuestionItemService = checkboxQuestionItemService;
        this.checkboxQuestionItemQueryService = checkboxQuestionItemQueryService;
    }

    /**
     * {@code POST  /checkbox-question-items} : Create a new checkboxQuestionItem.
     *
     * @param checkboxQuestionItemDTO the checkboxQuestionItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checkboxQuestionItemDTO, or with status {@code 400 (Bad Request)} if the checkboxQuestionItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/checkbox-question-items")
    public ResponseEntity<CheckboxQuestionItemDTO> createCheckboxQuestionItem(@Valid @RequestBody CheckboxQuestionItemDTO checkboxQuestionItemDTO) throws URISyntaxException {
        log.debug("REST request to save CheckboxQuestionItem : {}", checkboxQuestionItemDTO);
        if (checkboxQuestionItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new checkboxQuestionItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CheckboxQuestionItemDTO result = checkboxQuestionItemService.save(checkboxQuestionItemDTO);
        return ResponseEntity.created(new URI("/api/checkbox-question-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /checkbox-question-items} : Updates an existing checkboxQuestionItem.
     *
     * @param checkboxQuestionItemDTO the checkboxQuestionItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkboxQuestionItemDTO,
     * or with status {@code 400 (Bad Request)} if the checkboxQuestionItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the checkboxQuestionItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/checkbox-question-items")
    public ResponseEntity<CheckboxQuestionItemDTO> updateCheckboxQuestionItem(@Valid @RequestBody CheckboxQuestionItemDTO checkboxQuestionItemDTO) throws URISyntaxException {
        log.debug("REST request to update CheckboxQuestionItem : {}", checkboxQuestionItemDTO);
        if (checkboxQuestionItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CheckboxQuestionItemDTO result = checkboxQuestionItemService.save(checkboxQuestionItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, checkboxQuestionItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /checkbox-question-items} : get all the checkboxQuestionItems.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkboxQuestionItems in body.
     */
    @GetMapping("/checkbox-question-items")
    public ResponseEntity<List<CheckboxQuestionItemDTO>> getAllCheckboxQuestionItems(CheckboxQuestionItemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CheckboxQuestionItems by criteria: {}", criteria);
        Page<CheckboxQuestionItemDTO> page = checkboxQuestionItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /checkbox-question-items/count} : count all the checkboxQuestionItems.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/checkbox-question-items/count")
    public ResponseEntity<Long> countCheckboxQuestionItems(CheckboxQuestionItemCriteria criteria) {
        log.debug("REST request to count CheckboxQuestionItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(checkboxQuestionItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /checkbox-question-items/:id} : get the "id" checkboxQuestionItem.
     *
     * @param id the id of the checkboxQuestionItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the checkboxQuestionItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/checkbox-question-items/{id}")
    public ResponseEntity<CheckboxQuestionItemDTO> getCheckboxQuestionItem(@PathVariable Long id) {
        log.debug("REST request to get CheckboxQuestionItem : {}", id);
        Optional<CheckboxQuestionItemDTO> checkboxQuestionItemDTO = checkboxQuestionItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(checkboxQuestionItemDTO);
    }

    /**
     * {@code DELETE  /checkbox-question-items/:id} : delete the "id" checkboxQuestionItem.
     *
     * @param id the id of the checkboxQuestionItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/checkbox-question-items/{id}")
    public ResponseEntity<Void> deleteCheckboxQuestionItem(@PathVariable Long id) {
        log.debug("REST request to delete CheckboxQuestionItem : {}", id);
        checkboxQuestionItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
