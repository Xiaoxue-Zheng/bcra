package uk.ac.herc.bcra.web.rest;

import org.springframework.security.access.annotation.Secured;
import uk.ac.herc.bcra.security.RoleManager;
import uk.ac.herc.bcra.service.IdentifiableDataService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.IdentifiableDataDTO;

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
 * REST controller for managing {@link uk.ac.herc.bcra.domain.IdentifiableData}.
 */
@RestController
@RequestMapping("/api")
@Secured({RoleManager.ADMIN})
public class IdentifiableDataResource {

    private final Logger log = LoggerFactory.getLogger(IdentifiableDataResource.class);

    private static final String ENTITY_NAME = "identifiableData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IdentifiableDataService identifiableDataService;

    public IdentifiableDataResource(IdentifiableDataService identifiableDataService) {
        this.identifiableDataService = identifiableDataService;
    }

    /**
     * {@code POST  /identifiable-data} : Create a new identifiableData.
     *
     * @param identifiableDataDTO the identifiableDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new identifiableDataDTO, or with status {@code 400 (Bad Request)} if the identifiableData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/identifiable-data")
    public ResponseEntity<IdentifiableDataDTO> createIdentifiableData(@Valid @RequestBody IdentifiableDataDTO identifiableDataDTO) throws URISyntaxException {
        log.debug("REST request to save IdentifiableData : {}", identifiableDataDTO);
        if (identifiableDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new identifiableData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IdentifiableDataDTO result = identifiableDataService.save(identifiableDataDTO);
        return ResponseEntity.created(new URI("/api/identifiable-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /identifiable-data} : Updates an existing identifiableData.
     *
     * @param identifiableDataDTO the identifiableDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated identifiableDataDTO,
     * or with status {@code 400 (Bad Request)} if the identifiableDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the identifiableDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/identifiable-data")
    public ResponseEntity<IdentifiableDataDTO> updateIdentifiableData(@Valid @RequestBody IdentifiableDataDTO identifiableDataDTO) throws URISyntaxException {
        log.debug("REST request to update IdentifiableData : {}", identifiableDataDTO);
        if (identifiableDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IdentifiableDataDTO result = identifiableDataService.save(identifiableDataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, identifiableDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /identifiable-data} : get all the identifiableData.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of identifiableData in body.
     */
    @GetMapping("/identifiable-data")
    public List<IdentifiableDataDTO> getAllIdentifiableData() {
        log.debug("REST request to get all IdentifiableData");
        return identifiableDataService.findAll();
    }

    /**
     * {@code GET  /identifiable-data/:id} : get the "id" identifiableData.
     *
     * @param id the id of the identifiableDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the identifiableDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/identifiable-data/{id}")
    public ResponseEntity<IdentifiableDataDTO> getIdentifiableData(@PathVariable Long id) {
        log.debug("REST request to get IdentifiableData : {}", id);
        Optional<IdentifiableDataDTO> identifiableDataDTO = identifiableDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(identifiableDataDTO);
    }

    /**
     * {@code DELETE  /identifiable-data/:id} : delete the "id" identifiableData.
     *
     * @param id the id of the identifiableDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/identifiable-data/{id}")
    public ResponseEntity<Void> deleteIdentifiableData(@PathVariable Long id) {
        log.debug("REST request to delete IdentifiableData : {}", id);
        identifiableDataService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
