package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.CsvFileDTO;
import uk.ac.herc.bcra.service.dto.CsvFileUploadDTO;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.CsvFile}.
 */
public interface CsvFileService {

    /**
     * Save a csvFile.
     *
     * @param csvFileDTO the entity to save.
     * @return the persisted entity.
     */
    CsvFileDTO save(CsvFileDTO csvFileDTO);

    /**
     * Get all the csvFiles.
     *
     * @return the list of entities.
     */
    List<CsvFileDTO> findAll();


    /**
     * Get the "id" csvFile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CsvFileDTO> findOne(Long id);

    /**
     * Delete the "id" csvFile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    CsvFileUploadDTO storeCsvFile(MultipartFile file);
}
