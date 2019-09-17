package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.QuestionnaireQuestionGroupService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.QuestionnaireQuestionGroupDTO;
import uk.ac.herc.bcra.service.dto.QuestionnaireQuestionGroupCriteria;
import uk.ac.herc.bcra.service.QuestionnaireQuestionGroupQueryService;

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
 * REST controller for managing {@link uk.ac.herc.bcra.domain.QuestionnaireQuestionGroup}.
 */
@RestController
@RequestMapping("/api")
public class QuestionnaireQuestionGroupResource {

    private final Logger log = LoggerFactory.getLogger(QuestionnaireQuestionGroupResource.class);

    private static final String ENTITY_NAME = "questionnaireQuestionGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionnaireQuestionGroupService questionnaireQuestionGroupService;

    private final QuestionnaireQuestionGroupQueryService questionnaireQuestionGroupQueryService;

    public QuestionnaireQuestionGroupResource(QuestionnaireQuestionGroupService questionnaireQuestionGroupService, QuestionnaireQuestionGroupQueryService questionnaireQuestionGroupQueryService) {
        this.questionnaireQuestionGroupService = questionnaireQuestionGroupService;
        this.questionnaireQuestionGroupQueryService = questionnaireQuestionGroupQueryService;
    }

    /**
     * {@code POST  /questionnaire-question-groups} : Create a new questionnaireQuestionGroup.
     *
     * @param questionnaireQuestionGroupDTO the questionnaireQuestionGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questionnaireQuestionGroupDTO, or with status {@code 400 (Bad Request)} if the questionnaireQuestionGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/questionnaire-question-groups")
    public ResponseEntity<QuestionnaireQuestionGroupDTO> createQuestionnaireQuestionGroup(@Valid @RequestBody QuestionnaireQuestionGroupDTO questionnaireQuestionGroupDTO) throws URISyntaxException {
        log.debug("REST request to save QuestionnaireQuestionGroup : {}", questionnaireQuestionGroupDTO);
        if (questionnaireQuestionGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new questionnaireQuestionGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionnaireQuestionGroupDTO result = questionnaireQuestionGroupService.save(questionnaireQuestionGroupDTO);
        return ResponseEntity.created(new URI("/api/questionnaire-question-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /questionnaire-question-groups} : Updates an existing questionnaireQuestionGroup.
     *
     * @param questionnaireQuestionGroupDTO the questionnaireQuestionGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionnaireQuestionGroupDTO,
     * or with status {@code 400 (Bad Request)} if the questionnaireQuestionGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questionnaireQuestionGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/questionnaire-question-groups")
    public ResponseEntity<QuestionnaireQuestionGroupDTO> updateQuestionnaireQuestionGroup(@Valid @RequestBody QuestionnaireQuestionGroupDTO questionnaireQuestionGroupDTO) throws URISyntaxException {
        log.debug("REST request to update QuestionnaireQuestionGroup : {}", questionnaireQuestionGroupDTO);
        if (questionnaireQuestionGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuestionnaireQuestionGroupDTO result = questionnaireQuestionGroupService.save(questionnaireQuestionGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, questionnaireQuestionGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /questionnaire-question-groups} : get all the questionnaireQuestionGroups.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questionnaireQuestionGroups in body.
     */
    @GetMapping("/questionnaire-question-groups")
    public ResponseEntity<List<QuestionnaireQuestionGroupDTO>> getAllQuestionnaireQuestionGroups(QuestionnaireQuestionGroupCriteria criteria, Pageable pageable) {
        log.debug("REST request to get QuestionnaireQuestionGroups by criteria: {}", criteria);
        Page<QuestionnaireQuestionGroupDTO> page = questionnaireQuestionGroupQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /questionnaire-question-groups/count} : count all the questionnaireQuestionGroups.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/questionnaire-question-groups/count")
    public ResponseEntity<Long> countQuestionnaireQuestionGroups(QuestionnaireQuestionGroupCriteria criteria) {
        log.debug("REST request to count QuestionnaireQuestionGroups by criteria: {}", criteria);
        return ResponseEntity.ok().body(questionnaireQuestionGroupQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /questionnaire-question-groups/:id} : get the "id" questionnaireQuestionGroup.
     *
     * @param id the id of the questionnaireQuestionGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questionnaireQuestionGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/questionnaire-question-groups/{id}")
    public ResponseEntity<QuestionnaireQuestionGroupDTO> getQuestionnaireQuestionGroup(@PathVariable Long id) {
        log.debug("REST request to get QuestionnaireQuestionGroup : {}", id);
        Optional<QuestionnaireQuestionGroupDTO> questionnaireQuestionGroupDTO = questionnaireQuestionGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionnaireQuestionGroupDTO);
    }

    /**
     * {@code DELETE  /questionnaire-question-groups/:id} : delete the "id" questionnaireQuestionGroup.
     *
     * @param id the id of the questionnaireQuestionGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/questionnaire-question-groups/{id}")
    public ResponseEntity<Void> deleteQuestionnaireQuestionGroup(@PathVariable Long id) {
        log.debug("REST request to delete QuestionnaireQuestionGroup : {}", id);
        questionnaireQuestionGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
