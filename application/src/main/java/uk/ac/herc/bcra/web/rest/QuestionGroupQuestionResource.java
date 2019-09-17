package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.QuestionGroupQuestionService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.QuestionGroupQuestionDTO;
import uk.ac.herc.bcra.service.dto.QuestionGroupQuestionCriteria;
import uk.ac.herc.bcra.service.QuestionGroupQuestionQueryService;

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
 * REST controller for managing {@link uk.ac.herc.bcra.domain.QuestionGroupQuestion}.
 */
@RestController
@RequestMapping("/api")
public class QuestionGroupQuestionResource {

    private final Logger log = LoggerFactory.getLogger(QuestionGroupQuestionResource.class);

    private static final String ENTITY_NAME = "questionGroupQuestion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionGroupQuestionService questionGroupQuestionService;

    private final QuestionGroupQuestionQueryService questionGroupQuestionQueryService;

    public QuestionGroupQuestionResource(QuestionGroupQuestionService questionGroupQuestionService, QuestionGroupQuestionQueryService questionGroupQuestionQueryService) {
        this.questionGroupQuestionService = questionGroupQuestionService;
        this.questionGroupQuestionQueryService = questionGroupQuestionQueryService;
    }

    /**
     * {@code POST  /question-group-questions} : Create a new questionGroupQuestion.
     *
     * @param questionGroupQuestionDTO the questionGroupQuestionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questionGroupQuestionDTO, or with status {@code 400 (Bad Request)} if the questionGroupQuestion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/question-group-questions")
    public ResponseEntity<QuestionGroupQuestionDTO> createQuestionGroupQuestion(@Valid @RequestBody QuestionGroupQuestionDTO questionGroupQuestionDTO) throws URISyntaxException {
        log.debug("REST request to save QuestionGroupQuestion : {}", questionGroupQuestionDTO);
        if (questionGroupQuestionDTO.getId() != null) {
            throw new BadRequestAlertException("A new questionGroupQuestion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionGroupQuestionDTO result = questionGroupQuestionService.save(questionGroupQuestionDTO);
        return ResponseEntity.created(new URI("/api/question-group-questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /question-group-questions} : Updates an existing questionGroupQuestion.
     *
     * @param questionGroupQuestionDTO the questionGroupQuestionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionGroupQuestionDTO,
     * or with status {@code 400 (Bad Request)} if the questionGroupQuestionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questionGroupQuestionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/question-group-questions")
    public ResponseEntity<QuestionGroupQuestionDTO> updateQuestionGroupQuestion(@Valid @RequestBody QuestionGroupQuestionDTO questionGroupQuestionDTO) throws URISyntaxException {
        log.debug("REST request to update QuestionGroupQuestion : {}", questionGroupQuestionDTO);
        if (questionGroupQuestionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuestionGroupQuestionDTO result = questionGroupQuestionService.save(questionGroupQuestionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, questionGroupQuestionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /question-group-questions} : get all the questionGroupQuestions.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questionGroupQuestions in body.
     */
    @GetMapping("/question-group-questions")
    public ResponseEntity<List<QuestionGroupQuestionDTO>> getAllQuestionGroupQuestions(QuestionGroupQuestionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get QuestionGroupQuestions by criteria: {}", criteria);
        Page<QuestionGroupQuestionDTO> page = questionGroupQuestionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /question-group-questions/count} : count all the questionGroupQuestions.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/question-group-questions/count")
    public ResponseEntity<Long> countQuestionGroupQuestions(QuestionGroupQuestionCriteria criteria) {
        log.debug("REST request to count QuestionGroupQuestions by criteria: {}", criteria);
        return ResponseEntity.ok().body(questionGroupQuestionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /question-group-questions/:id} : get the "id" questionGroupQuestion.
     *
     * @param id the id of the questionGroupQuestionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questionGroupQuestionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/question-group-questions/{id}")
    public ResponseEntity<QuestionGroupQuestionDTO> getQuestionGroupQuestion(@PathVariable Long id) {
        log.debug("REST request to get QuestionGroupQuestion : {}", id);
        Optional<QuestionGroupQuestionDTO> questionGroupQuestionDTO = questionGroupQuestionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionGroupQuestionDTO);
    }

    /**
     * {@code DELETE  /question-group-questions/:id} : delete the "id" questionGroupQuestion.
     *
     * @param id the id of the questionGroupQuestionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/question-group-questions/{id}")
    public ResponseEntity<Void> deleteQuestionGroupQuestion(@PathVariable Long id) {
        log.debug("REST request to delete QuestionGroupQuestion : {}", id);
        questionGroupQuestionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
