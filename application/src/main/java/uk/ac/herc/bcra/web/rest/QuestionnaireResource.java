package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.QuestionnaireService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.QuestionnaireDTO;
import uk.ac.herc.bcra.service.dto.QuestionnaireCriteria;
import uk.ac.herc.bcra.service.QuestionnaireQueryService;

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
 * REST controller for managing {@link uk.ac.herc.bcra.domain.Questionnaire}.
 */
@RestController
@RequestMapping("/api")
public class QuestionnaireResource {

    private final Logger log = LoggerFactory.getLogger(QuestionnaireResource.class);

    private static final String ENTITY_NAME = "questionnaire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionnaireService questionnaireService;

    private final QuestionnaireQueryService questionnaireQueryService;

    public QuestionnaireResource(QuestionnaireService questionnaireService, QuestionnaireQueryService questionnaireQueryService) {
        this.questionnaireService = questionnaireService;
        this.questionnaireQueryService = questionnaireQueryService;
    }

    /**
     * {@code POST  /questionnaires} : Create a new questionnaire.
     *
     * @param questionnaireDTO the questionnaireDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questionnaireDTO, or with status {@code 400 (Bad Request)} if the questionnaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/questionnaires")
    public ResponseEntity<QuestionnaireDTO> createQuestionnaire(@Valid @RequestBody QuestionnaireDTO questionnaireDTO) throws URISyntaxException {
        log.debug("REST request to save Questionnaire : {}", questionnaireDTO);
        if (questionnaireDTO.getId() != null) {
            throw new BadRequestAlertException("A new questionnaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionnaireDTO result = questionnaireService.save(questionnaireDTO);
        return ResponseEntity.created(new URI("/api/questionnaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /questionnaires} : Updates an existing questionnaire.
     *
     * @param questionnaireDTO the questionnaireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionnaireDTO,
     * or with status {@code 400 (Bad Request)} if the questionnaireDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questionnaireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/questionnaires")
    public ResponseEntity<QuestionnaireDTO> updateQuestionnaire(@Valid @RequestBody QuestionnaireDTO questionnaireDTO) throws URISyntaxException {
        log.debug("REST request to update Questionnaire : {}", questionnaireDTO);
        if (questionnaireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuestionnaireDTO result = questionnaireService.save(questionnaireDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, questionnaireDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /questionnaires} : get all the questionnaires.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questionnaires in body.
     */
    @GetMapping("/questionnaires")
    public ResponseEntity<List<QuestionnaireDTO>> getAllQuestionnaires(QuestionnaireCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Questionnaires by criteria: {}", criteria);
        Page<QuestionnaireDTO> page = questionnaireQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /questionnaires/count} : count all the questionnaires.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/questionnaires/count")
    public ResponseEntity<Long> countQuestionnaires(QuestionnaireCriteria criteria) {
        log.debug("REST request to count Questionnaires by criteria: {}", criteria);
        return ResponseEntity.ok().body(questionnaireQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /questionnaires/:id} : get the "id" questionnaire.
     *
     * @param id the id of the questionnaireDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questionnaireDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/questionnaires/{id}")
    public ResponseEntity<QuestionnaireDTO> getQuestionnaire(@PathVariable Long id) {
        log.debug("REST request to get Questionnaire : {}", id);
        Optional<QuestionnaireDTO> questionnaireDTO = questionnaireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionnaireDTO);
    }

    /**
     * {@code DELETE  /questionnaires/:id} : delete the "id" questionnaire.
     *
     * @param id the id of the questionnaireDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/questionnaires/{id}")
    public ResponseEntity<Void> deleteQuestionnaire(@PathVariable Long id) {
        log.debug("REST request to delete Questionnaire : {}", id);
        questionnaireService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
