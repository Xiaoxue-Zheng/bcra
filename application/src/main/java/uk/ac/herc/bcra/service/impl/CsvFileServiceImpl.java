package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.CsvFileService;
import uk.ac.herc.bcra.domain.CsvFile;
import uk.ac.herc.bcra.domain.enumeration.CsvFileState;
import uk.ac.herc.bcra.repository.CsvFileRepository;
import uk.ac.herc.bcra.service.dto.CsvFileDTO;
import uk.ac.herc.bcra.service.dto.CsvFileUploadDTO;
import uk.ac.herc.bcra.service.mapper.CsvFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CsvFile}.
 */
@Service
@Transactional
public class CsvFileServiceImpl implements CsvFileService {

    private final Logger log = LoggerFactory.getLogger(CsvFileServiceImpl.class);

    private final CsvFileRepository csvFileRepository;

    private final CsvFileMapper csvFileMapper;

    public CsvFileServiceImpl(CsvFileRepository csvFileRepository, CsvFileMapper csvFileMapper) {
        this.csvFileRepository = csvFileRepository;
        this.csvFileMapper = csvFileMapper;
    }

    /**
     * Save a csvFile.
     *
     * @param csvFileDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CsvFileDTO save(CsvFileDTO csvFileDTO) {
        log.debug("Request to save CsvFile : {}", csvFileDTO);
        CsvFile csvFile = csvFileMapper.toEntity(csvFileDTO);
        csvFile = csvFileRepository.save(csvFile);
        return csvFileMapper.toDto(csvFile);
    }

    /**
     * Get all the csvFiles.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CsvFileDTO> findAll() {
        log.debug("Request to get all CsvFiles");
        return csvFileRepository.findAll().stream()
            .map(csvFileMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one csvFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CsvFileDTO> findOne(Long id) {
        log.debug("Request to get CsvFile : {}", id);
        return csvFileRepository.findById(id)
            .map(csvFileMapper::toDto);
    }

    /**
     * Delete the csvFile by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CsvFile : {}", id);
        csvFileRepository.deleteById(id);
    }

    public CsvFileUploadDTO storeCsvFile(MultipartFile file) {
        
        byte[] csvData;
        try {
            csvData = file.getBytes();
        }
        catch (Exception exception) {
            return CsvFileUploadDTO.UPLOAD_ERROR;
        }

        String fileName = file.getOriginalFilename();
        Optional<CsvFile> csvFileOptional = csvFileRepository.findOneByFileName(fileName);

        if (csvFileOptional.isPresent()) {
            CsvFile csvFile = csvFileOptional.get();

            if (csvFile.getState() == CsvFileState.INVALID) {
                csvFile.writeContent(csvData);
                csvFile.setUploadDatetime(Instant.now());
                csvFile.setState(CsvFileState.UPLOADED);
                csvFile.setStatus(null);
                csvFileRepository.save(csvFile);

                return CsvFileUploadDTO.UPDATED;
            }
            else {
                return CsvFileUploadDTO.ALREADY_EXISTS;
            }
        }
        else {
            CsvFile csvFile = new CsvFile();
            csvFile.setFileName(fileName);
            csvFile.writeContent(csvData);
            csvFile.setUploadDatetime(Instant.now());     
            csvFile.setState(CsvFileState.UPLOADED);
            csvFile.setStatus(null);
            csvFileRepository.save(csvFile);

            return CsvFileUploadDTO.CREATED;
        }
    }
}
