package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.RadioAnswerService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.RadioAnswerDTO;
import uk.ac.herc.bcra.service.dto.RadioAnswerCriteria;
import uk.ac.herc.bcra.service.RadioAnswerQueryService;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link uk.ac.herc.bcra.domain.RadioAnswer}.
 */
@RestController
@RequestMapping("/api")
public class RadioAnswerResource {

    private final Logger log = LoggerFactory.getLogger(RadioAnswerResource.class);

    private static final String ENTITY_NAME = "radioAnswer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RadioAnswerService radioAnswerService;

    private final RadioAnswerQueryService radioAnswerQueryService;

    public RadioAnswerResource(RadioAnswerService radioAnswerService, RadioAnswerQueryService radioAnswerQueryService) {
        this.radioAnswerService = radioAnswerService;
        this.radioAnswerQueryService = radioAnswerQueryService;
    }

    /**
     * {@code POST  /radio-answers} : Create a new radioAnswer.
     *
     * @param radioAnswerDTO the radioAnswerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new radioAnswerDTO, or with status {@code 400 (Bad Request)} if the radioAnswer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/radio-answers")
    public ResponseEntity<RadioAnswerDTO> createRadioAnswer(@RequestBody RadioAnswerDTO radioAnswerDTO) throws URISyntaxException {
        log.debug("REST request to save RadioAnswer : {}", radioAnswerDTO);
        if (radioAnswerDTO.getId() != null) {
            throw new BadRequestAlertException("A new radioAnswer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RadioAnswerDTO result = radioAnswerService.save(radioAnswerDTO);
        return ResponseEntity.created(new URI("/api/radio-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /radio-answers} : Updates an existing radioAnswer.
     *
     * @param radioAnswerDTO the radioAnswerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated radioAnswerDTO,
     * or with status {@code 400 (Bad Request)} if the radioAnswerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the radioAnswerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/radio-answers")
    public ResponseEntity<RadioAnswerDTO> updateRadioAnswer(@RequestBody RadioAnswerDTO radioAnswerDTO) throws URISyntaxException {
        log.debug("REST request to update RadioAnswer : {}", radioAnswerDTO);
        if (radioAnswerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RadioAnswerDTO result = radioAnswerService.save(radioAnswerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, radioAnswerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /radio-answers} : get all the radioAnswers.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of radioAnswers in body.
     */
    @GetMapping("/radio-answers")
    public ResponseEntity<List<RadioAnswerDTO>> getAllRadioAnswers(RadioAnswerCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RadioAnswers by criteria: {}", criteria);
        Page<RadioAnswerDTO> page = radioAnswerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /radio-answers/count} : count all the radioAnswers.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/radio-answers/count")
    public ResponseEntity<Long> countRadioAnswers(RadioAnswerCriteria criteria) {
        log.debug("REST request to count RadioAnswers by criteria: {}", criteria);
        return ResponseEntity.ok().body(radioAnswerQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /radio-answers/:id} : get the "id" radioAnswer.
     *
     * @param id the id of the radioAnswerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the radioAnswerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/radio-answers/{id}")
    public ResponseEntity<RadioAnswerDTO> getRadioAnswer(@PathVariable Long id) {
        log.debug("REST request to get RadioAnswer : {}", id);
        Optional<RadioAnswerDTO> radioAnswerDTO = radioAnswerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(radioAnswerDTO);
    }

    /**
     * {@code DELETE  /radio-answers/:id} : delete the "id" radioAnswer.
     *
     * @param id the id of the radioAnswerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/radio-answers/{id}")
    public ResponseEntity<Void> deleteRadioAnswer(@PathVariable Long id) {
        log.debug("REST request to delete RadioAnswer : {}", id);
        radioAnswerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
