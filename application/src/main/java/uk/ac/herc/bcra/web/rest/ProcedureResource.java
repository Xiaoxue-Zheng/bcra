package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.ProcedureService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.ProcedureDTO;

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
 * REST controller for managing {@link uk.ac.herc.bcra.domain.Procedure}.
 */
@RestController
@RequestMapping("/api")
public class ProcedureResource {

    private final Logger log = LoggerFactory.getLogger(ProcedureResource.class);

    private static final String ENTITY_NAME = "procedure";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcedureService procedureService;

    public ProcedureResource(ProcedureService procedureService) {
        this.procedureService = procedureService;
    }

    /**
     * {@code POST  /procedures} : Create a new procedure.
     *
     * @param procedureDTO the procedureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new procedureDTO, or with status {@code 400 (Bad Request)} if the procedure has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/procedures")
    public ResponseEntity<ProcedureDTO> createProcedure(@Valid @RequestBody ProcedureDTO procedureDTO) throws URISyntaxException {
        log.debug("REST request to save Procedure : {}", procedureDTO);
        if (procedureDTO.getId() != null) {
            throw new BadRequestAlertException("A new procedure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcedureDTO result = procedureService.save(procedureDTO);
        return ResponseEntity.created(new URI("/api/procedures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /procedures} : Updates an existing procedure.
     *
     * @param procedureDTO the procedureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated procedureDTO,
     * or with status {@code 400 (Bad Request)} if the procedureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the procedureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/procedures")
    public ResponseEntity<ProcedureDTO> updateProcedure(@Valid @RequestBody ProcedureDTO procedureDTO) throws URISyntaxException {
        log.debug("REST request to update Procedure : {}", procedureDTO);
        if (procedureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProcedureDTO result = procedureService.save(procedureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, procedureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /procedures} : get all the procedures.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of procedures in body.
     */
    @GetMapping("/procedures")
    public List<ProcedureDTO> getAllProcedures(@RequestParam(required = false) String filter) {
        if ("participant-is-null".equals(filter)) {
            log.debug("REST request to get all Procedures where participant is null");
            return procedureService.findAllWhereParticipantIsNull();
        }
        log.debug("REST request to get all Procedures");
        return procedureService.findAll();
    }

    /**
     * {@code GET  /procedures/:id} : get the "id" procedure.
     *
     * @param id the id of the procedureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the procedureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/procedures/{id}")
    public ResponseEntity<ProcedureDTO> getProcedure(@PathVariable Long id) {
        log.debug("REST request to get Procedure : {}", id);
        Optional<ProcedureDTO> procedureDTO = procedureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(procedureDTO);
    }

    /**
     * {@code DELETE  /procedures/:id} : delete the "id" procedure.
     *
     * @param id the id of the procedureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/procedures/{id}")
    public ResponseEntity<Void> deleteProcedure(@PathVariable Long id) {
        log.debug("REST request to delete Procedure : {}", id);
        procedureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
