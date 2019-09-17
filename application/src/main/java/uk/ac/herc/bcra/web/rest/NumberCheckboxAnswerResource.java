package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.NumberCheckboxAnswerService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.NumberCheckboxAnswerDTO;
import uk.ac.herc.bcra.service.dto.NumberCheckboxAnswerCriteria;
import uk.ac.herc.bcra.service.NumberCheckboxAnswerQueryService;

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
 * REST controller for managing {@link uk.ac.herc.bcra.domain.NumberCheckboxAnswer}.
 */
@RestController
@RequestMapping("/api")
public class NumberCheckboxAnswerResource {

    private final Logger log = LoggerFactory.getLogger(NumberCheckboxAnswerResource.class);

    private static final String ENTITY_NAME = "numberCheckboxAnswer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NumberCheckboxAnswerService numberCheckboxAnswerService;

    private final NumberCheckboxAnswerQueryService numberCheckboxAnswerQueryService;

    public NumberCheckboxAnswerResource(NumberCheckboxAnswerService numberCheckboxAnswerService, NumberCheckboxAnswerQueryService numberCheckboxAnswerQueryService) {
        this.numberCheckboxAnswerService = numberCheckboxAnswerService;
        this.numberCheckboxAnswerQueryService = numberCheckboxAnswerQueryService;
    }

    /**
     * {@code POST  /number-checkbox-answers} : Create a new numberCheckboxAnswer.
     *
     * @param numberCheckboxAnswerDTO the numberCheckboxAnswerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new numberCheckboxAnswerDTO, or with status {@code 400 (Bad Request)} if the numberCheckboxAnswer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/number-checkbox-answers")
    public ResponseEntity<NumberCheckboxAnswerDTO> createNumberCheckboxAnswer(@RequestBody NumberCheckboxAnswerDTO numberCheckboxAnswerDTO) throws URISyntaxException {
        log.debug("REST request to save NumberCheckboxAnswer : {}", numberCheckboxAnswerDTO);
        if (numberCheckboxAnswerDTO.getId() != null) {
            throw new BadRequestAlertException("A new numberCheckboxAnswer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NumberCheckboxAnswerDTO result = numberCheckboxAnswerService.save(numberCheckboxAnswerDTO);
        return ResponseEntity.created(new URI("/api/number-checkbox-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /number-checkbox-answers} : Updates an existing numberCheckboxAnswer.
     *
     * @param numberCheckboxAnswerDTO the numberCheckboxAnswerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated numberCheckboxAnswerDTO,
     * or with status {@code 400 (Bad Request)} if the numberCheckboxAnswerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the numberCheckboxAnswerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/number-checkbox-answers")
    public ResponseEntity<NumberCheckboxAnswerDTO> updateNumberCheckboxAnswer(@RequestBody NumberCheckboxAnswerDTO numberCheckboxAnswerDTO) throws URISyntaxException {
        log.debug("REST request to update NumberCheckboxAnswer : {}", numberCheckboxAnswerDTO);
        if (numberCheckboxAnswerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NumberCheckboxAnswerDTO result = numberCheckboxAnswerService.save(numberCheckboxAnswerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, numberCheckboxAnswerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /number-checkbox-answers} : get all the numberCheckboxAnswers.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of numberCheckboxAnswers in body.
     */
    @GetMapping("/number-checkbox-answers")
    public ResponseEntity<List<NumberCheckboxAnswerDTO>> getAllNumberCheckboxAnswers(NumberCheckboxAnswerCriteria criteria, Pageable pageable) {
        log.debug("REST request to get NumberCheckboxAnswers by criteria: {}", criteria);
        Page<NumberCheckboxAnswerDTO> page = numberCheckboxAnswerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /number-checkbox-answers/count} : count all the numberCheckboxAnswers.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/number-checkbox-answers/count")
    public ResponseEntity<Long> countNumberCheckboxAnswers(NumberCheckboxAnswerCriteria criteria) {
        log.debug("REST request to count NumberCheckboxAnswers by criteria: {}", criteria);
        return ResponseEntity.ok().body(numberCheckboxAnswerQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /number-checkbox-answers/:id} : get the "id" numberCheckboxAnswer.
     *
     * @param id the id of the numberCheckboxAnswerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the numberCheckboxAnswerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/number-checkbox-answers/{id}")
    public ResponseEntity<NumberCheckboxAnswerDTO> getNumberCheckboxAnswer(@PathVariable Long id) {
        log.debug("REST request to get NumberCheckboxAnswer : {}", id);
        Optional<NumberCheckboxAnswerDTO> numberCheckboxAnswerDTO = numberCheckboxAnswerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(numberCheckboxAnswerDTO);
    }

    /**
     * {@code DELETE  /number-checkbox-answers/:id} : delete the "id" numberCheckboxAnswer.
     *
     * @param id the id of the numberCheckboxAnswerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/number-checkbox-answers/{id}")
    public ResponseEntity<Void> deleteNumberCheckboxAnswer(@PathVariable Long id) {
        log.debug("REST request to delete NumberCheckboxAnswer : {}", id);
        numberCheckboxAnswerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
