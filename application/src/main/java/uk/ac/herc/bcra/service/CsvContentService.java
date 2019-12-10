package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.dto.CsvContentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link uk.ac.herc.bcra.domain.CsvContent}.
 */
public interface CsvContentService {

    /**
     * Save a csvContent.
     *
     * @param csvContentDTO the entity to save.
     * @return the persisted entity.
     */
    CsvContentDTO save(CsvContentDTO csvContentDTO);

    /**
     * Get all the csvContents.
     *
     * @return the list of entities.
     */
    List<CsvContentDTO> findAll();
    /**
     * Get all the CsvContentDTO where CsvFile is {@code null}.
     *
     * @return the list of entities.
     */
    List<CsvContentDTO> findAllWhereCsvFileIsNull();


    /**
     * Get the "id" csvContent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CsvContentDTO> findOne(Long id);

    /**
     * Delete the "id" csvContent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
