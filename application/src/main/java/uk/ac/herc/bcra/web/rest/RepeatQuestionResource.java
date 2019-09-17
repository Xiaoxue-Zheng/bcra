package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.RepeatQuestionService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.RepeatQuestionDTO;
import uk.ac.herc.bcra.service.dto.RepeatQuestionCriteria;
import uk.ac.herc.bcra.service.RepeatQuestionQueryService;

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
 * REST controller for managing {@link uk.ac.herc.bcra.domain.RepeatQuestion}.
 */
@RestController
@RequestMapping("/api")
public class RepeatQuestionResource {

    private final Logger log = LoggerFactory.getLogger(RepeatQuestionResource.class);

    private static final String ENTITY_NAME = "repeatQuestion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RepeatQuestionService repeatQuestionService;

    private final RepeatQuestionQueryService repeatQuestionQueryService;

    public RepeatQuestionResource(RepeatQuestionService repeatQuestionService, RepeatQuestionQueryService repeatQuestionQueryService) {
        this.repeatQuestionService = repeatQuestionService;
        this.repeatQuestionQueryService = repeatQuestionQueryService;
    }

    /**
     * {@code POST  /repeat-questions} : Create a new repeatQuestion.
     *
     * @param repeatQuestionDTO the repeatQuestionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new repeatQuestionDTO, or with status {@code 400 (Bad Request)} if the repeatQuestion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/repeat-questions")
    public ResponseEntity<RepeatQuestionDTO> createRepeatQuestion(@Valid @RequestBody RepeatQuestionDTO repeatQuestionDTO) throws URISyntaxException {
        log.debug("REST request to save RepeatQuestion : {}", repeatQuestionDTO);
        if (repeatQuestionDTO.getId() != null) {
            throw new BadRequestAlertException("A new repeatQuestion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RepeatQuestionDTO result = repeatQuestionService.save(repeatQuestionDTO);
        return ResponseEntity.created(new URI("/api/repeat-questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /repeat-questions} : Updates an existing repeatQuestion.
     *
     * @param repeatQuestionDTO the repeatQuestionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated repeatQuestionDTO,
     * or with status {@code 400 (Bad Request)} if the repeatQuestionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the repeatQuestionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/repeat-questions")
    public ResponseEntity<RepeatQuestionDTO> updateRepeatQuestion(@Valid @RequestBody RepeatQuestionDTO repeatQuestionDTO) throws URISyntaxException {
        log.debug("REST request to update RepeatQuestion : {}", repeatQuestionDTO);
        if (repeatQuestionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RepeatQuestionDTO result = repeatQuestionService.save(repeatQuestionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, repeatQuestionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /repeat-questions} : get all the repeatQuestions.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of repeatQuestions in body.
     */
    @GetMapping("/repeat-questions")
    public ResponseEntity<List<RepeatQuestionDTO>> getAllRepeatQuestions(RepeatQuestionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RepeatQuestions by criteria: {}", criteria);
        Page<RepeatQuestionDTO> page = repeatQuestionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /repeat-questions/count} : count all the repeatQuestions.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/repeat-questions/count")
    public ResponseEntity<Long> countRepeatQuestions(RepeatQuestionCriteria criteria) {
        log.debug("REST request to count RepeatQuestions by criteria: {}", criteria);
        return ResponseEntity.ok().body(repeatQuestionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /repeat-questions/:id} : get the "id" repeatQuestion.
     *
     * @param id the id of the repeatQuestionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the repeatQuestionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/repeat-questions/{id}")
    public ResponseEntity<RepeatQuestionDTO> getRepeatQuestion(@PathVariable Long id) {
        log.debug("REST request to get RepeatQuestion : {}", id);
        Optional<RepeatQuestionDTO> repeatQuestionDTO = repeatQuestionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(repeatQuestionDTO);
    }

    /**
     * {@code DELETE  /repeat-questions/:id} : delete the "id" repeatQuestion.
     *
     * @param id the id of the repeatQuestionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/repeat-questions/{id}")
    public ResponseEntity<Void> deleteRepeatQuestion(@PathVariable Long id) {
        log.debug("REST request to delete RepeatQuestion : {}", id);
        repeatQuestionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
