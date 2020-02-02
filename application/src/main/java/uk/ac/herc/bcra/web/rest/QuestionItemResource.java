package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.QuestionItemService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.QuestionItemDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link uk.ac.herc.bcra.domain.QuestionItem}.
 */
@RestController
@RequestMapping("/api")
public class QuestionItemResource {

    private final Logger log = LoggerFactory.getLogger(QuestionItemResource.class);

    private static final String ENTITY_NAME = "questionItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionItemService questionItemService;

    public QuestionItemResource(QuestionItemService questionItemService) {
        this.questionItemService = questionItemService;
    }

    /**
     * {@code POST  /question-items} : Create a new questionItem.
     *
     * @param questionItemDTO the questionItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questionItemDTO, or with status {@code 400 (Bad Request)} if the questionItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/question-items")
    public ResponseEntity<QuestionItemDTO> createQuestionItem(@Valid @RequestBody QuestionItemDTO questionItemDTO) throws URISyntaxException {
        log.debug("REST request to save QuestionItem : {}", questionItemDTO);
        if (questionItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new questionItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionItemDTO result = questionItemService.save(questionItemDTO);
        return ResponseEntity.created(new URI("/api/question-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /question-items} : Updates an existing questionItem.
     *
     * @param questionItemDTO the questionItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionItemDTO,
     * or with status {@code 400 (Bad Request)} if the questionItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questionItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/question-items")
    public ResponseEntity<QuestionItemDTO> updateQuestionItem(@Valid @RequestBody QuestionItemDTO questionItemDTO) throws URISyntaxException {
        log.debug("REST request to update QuestionItem : {}", questionItemDTO);
        if (questionItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuestionItemDTO result = questionItemService.save(questionItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, questionItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /question-items} : get all the questionItems.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questionItems in body.
     */
    @GetMapping("/question-items")
    public List<QuestionItemDTO> getAllQuestionItems() {
        log.debug("REST request to get all QuestionItems");
        return questionItemService.findAll();
    }

    /**
     * {@code GET  /question-items/:id} : get the "id" questionItem.
     *
     * @param id the id of the questionItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questionItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/question-items/{id}")
    public ResponseEntity<QuestionItemDTO> getQuestionItem(@PathVariable Long id) {
        log.debug("REST request to get QuestionItem : {}", id);
        Optional<QuestionItemDTO> questionItemDTO = questionItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionItemDTO);
    }

    /**
     * {@code DELETE  /question-items/:id} : delete the "id" questionItem.
     *
     * @param id the id of the questionItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/question-items/{id}")
    public ResponseEntity<Void> deleteQuestionItem(@PathVariable Long id) {
        log.debug("REST request to delete QuestionItem : {}", id);
        questionItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
