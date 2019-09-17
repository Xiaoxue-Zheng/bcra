package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.AnswerGroupService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.AnswerGroupDTO;
import uk.ac.herc.bcra.service.dto.AnswerGroupCriteria;
import uk.ac.herc.bcra.service.AnswerGroupQueryService;

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
 * REST controller for managing {@link uk.ac.herc.bcra.domain.AnswerGroup}.
 */
@RestController
@RequestMapping("/api")
public class AnswerGroupResource {

    private final Logger log = LoggerFactory.getLogger(AnswerGroupResource.class);

    private static final String ENTITY_NAME = "answerGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnswerGroupService answerGroupService;

    private final AnswerGroupQueryService answerGroupQueryService;

    public AnswerGroupResource(AnswerGroupService answerGroupService, AnswerGroupQueryService answerGroupQueryService) {
        this.answerGroupService = answerGroupService;
        this.answerGroupQueryService = answerGroupQueryService;
    }

    /**
     * {@code POST  /answer-groups} : Create a new answerGroup.
     *
     * @param answerGroupDTO the answerGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new answerGroupDTO, or with status {@code 400 (Bad Request)} if the answerGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/answer-groups")
    public ResponseEntity<AnswerGroupDTO> createAnswerGroup(@RequestBody AnswerGroupDTO answerGroupDTO) throws URISyntaxException {
        log.debug("REST request to save AnswerGroup : {}", answerGroupDTO);
        if (answerGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new answerGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnswerGroupDTO result = answerGroupService.save(answerGroupDTO);
        return ResponseEntity.created(new URI("/api/answer-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /answer-groups} : Updates an existing answerGroup.
     *
     * @param answerGroupDTO the answerGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated answerGroupDTO,
     * or with status {@code 400 (Bad Request)} if the answerGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the answerGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/answer-groups")
    public ResponseEntity<AnswerGroupDTO> updateAnswerGroup(@RequestBody AnswerGroupDTO answerGroupDTO) throws URISyntaxException {
        log.debug("REST request to update AnswerGroup : {}", answerGroupDTO);
        if (answerGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnswerGroupDTO result = answerGroupService.save(answerGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, answerGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /answer-groups} : get all the answerGroups.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of answerGroups in body.
     */
    @GetMapping("/answer-groups")
    public ResponseEntity<List<AnswerGroupDTO>> getAllAnswerGroups(AnswerGroupCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AnswerGroups by criteria: {}", criteria);
        Page<AnswerGroupDTO> page = answerGroupQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /answer-groups/count} : count all the answerGroups.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/answer-groups/count")
    public ResponseEntity<Long> countAnswerGroups(AnswerGroupCriteria criteria) {
        log.debug("REST request to count AnswerGroups by criteria: {}", criteria);
        return ResponseEntity.ok().body(answerGroupQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /answer-groups/:id} : get the "id" answerGroup.
     *
     * @param id the id of the answerGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the answerGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/answer-groups/{id}")
    public ResponseEntity<AnswerGroupDTO> getAnswerGroup(@PathVariable Long id) {
        log.debug("REST request to get AnswerGroup : {}", id);
        Optional<AnswerGroupDTO> answerGroupDTO = answerGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(answerGroupDTO);
    }

    /**
     * {@code DELETE  /answer-groups/:id} : delete the "id" answerGroup.
     *
     * @param id the id of the answerGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/answer-groups/{id}")
    public ResponseEntity<Void> deleteAnswerGroup(@PathVariable Long id) {
        log.debug("REST request to delete AnswerGroup : {}", id);
        answerGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
