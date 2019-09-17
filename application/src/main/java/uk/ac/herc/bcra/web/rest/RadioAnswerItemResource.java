package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.RadioAnswerItemService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.RadioAnswerItemDTO;
import uk.ac.herc.bcra.service.dto.RadioAnswerItemCriteria;
import uk.ac.herc.bcra.service.RadioAnswerItemQueryService;

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
 * REST controller for managing {@link uk.ac.herc.bcra.domain.RadioAnswerItem}.
 */
@RestController
@RequestMapping("/api")
public class RadioAnswerItemResource {

    private final Logger log = LoggerFactory.getLogger(RadioAnswerItemResource.class);

    private static final String ENTITY_NAME = "radioAnswerItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RadioAnswerItemService radioAnswerItemService;

    private final RadioAnswerItemQueryService radioAnswerItemQueryService;

    public RadioAnswerItemResource(RadioAnswerItemService radioAnswerItemService, RadioAnswerItemQueryService radioAnswerItemQueryService) {
        this.radioAnswerItemService = radioAnswerItemService;
        this.radioAnswerItemQueryService = radioAnswerItemQueryService;
    }

    /**
     * {@code POST  /radio-answer-items} : Create a new radioAnswerItem.
     *
     * @param radioAnswerItemDTO the radioAnswerItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new radioAnswerItemDTO, or with status {@code 400 (Bad Request)} if the radioAnswerItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/radio-answer-items")
    public ResponseEntity<RadioAnswerItemDTO> createRadioAnswerItem(@RequestBody RadioAnswerItemDTO radioAnswerItemDTO) throws URISyntaxException {
        log.debug("REST request to save RadioAnswerItem : {}", radioAnswerItemDTO);
        if (radioAnswerItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new radioAnswerItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RadioAnswerItemDTO result = radioAnswerItemService.save(radioAnswerItemDTO);
        return ResponseEntity.created(new URI("/api/radio-answer-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /radio-answer-items} : Updates an existing radioAnswerItem.
     *
     * @param radioAnswerItemDTO the radioAnswerItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated radioAnswerItemDTO,
     * or with status {@code 400 (Bad Request)} if the radioAnswerItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the radioAnswerItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/radio-answer-items")
    public ResponseEntity<RadioAnswerItemDTO> updateRadioAnswerItem(@RequestBody RadioAnswerItemDTO radioAnswerItemDTO) throws URISyntaxException {
        log.debug("REST request to update RadioAnswerItem : {}", radioAnswerItemDTO);
        if (radioAnswerItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RadioAnswerItemDTO result = radioAnswerItemService.save(radioAnswerItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, radioAnswerItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /radio-answer-items} : get all the radioAnswerItems.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of radioAnswerItems in body.
     */
    @GetMapping("/radio-answer-items")
    public ResponseEntity<List<RadioAnswerItemDTO>> getAllRadioAnswerItems(RadioAnswerItemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RadioAnswerItems by criteria: {}", criteria);
        Page<RadioAnswerItemDTO> page = radioAnswerItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /radio-answer-items/count} : count all the radioAnswerItems.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/radio-answer-items/count")
    public ResponseEntity<Long> countRadioAnswerItems(RadioAnswerItemCriteria criteria) {
        log.debug("REST request to count RadioAnswerItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(radioAnswerItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /radio-answer-items/:id} : get the "id" radioAnswerItem.
     *
     * @param id the id of the radioAnswerItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the radioAnswerItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/radio-answer-items/{id}")
    public ResponseEntity<RadioAnswerItemDTO> getRadioAnswerItem(@PathVariable Long id) {
        log.debug("REST request to get RadioAnswerItem : {}", id);
        Optional<RadioAnswerItemDTO> radioAnswerItemDTO = radioAnswerItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(radioAnswerItemDTO);
    }

    /**
     * {@code DELETE  /radio-answer-items/:id} : delete the "id" radioAnswerItem.
     *
     * @param id the id of the radioAnswerItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/radio-answer-items/{id}")
    public ResponseEntity<Void> deleteRadioAnswerItem(@PathVariable Long id) {
        log.debug("REST request to delete RadioAnswerItem : {}", id);
        radioAnswerItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
