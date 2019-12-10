package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.CsvContentService;
import uk.ac.herc.bcra.domain.CsvContent;
import uk.ac.herc.bcra.repository.CsvContentRepository;
import uk.ac.herc.bcra.service.dto.CsvContentDTO;
import uk.ac.herc.bcra.service.mapper.CsvContentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link CsvContent}.
 */
@Service
@Transactional
public class CsvContentServiceImpl implements CsvContentService {

    private final Logger log = LoggerFactory.getLogger(CsvContentServiceImpl.class);

    private final CsvContentRepository csvContentRepository;

    private final CsvContentMapper csvContentMapper;

    public CsvContentServiceImpl(CsvContentRepository csvContentRepository, CsvContentMapper csvContentMapper) {
        this.csvContentRepository = csvContentRepository;
        this.csvContentMapper = csvContentMapper;
    }

    /**
     * Save a csvContent.
     *
     * @param csvContentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CsvContentDTO save(CsvContentDTO csvContentDTO) {
        log.debug("Request to save CsvContent : {}", csvContentDTO);
        CsvContent csvContent = csvContentMapper.toEntity(csvContentDTO);
        csvContent = csvContentRepository.save(csvContent);
        return csvContentMapper.toDto(csvContent);
    }

    /**
     * Get all the csvContents.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CsvContentDTO> findAll() {
        log.debug("Request to get all CsvContents");
        return csvContentRepository.findAll().stream()
            .map(csvContentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
    *  Get all the csvContents where CsvFile is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<CsvContentDTO> findAllWhereCsvFileIsNull() {
        log.debug("Request to get all csvContents where CsvFile is null");
        return StreamSupport
            .stream(csvContentRepository.findAll().spliterator(), false)
            .filter(csvContent -> csvContent.getCsvFile() == null)
            .map(csvContentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one csvContent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CsvContentDTO> findOne(Long id) {
        log.debug("Request to get CsvContent : {}", id);
        return csvContentRepository.findById(id)
            .map(csvContentMapper::toDto);
    }

    /**
     * Delete the csvContent by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CsvContent : {}", id);
        csvContentRepository.deleteById(id);
    }
}
