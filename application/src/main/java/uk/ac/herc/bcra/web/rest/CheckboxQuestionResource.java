package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.CheckboxQuestionService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.CheckboxQuestionDTO;
import uk.ac.herc.bcra.service.dto.CheckboxQuestionCriteria;
import uk.ac.herc.bcra.service.CheckboxQuestionQueryService;

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
 * REST controller for managing {@link uk.ac.herc.bcra.domain.CheckboxQuestion}.
 */
@RestController
@RequestMapping("/api")
public class CheckboxQuestionResource {

    private final Logger log = LoggerFactory.getLogger(CheckboxQuestionResource.class);

    private static final String ENTITY_NAME = "checkboxQuestion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CheckboxQuestionService checkboxQuestionService;

    private final CheckboxQuestionQueryService checkboxQuestionQueryService;

    public CheckboxQuestionResource(CheckboxQuestionService checkboxQuestionService, CheckboxQuestionQueryService checkboxQuestionQueryService) {
        this.checkboxQuestionService = checkboxQuestionService;
        this.checkboxQuestionQueryService = checkboxQuestionQueryService;
    }

    /**
     * {@code POST  /checkbox-questions} : Create a new checkboxQuestion.
     *
     * @param checkboxQuestionDTO the checkboxQuestionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checkboxQuestionDTO, or with status {@code 400 (Bad Request)} if the checkboxQuestion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/checkbox-questions")
    public ResponseEntity<CheckboxQuestionDTO> createCheckboxQuestion(@RequestBody CheckboxQuestionDTO checkboxQuestionDTO) throws URISyntaxException {
        log.debug("REST request to save CheckboxQuestion : {}", checkboxQuestionDTO);
        if (checkboxQuestionDTO.getId() != null) {
            throw new BadRequestAlertException("A new checkboxQuestion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CheckboxQuestionDTO result = checkboxQuestionService.save(checkboxQuestionDTO);
        return ResponseEntity.created(new URI("/api/checkbox-questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /checkbox-questions} : Updates an existing checkboxQuestion.
     *
     * @param checkboxQuestionDTO the checkboxQuestionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkboxQuestionDTO,
     * or with status {@code 400 (Bad Request)} if the checkboxQuestionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the checkboxQuestionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/checkbox-questions")
    public ResponseEntity<CheckboxQuestionDTO> updateCheckboxQuestion(@RequestBody CheckboxQuestionDTO checkboxQuestionDTO) throws URISyntaxException {
        log.debug("REST request to update CheckboxQuestion : {}", checkboxQuestionDTO);
        if (checkboxQuestionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CheckboxQuestionDTO result = checkboxQuestionService.save(checkboxQuestionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, checkboxQuestionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /checkbox-questions} : get all the checkboxQuestions.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkboxQuestions in body.
     */
    @GetMapping("/checkbox-questions")
    public ResponseEntity<List<CheckboxQuestionDTO>> getAllCheckboxQuestions(CheckboxQuestionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CheckboxQuestions by criteria: {}", criteria);
        Page<CheckboxQuestionDTO> page = checkboxQuestionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /checkbox-questions/count} : count all the checkboxQuestions.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/checkbox-questions/count")
    public ResponseEntity<Long> countCheckboxQuestions(CheckboxQuestionCriteria criteria) {
        log.debug("REST request to count CheckboxQuestions by criteria: {}", criteria);
        return ResponseEntity.ok().body(checkboxQuestionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /checkbox-questions/:id} : get the "id" checkboxQuestion.
     *
     * @param id the id of the checkboxQuestionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the checkboxQuestionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/checkbox-questions/{id}")
    public ResponseEntity<CheckboxQuestionDTO> getCheckboxQuestion(@PathVariable Long id) {
        log.debug("REST request to get CheckboxQuestion : {}", id);
        Optional<CheckboxQuestionDTO> checkboxQuestionDTO = checkboxQuestionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(checkboxQuestionDTO);
    }

    /**
     * {@code DELETE  /checkbox-questions/:id} : delete the "id" checkboxQuestion.
     *
     * @param id the id of the checkboxQuestionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/checkbox-questions/{id}")
    public ResponseEntity<Void> deleteCheckboxQuestion(@PathVariable Long id) {
        log.debug("REST request to delete CheckboxQuestion : {}", id);
        checkboxQuestionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
