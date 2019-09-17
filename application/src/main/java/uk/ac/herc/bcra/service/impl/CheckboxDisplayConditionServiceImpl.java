package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.CheckboxDisplayConditionService;
import uk.ac.herc.bcra.domain.CheckboxDisplayCondition;
import uk.ac.herc.bcra.repository.CheckboxDisplayConditionRepository;
import uk.ac.herc.bcra.service.dto.CheckboxDisplayConditionDTO;
import uk.ac.herc.bcra.service.mapper.CheckboxDisplayConditionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CheckboxDisplayCondition}.
 */
@Service
@Transactional
public class CheckboxDisplayConditionServiceImpl implements CheckboxDisplayConditionService {

    private final Logger log = LoggerFactory.getLogger(CheckboxDisplayConditionServiceImpl.class);

    private final CheckboxDisplayConditionRepository checkboxDisplayConditionRepository;

    private final CheckboxDisplayConditionMapper checkboxDisplayConditionMapper;

    public CheckboxDisplayConditionServiceImpl(CheckboxDisplayConditionRepository checkboxDisplayConditionRepository, CheckboxDisplayConditionMapper checkboxDisplayConditionMapper) {
        this.checkboxDisplayConditionRepository = checkboxDisplayConditionRepository;
        this.checkboxDisplayConditionMapper = checkboxDisplayConditionMapper;
    }

    /**
     * Save a checkboxDisplayCondition.
     *
     * @param checkboxDisplayConditionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CheckboxDisplayConditionDTO save(CheckboxDisplayConditionDTO checkboxDisplayConditionDTO) {
        log.debug("Request to save CheckboxDisplayCondition : {}", checkboxDisplayConditionDTO);
        CheckboxDisplayCondition checkboxDisplayCondition = checkboxDisplayConditionMapper.toEntity(checkboxDisplayConditionDTO);
        checkboxDisplayCondition = checkboxDisplayConditionRepository.save(checkboxDisplayCondition);
        return checkboxDisplayConditionMapper.toDto(checkboxDisplayCondition);
    }

    /**
     * Get all the checkboxDisplayConditions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CheckboxDisplayConditionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CheckboxDisplayConditions");
        return checkboxDisplayConditionRepository.findAll(pageable)
            .map(checkboxDisplayConditionMapper::toDto);
    }


    /**
     * Get one checkboxDisplayCondition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CheckboxDisplayConditionDTO> findOne(Long id) {
        log.debug("Request to get CheckboxDisplayCondition : {}", id);
        return checkboxDisplayConditionRepository.findById(id)
            .map(checkboxDisplayConditionMapper::toDto);
    }

    /**
     * Delete the checkboxDisplayCondition by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CheckboxDisplayCondition : {}", id);
        checkboxDisplayConditionRepository.deleteById(id);
    }
}
