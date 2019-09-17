package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.QuestionGroupService;
import uk.ac.herc.bcra.domain.QuestionGroup;
import uk.ac.herc.bcra.repository.QuestionGroupRepository;
import uk.ac.herc.bcra.service.dto.QuestionGroupDTO;
import uk.ac.herc.bcra.service.mapper.QuestionGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link QuestionGroup}.
 */
@Service
@Transactional
public class QuestionGroupServiceImpl implements QuestionGroupService {

    private final Logger log = LoggerFactory.getLogger(QuestionGroupServiceImpl.class);

    private final QuestionGroupRepository questionGroupRepository;

    private final QuestionGroupMapper questionGroupMapper;

    public QuestionGroupServiceImpl(QuestionGroupRepository questionGroupRepository, QuestionGroupMapper questionGroupMapper) {
        this.questionGroupRepository = questionGroupRepository;
        this.questionGroupMapper = questionGroupMapper;
    }

    /**
     * Save a questionGroup.
     *
     * @param questionGroupDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public QuestionGroupDTO save(QuestionGroupDTO questionGroupDTO) {
        log.debug("Request to save QuestionGroup : {}", questionGroupDTO);
        QuestionGroup questionGroup = questionGroupMapper.toEntity(questionGroupDTO);
        questionGroup = questionGroupRepository.save(questionGroup);
        return questionGroupMapper.toDto(questionGroup);
    }

    /**
     * Get all the questionGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<QuestionGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all QuestionGroups");
        return questionGroupRepository.findAll(pageable)
            .map(questionGroupMapper::toDto);
    }


    /**
     * Get one questionGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionGroupDTO> findOne(Long id) {
        log.debug("Request to get QuestionGroup : {}", id);
        return questionGroupRepository.findById(id)
            .map(questionGroupMapper::toDto);
    }

    /**
     * Delete the questionGroup by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuestionGroup : {}", id);
        questionGroupRepository.deleteById(id);
    }
}
