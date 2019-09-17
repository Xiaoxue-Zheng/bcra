package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.RepeatAnswerService;
import uk.ac.herc.bcra.domain.RepeatAnswer;
import uk.ac.herc.bcra.repository.RepeatAnswerRepository;
import uk.ac.herc.bcra.service.dto.RepeatAnswerDTO;
import uk.ac.herc.bcra.service.mapper.RepeatAnswerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RepeatAnswer}.
 */
@Service
@Transactional
public class RepeatAnswerServiceImpl implements RepeatAnswerService {

    private final Logger log = LoggerFactory.getLogger(RepeatAnswerServiceImpl.class);

    private final RepeatAnswerRepository repeatAnswerRepository;

    private final RepeatAnswerMapper repeatAnswerMapper;

    public RepeatAnswerServiceImpl(RepeatAnswerRepository repeatAnswerRepository, RepeatAnswerMapper repeatAnswerMapper) {
        this.repeatAnswerRepository = repeatAnswerRepository;
        this.repeatAnswerMapper = repeatAnswerMapper;
    }

    /**
     * Save a repeatAnswer.
     *
     * @param repeatAnswerDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RepeatAnswerDTO save(RepeatAnswerDTO repeatAnswerDTO) {
        log.debug("Request to save RepeatAnswer : {}", repeatAnswerDTO);
        RepeatAnswer repeatAnswer = repeatAnswerMapper.toEntity(repeatAnswerDTO);
        repeatAnswer = repeatAnswerRepository.save(repeatAnswer);
        return repeatAnswerMapper.toDto(repeatAnswer);
    }

    /**
     * Get all the repeatAnswers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RepeatAnswerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RepeatAnswers");
        return repeatAnswerRepository.findAll(pageable)
            .map(repeatAnswerMapper::toDto);
    }


    /**
     * Get one repeatAnswer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RepeatAnswerDTO> findOne(Long id) {
        log.debug("Request to get RepeatAnswer : {}", id);
        return repeatAnswerRepository.findById(id)
            .map(repeatAnswerMapper::toDto);
    }

    /**
     * Delete the repeatAnswer by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RepeatAnswer : {}", id);
        repeatAnswerRepository.deleteById(id);
    }
}
