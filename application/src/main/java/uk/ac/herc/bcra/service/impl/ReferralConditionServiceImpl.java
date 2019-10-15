package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.ReferralConditionService;
import uk.ac.herc.bcra.domain.ReferralCondition;
import uk.ac.herc.bcra.repository.ReferralConditionRepository;
import uk.ac.herc.bcra.service.dto.ReferralConditionDTO;
import uk.ac.herc.bcra.service.mapper.ReferralConditionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ReferralCondition}.
 */
@Service
@Transactional
public class ReferralConditionServiceImpl implements ReferralConditionService {

    private final Logger log = LoggerFactory.getLogger(ReferralConditionServiceImpl.class);

    private final ReferralConditionRepository referralConditionRepository;

    private final ReferralConditionMapper referralConditionMapper;

    public ReferralConditionServiceImpl(ReferralConditionRepository referralConditionRepository, ReferralConditionMapper referralConditionMapper) {
        this.referralConditionRepository = referralConditionRepository;
        this.referralConditionMapper = referralConditionMapper;
    }

    /**
     * Save a referralCondition.
     *
     * @param referralConditionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ReferralConditionDTO save(ReferralConditionDTO referralConditionDTO) {
        log.debug("Request to save ReferralCondition : {}", referralConditionDTO);
        ReferralCondition referralCondition = referralConditionMapper.toEntity(referralConditionDTO);
        referralCondition = referralConditionRepository.save(referralCondition);
        return referralConditionMapper.toDto(referralCondition);
    }

    /**
     * Get all the referralConditions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReferralConditionDTO> findAll() {
        log.debug("Request to get all ReferralConditions");
        return referralConditionRepository.findAll().stream()
            .map(referralConditionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one referralCondition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ReferralConditionDTO> findOne(Long id) {
        log.debug("Request to get ReferralCondition : {}", id);
        return referralConditionRepository.findById(id)
            .map(referralConditionMapper::toDto);
    }

    /**
     * Delete the referralCondition by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReferralCondition : {}", id);
        referralConditionRepository.deleteById(id);
    }
}
