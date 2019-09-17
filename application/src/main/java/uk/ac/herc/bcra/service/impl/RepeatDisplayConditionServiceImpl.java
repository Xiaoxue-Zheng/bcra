package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.RepeatDisplayConditionService;
import uk.ac.herc.bcra.domain.RepeatDisplayCondition;
import uk.ac.herc.bcra.repository.RepeatDisplayConditionRepository;
import uk.ac.herc.bcra.service.dto.RepeatDisplayConditionDTO;
import uk.ac.herc.bcra.service.mapper.RepeatDisplayConditionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RepeatDisplayCondition}.
 */
@Service
@Transactional
public class RepeatDisplayConditionServiceImpl implements RepeatDisplayConditionService {

    private final Logger log = LoggerFactory.getLogger(RepeatDisplayConditionServiceImpl.class);

    private final RepeatDisplayConditionRepository repeatDisplayConditionRepository;

    private final RepeatDisplayConditionMapper repeatDisplayConditionMapper;

    public RepeatDisplayConditionServiceImpl(RepeatDisplayConditionRepository repeatDisplayConditionRepository, RepeatDisplayConditionMapper repeatDisplayConditionMapper) {
        this.repeatDisplayConditionRepository = repeatDisplayConditionRepository;
        this.repeatDisplayConditionMapper = repeatDisplayConditionMapper;
    }

    /**
     * Save a repeatDisplayCondition.
     *
     * @param repeatDisplayConditionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RepeatDisplayConditionDTO save(RepeatDisplayConditionDTO repeatDisplayConditionDTO) {
        log.debug("Request to save RepeatDisplayCondition : {}", repeatDisplayConditionDTO);
        RepeatDisplayCondition repeatDisplayCondition = repeatDisplayConditionMapper.toEntity(repeatDisplayConditionDTO);
        repeatDisplayCondition = repeatDisplayConditionRepository.save(repeatDisplayCondition);
        return repeatDisplayConditionMapper.toDto(repeatDisplayCondition);
    }

    /**
     * Get all the repeatDisplayConditions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RepeatDisplayConditionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RepeatDisplayConditions");
        return repeatDisplayConditionRepository.findAll(pageable)
            .map(repeatDisplayConditionMapper::toDto);
    }


    /**
     * Get one repeatDisplayCondition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RepeatDisplayConditionDTO> findOne(Long id) {
        log.debug("Request to get RepeatDisplayCondition : {}", id);
        return repeatDisplayConditionRepository.findById(id)
            .map(repeatDisplayConditionMapper::toDto);
    }

    /**
     * Delete the repeatDisplayCondition by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RepeatDisplayCondition : {}", id);
        repeatDisplayConditionRepository.deleteById(id);
    }
}
