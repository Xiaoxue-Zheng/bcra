package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.DisplayConditionService;
import uk.ac.herc.bcra.domain.DisplayCondition;
import uk.ac.herc.bcra.repository.DisplayConditionRepository;
import uk.ac.herc.bcra.service.dto.DisplayConditionDTO;
import uk.ac.herc.bcra.service.mapper.DisplayConditionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DisplayCondition}.
 */
@Service
@Transactional
public class DisplayConditionServiceImpl implements DisplayConditionService {

    private final Logger log = LoggerFactory.getLogger(DisplayConditionServiceImpl.class);

    private final DisplayConditionRepository displayConditionRepository;

    private final DisplayConditionMapper displayConditionMapper;

    public DisplayConditionServiceImpl(DisplayConditionRepository displayConditionRepository, DisplayConditionMapper displayConditionMapper) {
        this.displayConditionRepository = displayConditionRepository;
        this.displayConditionMapper = displayConditionMapper;
    }

    /**
     * Save a displayCondition.
     *
     * @param displayConditionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DisplayConditionDTO save(DisplayConditionDTO displayConditionDTO) {
        log.debug("Request to save DisplayCondition : {}", displayConditionDTO);
        DisplayCondition displayCondition = displayConditionMapper.toEntity(displayConditionDTO);
        displayCondition = displayConditionRepository.save(displayCondition);
        return displayConditionMapper.toDto(displayCondition);
    }

    /**
     * Get all the displayConditions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DisplayConditionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DisplayConditions");
        return displayConditionRepository.findAll(pageable)
            .map(displayConditionMapper::toDto);
    }


    /**
     * Get one displayCondition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DisplayConditionDTO> findOne(Long id) {
        log.debug("Request to get DisplayCondition : {}", id);
        return displayConditionRepository.findById(id)
            .map(displayConditionMapper::toDto);
    }

    /**
     * Delete the displayCondition by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DisplayCondition : {}", id);
        displayConditionRepository.deleteById(id);
    }
}
