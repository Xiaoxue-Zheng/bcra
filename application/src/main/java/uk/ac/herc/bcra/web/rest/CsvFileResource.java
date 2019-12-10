package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.service.CsvFileService;
import uk.ac.herc.bcra.web.rest.errors.BadRequestAlertException;
import uk.ac.herc.bcra.service.dto.CsvFileDTO;
import uk.ac.herc.bcra.service.dto.CsvFileUploadDTO;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link uk.ac.herc.bcra.domain.CsvFile}.
 */
@RestController
@RequestMapping("/api")
public class CsvFileResource {

    private final Logger log = LoggerFactory.getLogger(CsvFileResource.class);

    private static final String ENTITY_NAME = "csvFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CsvFileService csvFileService;
    
    public CsvFileResource( CsvFileService csvFileService) {
        this.csvFileService = csvFileService;
    }

    @PostMapping("/participant-csv")
    public CsvFileUploadDTO uploadCsv(@RequestParam("file") MultipartFile file) {
        return csvFileService.storeCsvFile(file);
    }

    /**
     * {@code POST  /csv-files} : Create a new csvFile.
     *
     * @param csvFileDTO the csvFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new csvFileDTO, or with status {@code 400 (Bad Request)} if the csvFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/csv-files")
    public ResponseEntity<CsvFileDTO> createCsvFile(@Valid @RequestBody CsvFileDTO csvFileDTO) throws URISyntaxException {
        log.debug("REST request to save CsvFile : {}", csvFileDTO);
        if (csvFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new csvFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CsvFileDTO result = csvFileService.save(csvFileDTO);
        return ResponseEntity.created(new URI("/api/csv-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /csv-files} : Updates an existing csvFile.
     *
     * @param csvFileDTO the csvFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated csvFileDTO,
     * or with status {@code 400 (Bad Request)} if the csvFileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the csvFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/csv-files")
    public ResponseEntity<CsvFileDTO> updateCsvFile(@Valid @RequestBody CsvFileDTO csvFileDTO) throws URISyntaxException {
        log.debug("REST request to update CsvFile : {}", csvFileDTO);
        if (csvFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CsvFileDTO result = csvFileService.save(csvFileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, csvFileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /csv-files} : get all the csvFiles.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of csvFiles in body.
     */
    @GetMapping("/csv-files")
    public List<CsvFileDTO> getAllCsvFiles() {
        log.debug("REST request to get all CsvFiles");
        return csvFileService.findAll();
    }

    /**
     * {@code GET  /csv-files/:id} : get the "id" csvFile.
     *
     * @param id the id of the csvFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the csvFileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/csv-files/{id}")
    public ResponseEntity<CsvFileDTO> getCsvFile(@PathVariable Long id) {
        log.debug("REST request to get CsvFile : {}", id);
        Optional<CsvFileDTO> csvFileDTO = csvFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(csvFileDTO);
    }

    /**
     * {@code DELETE  /csv-files/:id} : delete the "id" csvFile.
     *
     * @param id the id of the csvFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/csv-files/{id}")
    public ResponseEntity<Void> deleteCsvFile(@PathVariable Long id) {
        log.debug("REST request to delete CsvFile : {}", id);
        csvFileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
