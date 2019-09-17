package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.RadioQuestionItemService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.RadioQuestionItemDTO;
import uk.ac.herc.bcra.service.dto.RadioQuestionItemCriteria;
import uk.ac.herc.bcra.service.RadioQuestionItemQueryService;

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
 * REST controller for managing {@link uk.ac.herc.bcra.domain.RadioQuestionItem}.
 */
@RestController
@RequestMapping("/api")
public class RadioQuestionItemResource {

    private final Logger log = LoggerFactory.getLogger(RadioQuestionItemResource.class);

    private static final String ENTITY_NAME = "radioQuestionItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RadioQuestionItemService radioQuestionItemService;

    private final RadioQuestionItemQueryService radioQuestionItemQueryService;

    public RadioQuestionItemResource(RadioQuestionItemService radioQuestionItemService, RadioQuestionItemQueryService radioQuestionItemQueryService) {
        this.radioQuestionItemService = radioQuestionItemService;
        this.radioQuestionItemQueryService = radioQuestionItemQueryService;
    }

    /**
     * {@code POST  /radio-question-items} : Create a new radioQuestionItem.
     *
     * @param radioQuestionItemDTO the radioQuestionItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new radioQuestionItemDTO, or with status {@code 400 (Bad Request)} if the radioQuestionItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/radio-question-items")
    public ResponseEntity<RadioQuestionItemDTO> createRadioQuestionItem(@Valid @RequestBody RadioQuestionItemDTO radioQuestionItemDTO) throws URISyntaxException {
        log.debug("REST request to save RadioQuestionItem : {}", radioQuestionItemDTO);
        if (radioQuestionItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new radioQuestionItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RadioQuestionItemDTO result = radioQuestionItemService.save(radioQuestionItemDTO);
        return ResponseEntity.created(new URI("/api/radio-question-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /radio-question-items} : Updates an existing radioQuestionItem.
     *
     * @param radioQuestionItemDTO the radioQuestionItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated radioQuestionItemDTO,
     * or with status {@code 400 (Bad Request)} if the radioQuestionItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the radioQuestionItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/radio-question-items")
    public ResponseEntity<RadioQuestionItemDTO> updateRadioQuestionItem(@Valid @RequestBody RadioQuestionItemDTO radioQuestionItemDTO) throws URISyntaxException {
        log.debug("REST request to update RadioQuestionItem : {}", radioQuestionItemDTO);
        if (radioQuestionItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RadioQuestionItemDTO result = radioQuestionItemService.save(radioQuestionItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, radioQuestionItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /radio-question-items} : get all the radioQuestionItems.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of radioQuestionItems in body.
     */
    @GetMapping("/radio-question-items")
    public ResponseEntity<List<RadioQuestionItemDTO>> getAllRadioQuestionItems(RadioQuestionItemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RadioQuestionItems by criteria: {}", criteria);
        Page<RadioQuestionItemDTO> page = radioQuestionItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /radio-question-items/count} : count all the radioQuestionItems.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/radio-question-items/count")
    public ResponseEntity<Long> countRadioQuestionItems(RadioQuestionItemCriteria criteria) {
        log.debug("REST request to count RadioQuestionItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(radioQuestionItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /radio-question-items/:id} : get the "id" radioQuestionItem.
     *
     * @param id the id of the radioQuestionItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the radioQuestionItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/radio-question-items/{id}")
    public ResponseEntity<RadioQuestionItemDTO> getRadioQuestionItem(@PathVariable Long id) {
        log.debug("REST request to get RadioQuestionItem : {}", id);
        Optional<RadioQuestionItemDTO> radioQuestionItemDTO = radioQuestionItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(radioQuestionItemDTO);
    }

    /**
     * {@code DELETE  /radio-question-items/:id} : delete the "id" radioQuestionItem.
     *
     * @param id the id of the radioQuestionItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/radio-question-items/{id}")
    public ResponseEntity<Void> deleteRadioQuestionItem(@PathVariable Long id) {
        log.debug("REST request to delete RadioQuestionItem : {}", id);
        radioQuestionItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
