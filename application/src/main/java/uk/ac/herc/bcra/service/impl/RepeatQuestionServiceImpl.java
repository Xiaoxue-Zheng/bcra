package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.RepeatQuestionService;
import uk.ac.herc.bcra.domain.RepeatQuestion;
import uk.ac.herc.bcra.repository.RepeatQuestionRepository;
import uk.ac.herc.bcra.service.dto.RepeatQuestionDTO;
import uk.ac.herc.bcra.service.mapper.RepeatQuestionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RepeatQuestion}.
 */
@Service
@Transactional
public class RepeatQuestionServiceImpl implements RepeatQuestionService {

    private final Logger log = LoggerFactory.getLogger(RepeatQuestionServiceImpl.class);

    private final RepeatQuestionRepository repeatQuestionRepository;

    private final RepeatQuestionMapper repeatQuestionMapper;

    public RepeatQuestionServiceImpl(RepeatQuestionRepository repeatQuestionRepository, RepeatQuestionMapper repeatQuestionMapper) {
        this.repeatQuestionRepository = repeatQuestionRepository;
        this.repeatQuestionMapper = repeatQuestionMapper;
    }

    /**
     * Save a repeatQuestion.
     *
     * @param repeatQuestionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RepeatQuestionDTO save(RepeatQuestionDTO repeatQuestionDTO) {
        log.debug("Request to save RepeatQuestion : {}", repeatQuestionDTO);
        RepeatQuestion repeatQuestion = repeatQuestionMapper.toEntity(repeatQuestionDTO);
        repeatQuestion = repeatQuestionRepository.save(repeatQuestion);
        return repeatQuestionMapper.toDto(repeatQuestion);
    }

    /**
     * Get all the repeatQuestions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RepeatQuestionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RepeatQuestions");
        return repeatQuestionRepository.findAll(pageable)
            .map(repeatQuestionMapper::toDto);
    }


    /**
     * Get one repeatQuestion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RepeatQuestionDTO> findOne(Long id) {
        log.debug("Request to get RepeatQuestion : {}", id);
        return repeatQuestionRepository.findById(id)
            .map(repeatQuestionMapper::toDto);
    }

    /**
     * Delete the repeatQuestion by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RepeatQuestion : {}", id);
        repeatQuestionRepository.deleteById(id);
    }
}
