package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.AnswerGroupService;
import uk.ac.herc.bcra.domain.AnswerGroup;
import uk.ac.herc.bcra.repository.AnswerGroupRepository;
import uk.ac.herc.bcra.service.dto.AnswerGroupDTO;
import uk.ac.herc.bcra.service.mapper.AnswerGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AnswerGroup}.
 */
@Service
@Transactional
public class AnswerGroupServiceImpl implements AnswerGroupService {

    private final Logger log = LoggerFactory.getLogger(AnswerGroupServiceImpl.class);

    private final AnswerGroupRepository answerGroupRepository;

    private final AnswerGroupMapper answerGroupMapper;

    public AnswerGroupServiceImpl(AnswerGroupRepository answerGroupRepository, AnswerGroupMapper answerGroupMapper) {
        this.answerGroupRepository = answerGroupRepository;
        this.answerGroupMapper = answerGroupMapper;
    }

    /**
     * Save a answerGroup.
     *
     * @param answerGroupDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AnswerGroupDTO save(AnswerGroupDTO answerGroupDTO) {
        log.debug("Request to save AnswerGroup : {}", answerGroupDTO);
        AnswerGroup answerGroup = answerGroupMapper.toEntity(answerGroupDTO);
        answerGroup = answerGroupRepository.save(answerGroup);
        return answerGroupMapper.toDto(answerGroup);
    }

    /**
     * Get all the answerGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AnswerGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnswerGroups");
        return answerGroupRepository.findAll(pageable)
            .map(answerGroupMapper::toDto);
    }


    /**
     * Get one answerGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AnswerGroupDTO> findOne(Long id) {
        log.debug("Request to get AnswerGroup : {}", id);
        return answerGroupRepository.findById(id)
            .map(answerGroupMapper::toDto);
    }

    /**
     * Delete the answerGroup by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnswerGroup : {}", id);
        answerGroupRepository.deleteById(id);
    }
}
