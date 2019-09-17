package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.NumberCheckboxQuestionService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.NumberCheckboxQuestionDTO;
import uk.ac.herc.bcra.service.dto.NumberCheckboxQuestionCriteria;
import uk.ac.herc.bcra.service.NumberCheckboxQuestionQueryService;

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
 * REST controller for managing {@link uk.ac.herc.bcra.domain.NumberCheckboxQuestion}.
 */
@RestController
@RequestMapping("/api")
public class NumberCheckboxQuestionResource {

    private final Logger log = LoggerFactory.getLogger(NumberCheckboxQuestionResource.class);

    private static final String ENTITY_NAME = "numberCheckboxQuestion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NumberCheckboxQuestionService numberCheckboxQuestionService;

    private final NumberCheckboxQuestionQueryService numberCheckboxQuestionQueryService;

    public NumberCheckboxQuestionResource(NumberCheckboxQuestionService numberCheckboxQuestionService, NumberCheckboxQuestionQueryService numberCheckboxQuestionQueryService) {
        this.numberCheckboxQuestionService = numberCheckboxQuestionService;
        this.numberCheckboxQuestionQueryService = numberCheckboxQuestionQueryService;
    }

    /**
     * {@code POST  /number-checkbox-questions} : Create a new numberCheckboxQuestion.
     *
     * @param numberCheckboxQuestionDTO the numberCheckboxQuestionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new numberCheckboxQuestionDTO, or with status {@code 400 (Bad Request)} if the numberCheckboxQuestion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/number-checkbox-questions")
    public ResponseEntity<NumberCheckboxQuestionDTO> createNumberCheckboxQuestion(@Valid @RequestBody NumberCheckboxQuestionDTO numberCheckboxQuestionDTO) throws URISyntaxException {
        log.debug("REST request to save NumberCheckboxQuestion : {}", numberCheckboxQuestionDTO);
        if (numberCheckboxQuestionDTO.getId() != null) {
            throw new BadRequestAlertException("A new numberCheckboxQuestion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NumberCheckboxQuestionDTO result = numberCheckboxQuestionService.save(numberCheckboxQuestionDTO);
        return ResponseEntity.created(new URI("/api/number-checkbox-questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /number-checkbox-questions} : Updates an existing numberCheckboxQuestion.
     *
     * @param numberCheckboxQuestionDTO the numberCheckboxQuestionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated numberCheckboxQuestionDTO,
     * or with status {@code 400 (Bad Request)} if the numberCheckboxQuestionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the numberCheckboxQuestionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/number-checkbox-questions")
    public ResponseEntity<NumberCheckboxQuestionDTO> updateNumberCheckboxQuestion(@Valid @RequestBody NumberCheckboxQuestionDTO numberCheckboxQuestionDTO) throws URISyntaxException {
        log.debug("REST request to update NumberCheckboxQuestion : {}", numberCheckboxQuestionDTO);
        if (numberCheckboxQuestionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NumberCheckboxQuestionDTO result = numberCheckboxQuestionService.save(numberCheckboxQuestionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, numberCheckboxQuestionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /number-checkbox-questions} : get all the numberCheckboxQuestions.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of numberCheckboxQuestions in body.
     */
    @GetMapping("/number-checkbox-questions")
    public ResponseEntity<List<NumberCheckboxQuestionDTO>> getAllNumberCheckboxQuestions(NumberCheckboxQuestionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get NumberCheckboxQuestions by criteria: {}", criteria);
        Page<NumberCheckboxQuestionDTO> page = numberCheckboxQuestionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /number-checkbox-questions/count} : count all the numberCheckboxQuestions.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/number-checkbox-questions/count")
    public ResponseEntity<Long> countNumberCheckboxQuestions(NumberCheckboxQuestionCriteria criteria) {
        log.debug("REST request to count NumberCheckboxQuestions by criteria: {}", criteria);
        return ResponseEntity.ok().body(numberCheckboxQuestionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /number-checkbox-questions/:id} : get the "id" numberCheckboxQuestion.
     *
     * @param id the id of the numberCheckboxQuestionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the numberCheckboxQuestionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/number-checkbox-questions/{id}")
    public ResponseEntity<NumberCheckboxQuestionDTO> getNumberCheckboxQuestion(@PathVariable Long id) {
        log.debug("REST request to get NumberCheckboxQuestion : {}", id);
        Optional<NumberCheckboxQuestionDTO> numberCheckboxQuestionDTO = numberCheckboxQuestionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(numberCheckboxQuestionDTO);
    }

    /**
     * {@code DELETE  /number-checkbox-questions/:id} : delete the "id" numberCheckboxQuestion.
     *
     * @param id the id of the numberCheckboxQuestionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/number-checkbox-questions/{id}")
    public ResponseEntity<Void> deleteNumberCheckboxQuestion(@PathVariable Long id) {
        log.debug("REST request to delete NumberCheckboxQuestion : {}", id);
        numberCheckboxQuestionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
