package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.CheckboxAnswerService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.CheckboxAnswerDTO;
import uk.ac.herc.bcra.service.dto.CheckboxAnswerCriteria;
import uk.ac.herc.bcra.service.CheckboxAnswerQueryService;

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
 * REST controller for managing {@link uk.ac.herc.bcra.domain.CheckboxAnswer}.
 */
@RestController
@RequestMapping("/api")
public class CheckboxAnswerResource {

    private final Logger log = LoggerFactory.getLogger(CheckboxAnswerResource.class);

    private static final String ENTITY_NAME = "checkboxAnswer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CheckboxAnswerService checkboxAnswerService;

    private final CheckboxAnswerQueryService checkboxAnswerQueryService;

    public CheckboxAnswerResource(CheckboxAnswerService checkboxAnswerService, CheckboxAnswerQueryService checkboxAnswerQueryService) {
        this.checkboxAnswerService = checkboxAnswerService;
        this.checkboxAnswerQueryService = checkboxAnswerQueryService;
    }

    /**
     * {@code POST  /checkbox-answers} : Create a new checkboxAnswer.
     *
     * @param checkboxAnswerDTO the checkboxAnswerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checkboxAnswerDTO, or with status {@code 400 (Bad Request)} if the checkboxAnswer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/checkbox-answers")
    public ResponseEntity<CheckboxAnswerDTO> createCheckboxAnswer(@RequestBody CheckboxAnswerDTO checkboxAnswerDTO) throws URISyntaxException {
        log.debug("REST request to save CheckboxAnswer : {}", checkboxAnswerDTO);
        if (checkboxAnswerDTO.getId() != null) {
            throw new BadRequestAlertException("A new checkboxAnswer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CheckboxAnswerDTO result = checkboxAnswerService.save(checkboxAnswerDTO);
        return ResponseEntity.created(new URI("/api/checkbox-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /checkbox-answers} : Updates an existing checkboxAnswer.
     *
     * @param checkboxAnswerDTO the checkboxAnswerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkboxAnswerDTO,
     * or with status {@code 400 (Bad Request)} if the checkboxAnswerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the checkboxAnswerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/checkbox-answers")
    public ResponseEntity<CheckboxAnswerDTO> updateCheckboxAnswer(@RequestBody CheckboxAnswerDTO checkboxAnswerDTO) throws URISyntaxException {
        log.debug("REST request to update CheckboxAnswer : {}", checkboxAnswerDTO);
        if (checkboxAnswerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CheckboxAnswerDTO result = checkboxAnswerService.save(checkboxAnswerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, checkboxAnswerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /checkbox-answers} : get all the checkboxAnswers.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkboxAnswers in body.
     */
    @GetMapping("/checkbox-answers")
    public ResponseEntity<List<CheckboxAnswerDTO>> getAllCheckboxAnswers(CheckboxAnswerCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CheckboxAnswers by criteria: {}", criteria);
        Page<CheckboxAnswerDTO> page = checkboxAnswerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /checkbox-answers/count} : count all the checkboxAnswers.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/checkbox-answers/count")
    public ResponseEntity<Long> countCheckboxAnswers(CheckboxAnswerCriteria criteria) {
        log.debug("REST request to count CheckboxAnswers by criteria: {}", criteria);
        return ResponseEntity.ok().body(checkboxAnswerQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /checkbox-answers/:id} : get the "id" checkboxAnswer.
     *
     * @param id the id of the checkboxAnswerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the checkboxAnswerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/checkbox-answers/{id}")
    public ResponseEntity<CheckboxAnswerDTO> getCheckboxAnswer(@PathVariable Long id) {
        log.debug("REST request to get CheckboxAnswer : {}", id);
        Optional<CheckboxAnswerDTO> checkboxAnswerDTO = checkboxAnswerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(checkboxAnswerDTO);
    }

    /**
     * {@code DELETE  /checkbox-answers/:id} : delete the "id" checkboxAnswer.
     *
     * @param id the id of the checkboxAnswerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/checkbox-answers/{id}")
    public ResponseEntity<Void> deleteCheckboxAnswer(@PathVariable Long id) {
        log.debug("REST request to delete CheckboxAnswer : {}", id);
        checkboxAnswerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
