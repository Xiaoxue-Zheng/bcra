package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.QuestionGroupService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.QuestionGroupDTO;
import uk.ac.herc.bcra.service.dto.QuestionGroupCriteria;
import uk.ac.herc.bcra.service.QuestionGroupQueryService;

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
 * REST controller for managing {@link uk.ac.herc.bcra.domain.QuestionGroup}.
 */
@RestController
@RequestMapping("/api")
public class QuestionGroupResource {

    private final Logger log = LoggerFactory.getLogger(QuestionGroupResource.class);

    private static final String ENTITY_NAME = "questionGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionGroupService questionGroupService;

    private final QuestionGroupQueryService questionGroupQueryService;

    public QuestionGroupResource(QuestionGroupService questionGroupService, QuestionGroupQueryService questionGroupQueryService) {
        this.questionGroupService = questionGroupService;
        this.questionGroupQueryService = questionGroupQueryService;
    }

    /**
     * {@code POST  /question-groups} : Create a new questionGroup.
     *
     * @param questionGroupDTO the questionGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questionGroupDTO, or with status {@code 400 (Bad Request)} if the questionGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/question-groups")
    public ResponseEntity<QuestionGroupDTO> createQuestionGroup(@Valid @RequestBody QuestionGroupDTO questionGroupDTO) throws URISyntaxException {
        log.debug("REST request to save QuestionGroup : {}", questionGroupDTO);
        if (questionGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new questionGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionGroupDTO result = questionGroupService.save(questionGroupDTO);
        return ResponseEntity.created(new URI("/api/question-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /question-groups} : Updates an existing questionGroup.
     *
     * @param questionGroupDTO the questionGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionGroupDTO,
     * or with status {@code 400 (Bad Request)} if the questionGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questionGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/question-groups")
    public ResponseEntity<QuestionGroupDTO> updateQuestionGroup(@Valid @RequestBody QuestionGroupDTO questionGroupDTO) throws URISyntaxException {
        log.debug("REST request to update QuestionGroup : {}", questionGroupDTO);
        if (questionGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuestionGroupDTO result = questionGroupService.save(questionGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, questionGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /question-groups} : get all the questionGroups.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questionGroups in body.
     */
    @GetMapping("/question-groups")
    public ResponseEntity<List<QuestionGroupDTO>> getAllQuestionGroups(QuestionGroupCriteria criteria, Pageable pageable) {
        log.debug("REST request to get QuestionGroups by criteria: {}", criteria);
        Page<QuestionGroupDTO> page = questionGroupQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /question-groups/count} : count all the questionGroups.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/question-groups/count")
    public ResponseEntity<Long> countQuestionGroups(QuestionGroupCriteria criteria) {
        log.debug("REST request to count QuestionGroups by criteria: {}", criteria);
        return ResponseEntity.ok().body(questionGroupQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /question-groups/:id} : get the "id" questionGroup.
     *
     * @param id the id of the questionGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questionGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/question-groups/{id}")
    public ResponseEntity<QuestionGroupDTO> getQuestionGroup(@PathVariable Long id) {
        log.debug("REST request to get QuestionGroup : {}", id);
        Optional<QuestionGroupDTO> questionGroupDTO = questionGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionGroupDTO);
    }

    /**
     * {@code DELETE  /question-groups/:id} : delete the "id" questionGroup.
     *
     * @param id the id of the questionGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/question-groups/{id}")
    public ResponseEntity<Void> deleteQuestionGroup(@PathVariable Long id) {
        log.debug("REST request to delete QuestionGroup : {}", id);
        questionGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
