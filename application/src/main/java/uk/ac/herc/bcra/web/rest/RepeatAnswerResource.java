package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.RepeatAnswerService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.RepeatAnswerDTO;
import uk.ac.herc.bcra.service.dto.RepeatAnswerCriteria;
import uk.ac.herc.bcra.service.RepeatAnswerQueryService;

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
 * REST controller for managing {@link uk.ac.herc.bcra.domain.RepeatAnswer}.
 */
@RestController
@RequestMapping("/api")
public class RepeatAnswerResource {

    private final Logger log = LoggerFactory.getLogger(RepeatAnswerResource.class);

    private static final String ENTITY_NAME = "repeatAnswer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RepeatAnswerService repeatAnswerService;

    private final RepeatAnswerQueryService repeatAnswerQueryService;

    public RepeatAnswerResource(RepeatAnswerService repeatAnswerService, RepeatAnswerQueryService repeatAnswerQueryService) {
        this.repeatAnswerService = repeatAnswerService;
        this.repeatAnswerQueryService = repeatAnswerQueryService;
    }

    /**
     * {@code POST  /repeat-answers} : Create a new repeatAnswer.
     *
     * @param repeatAnswerDTO the repeatAnswerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new repeatAnswerDTO, or with status {@code 400 (Bad Request)} if the repeatAnswer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/repeat-answers")
    public ResponseEntity<RepeatAnswerDTO> createRepeatAnswer(@Valid @RequestBody RepeatAnswerDTO repeatAnswerDTO) throws URISyntaxException {
        log.debug("REST request to save RepeatAnswer : {}", repeatAnswerDTO);
        if (repeatAnswerDTO.getId() != null) {
            throw new BadRequestAlertException("A new repeatAnswer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RepeatAnswerDTO result = repeatAnswerService.save(repeatAnswerDTO);
        return ResponseEntity.created(new URI("/api/repeat-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /repeat-answers} : Updates an existing repeatAnswer.
     *
     * @param repeatAnswerDTO the repeatAnswerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated repeatAnswerDTO,
     * or with status {@code 400 (Bad Request)} if the repeatAnswerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the repeatAnswerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/repeat-answers")
    public ResponseEntity<RepeatAnswerDTO> updateRepeatAnswer(@Valid @RequestBody RepeatAnswerDTO repeatAnswerDTO) throws URISyntaxException {
        log.debug("REST request to update RepeatAnswer : {}", repeatAnswerDTO);
        if (repeatAnswerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RepeatAnswerDTO result = repeatAnswerService.save(repeatAnswerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, repeatAnswerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /repeat-answers} : get all the repeatAnswers.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of repeatAnswers in body.
     */
    @GetMapping("/repeat-answers")
    public ResponseEntity<List<RepeatAnswerDTO>> getAllRepeatAnswers(RepeatAnswerCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RepeatAnswers by criteria: {}", criteria);
        Page<RepeatAnswerDTO> page = repeatAnswerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /repeat-answers/count} : count all the repeatAnswers.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/repeat-answers/count")
    public ResponseEntity<Long> countRepeatAnswers(RepeatAnswerCriteria criteria) {
        log.debug("REST request to count RepeatAnswers by criteria: {}", criteria);
        return ResponseEntity.ok().body(repeatAnswerQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /repeat-answers/:id} : get the "id" repeatAnswer.
     *
     * @param id the id of the repeatAnswerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the repeatAnswerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/repeat-answers/{id}")
    public ResponseEntity<RepeatAnswerDTO> getRepeatAnswer(@PathVariable Long id) {
        log.debug("REST request to get RepeatAnswer : {}", id);
        Optional<RepeatAnswerDTO> repeatAnswerDTO = repeatAnswerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(repeatAnswerDTO);
    }

    /**
     * {@code DELETE  /repeat-answers/:id} : delete the "id" repeatAnswer.
     *
     * @param id the id of the repeatAnswerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/repeat-answers/{id}")
    public ResponseEntity<Void> deleteRepeatAnswer(@PathVariable Long id) {
        log.debug("REST request to delete RepeatAnswer : {}", id);
        repeatAnswerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
