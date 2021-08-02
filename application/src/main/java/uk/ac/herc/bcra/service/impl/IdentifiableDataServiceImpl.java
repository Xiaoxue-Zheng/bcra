package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.IdentifiableDataService;
import uk.ac.herc.bcra.domain.IdentifiableData;
import uk.ac.herc.bcra.repository.IdentifiableDataRepository;
import uk.ac.herc.bcra.service.dto.IdentifiableDataDTO;
import uk.ac.herc.bcra.service.mapper.IdentifiableDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link IdentifiableData}.
 */
@Service
@Transactional
public class IdentifiableDataServiceImpl implements IdentifiableDataService {

    private final Logger log = LoggerFactory.getLogger(IdentifiableDataServiceImpl.class);

    private final IdentifiableDataRepository identifiableDataRepository;

    private final IdentifiableDataMapper identifiableDataMapper;

    public IdentifiableDataServiceImpl(IdentifiableDataRepository identifiableDataRepository, IdentifiableDataMapper identifiableDataMapper) {
        this.identifiableDataRepository = identifiableDataRepository;
        this.identifiableDataMapper = identifiableDataMapper;
    }

    /**
     * Save a identifiableData.
     *
     * @param identifiableDataDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public IdentifiableDataDTO save(IdentifiableDataDTO identifiableDataDTO) {
        log.debug("Request to save IdentifiableData : {}", identifiableDataDTO);
        IdentifiableData identifiableData = identifiableDataMapper.toEntity(identifiableDataDTO);
        identifiableData = identifiableDataRepository.save(identifiableData);
        return identifiableDataMapper.toDto(identifiableData);
    }

    /**
     * Get all the identifiableData.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<IdentifiableDataDTO> findAll() {
        log.debug("Request to get all IdentifiableData");
        return identifiableDataRepository.findAll().stream()
            .map(identifiableDataMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one identifiableData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<IdentifiableDataDTO> findOne(Long id) {
        log.debug("Request to get IdentifiableData : {}", id);
        return identifiableDataRepository.findById(id)
            .map(identifiableDataMapper::toDto);
    }

    /**
     * Delete the identifiableData by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete IdentifiableData : {}", id);
        identifiableDataRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IdentifiableData> findOne(String emailAddress) {
        IdentifiableData exampleIdentifiableData = new IdentifiableData();
        exampleIdentifiableData.setEmail(emailAddress);

        Example<IdentifiableData> example = Example.of(exampleIdentifiableData);
        return identifiableDataRepository.findOne(example);
    }
}
