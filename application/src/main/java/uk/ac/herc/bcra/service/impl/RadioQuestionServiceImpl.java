package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.RadioQuestionService;
import uk.ac.herc.bcra.domain.RadioQuestion;
import uk.ac.herc.bcra.repository.RadioQuestionRepository;
import uk.ac.herc.bcra.service.dto.RadioQuestionDTO;
import uk.ac.herc.bcra.service.mapper.RadioQuestionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RadioQuestion}.
 */
@Service
@Transactional
public class RadioQuestionServiceImpl implements RadioQuestionService {

    private final Logger log = LoggerFactory.getLogger(RadioQuestionServiceImpl.class);

    private final RadioQuestionRepository radioQuestionRepository;

    private final RadioQuestionMapper radioQuestionMapper;

    public RadioQuestionServiceImpl(RadioQuestionRepository radioQuestionRepository, RadioQuestionMapper radioQuestionMapper) {
        this.radioQuestionRepository = radioQuestionRepository;
        this.radioQuestionMapper = radioQuestionMapper;
    }

    /**
     * Save a radioQuestion.
     *
     * @param radioQuestionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RadioQuestionDTO save(RadioQuestionDTO radioQuestionDTO) {
        log.debug("Request to save RadioQuestion : {}", radioQuestionDTO);
        RadioQuestion radioQuestion = radioQuestionMapper.toEntity(radioQuestionDTO);
        radioQuestion = radioQuestionRepository.save(radioQuestion);
        return radioQuestionMapper.toDto(radioQuestion);
    }

    /**
     * Get all the radioQuestions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RadioQuestionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RadioQuestions");
        return radioQuestionRepository.findAll(pageable)
            .map(radioQuestionMapper::toDto);
    }


    /**
     * Get one radioQuestion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RadioQuestionDTO> findOne(Long id) {
        log.debug("Request to get RadioQuestion : {}", id);
        return radioQuestionRepository.findById(id)
            .map(radioQuestionMapper::toDto);
    }

    /**
     * Delete the radioQuestion by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RadioQuestion : {}", id);
        radioQuestionRepository.deleteById(id);
    }
}
