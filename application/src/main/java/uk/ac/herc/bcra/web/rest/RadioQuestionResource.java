package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.RadioQuestionService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.RadioQuestionDTO;
import uk.ac.herc.bcra.service.dto.RadioQuestionCriteria;
import uk.ac.herc.bcra.service.RadioQuestionQueryService;

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
 * REST controller for managing {@link uk.ac.herc.bcra.domain.RadioQuestion}.
 */
@RestController
@RequestMapping("/api")
public class RadioQuestionResource {

    private final Logger log = LoggerFactory.getLogger(RadioQuestionResource.class);

    private static final String ENTITY_NAME = "radioQuestion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RadioQuestionService radioQuestionService;

    private final RadioQuestionQueryService radioQuestionQueryService;

    public RadioQuestionResource(RadioQuestionService radioQuestionService, RadioQuestionQueryService radioQuestionQueryService) {
        this.radioQuestionService = radioQuestionService;
        this.radioQuestionQueryService = radioQuestionQueryService;
    }

    /**
     * {@code POST  /radio-questions} : Create a new radioQuestion.
     *
     * @param radioQuestionDTO the radioQuestionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new radioQuestionDTO, or with status {@code 400 (Bad Request)} if the radioQuestion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/radio-questions")
    public ResponseEntity<RadioQuestionDTO> createRadioQuestion(@RequestBody RadioQuestionDTO radioQuestionDTO) throws URISyntaxException {
        log.debug("REST request to save RadioQuestion : {}", radioQuestionDTO);
        if (radioQuestionDTO.getId() != null) {
            throw new BadRequestAlertException("A new radioQuestion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RadioQuestionDTO result = radioQuestionService.save(radioQuestionDTO);
        return ResponseEntity.created(new URI("/api/radio-questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /radio-questions} : Updates an existing radioQuestion.
     *
     * @param radioQuestionDTO the radioQuestionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated radioQuestionDTO,
     * or with status {@code 400 (Bad Request)} if the radioQuestionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the radioQuestionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/radio-questions")
    public ResponseEntity<RadioQuestionDTO> updateRadioQuestion(@RequestBody RadioQuestionDTO radioQuestionDTO) throws URISyntaxException {
        log.debug("REST request to update RadioQuestion : {}", radioQuestionDTO);
        if (radioQuestionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RadioQuestionDTO result = radioQuestionService.save(radioQuestionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, radioQuestionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /radio-questions} : get all the radioQuestions.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of radioQuestions in body.
     */
    @GetMapping("/radio-questions")
    public ResponseEntity<List<RadioQuestionDTO>> getAllRadioQuestions(RadioQuestionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RadioQuestions by criteria: {}", criteria);
        Page<RadioQuestionDTO> page = radioQuestionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /radio-questions/count} : count all the radioQuestions.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/radio-questions/count")
    public ResponseEntity<Long> countRadioQuestions(RadioQuestionCriteria criteria) {
        log.debug("REST request to count RadioQuestions by criteria: {}", criteria);
        return ResponseEntity.ok().body(radioQuestionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /radio-questions/:id} : get the "id" radioQuestion.
     *
     * @param id the id of the radioQuestionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the radioQuestionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/radio-questions/{id}")
    public ResponseEntity<RadioQuestionDTO> getRadioQuestion(@PathVariable Long id) {
        log.debug("REST request to get RadioQuestion : {}", id);
        Optional<RadioQuestionDTO> radioQuestionDTO = radioQuestionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(radioQuestionDTO);
    }

    /**
     * {@code DELETE  /radio-questions/:id} : delete the "id" radioQuestion.
     *
     * @param id the id of the radioQuestionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/radio-questions/{id}")
    public ResponseEntity<Void> deleteRadioQuestion(@PathVariable Long id) {
        log.debug("REST request to delete RadioQuestion : {}", id);
        radioQuestionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
