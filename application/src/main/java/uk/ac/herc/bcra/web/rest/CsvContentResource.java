package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.CsvContentService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.CsvContentDTO;

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
 * REST controller for managing {@link uk.ac.herc.bcra.domain.CsvContent}.
 */
@RestController
@RequestMapping("/api")
public class CsvContentResource {

    private final Logger log = LoggerFactory.getLogger(CsvContentResource.class);

    private static final String ENTITY_NAME = "csvContent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CsvContentService csvContentService;

    public CsvContentResource(CsvContentService csvContentService) {
        this.csvContentService = csvContentService;
    }

    /**
     * {@code POST  /csv-contents} : Create a new csvContent.
     *
     * @param csvContentDTO the csvContentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new csvContentDTO, or with status {@code 400 (Bad Request)} if the csvContent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/csv-contents")
    public ResponseEntity<CsvContentDTO> createCsvContent(@Valid @RequestBody CsvContentDTO csvContentDTO) throws URISyntaxException {
        log.debug("REST request to save CsvContent : {}", csvContentDTO);
        if (csvContentDTO.getId() != null) {
            throw new BadRequestAlertException("A new csvContent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CsvContentDTO result = csvContentService.save(csvContentDTO);
        return ResponseEntity.created(new URI("/api/csv-contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /csv-contents} : Updates an existing csvContent.
     *
     * @param csvContentDTO the csvContentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated csvContentDTO,
     * or with status {@code 400 (Bad Request)} if the csvContentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the csvContentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/csv-contents")
    public ResponseEntity<CsvContentDTO> updateCsvContent(@Valid @RequestBody CsvContentDTO csvContentDTO) throws URISyntaxException {
        log.debug("REST request to update CsvContent : {}", csvContentDTO);
        if (csvContentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CsvContentDTO result = csvContentService.save(csvContentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, csvContentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /csv-contents} : get all the csvContents.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of csvContents in body.
     */
    @GetMapping("/csv-contents")
    public List<CsvContentDTO> getAllCsvContents(@RequestParam(required = false) String filter) {
        if ("csvfile-is-null".equals(filter)) {
            log.debug("REST request to get all CsvContents where csvFile is null");
            return csvContentService.findAllWhereCsvFileIsNull();
        }
        log.debug("REST request to get all CsvContents");
        return csvContentService.findAll();
    }

    /**
     * {@code GET  /csv-contents/:id} : get the "id" csvContent.
     *
     * @param id the id of the csvContentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the csvContentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/csv-contents/{id}")
    public ResponseEntity<CsvContentDTO> getCsvContent(@PathVariable Long id) {
        log.debug("REST request to get CsvContent : {}", id);
        Optional<CsvContentDTO> csvContentDTO = csvContentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(csvContentDTO);
    }

    /**
     * {@code DELETE  /csv-contents/:id} : delete the "id" csvContent.
     *
     * @param id the id of the csvContentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/csv-contents/{id}")
    public ResponseEntity<Void> deleteCsvContent(@PathVariable Long id) {
        log.debug("REST request to delete CsvContent : {}", id);
        csvContentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
