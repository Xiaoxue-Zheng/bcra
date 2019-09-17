package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.NumberCheckboxQuestionService;
import uk.ac.herc.bcra.domain.NumberCheckboxQuestion;
import uk.ac.herc.bcra.repository.NumberCheckboxQuestionRepository;
import uk.ac.herc.bcra.service.dto.NumberCheckboxQuestionDTO;
import uk.ac.herc.bcra.service.mapper.NumberCheckboxQuestionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link NumberCheckboxQuestion}.
 */
@Service
@Transactional
public class NumberCheckboxQuestionServiceImpl implements NumberCheckboxQuestionService {

    private final Logger log = LoggerFactory.getLogger(NumberCheckboxQuestionServiceImpl.class);

    private final NumberCheckboxQuestionRepository numberCheckboxQuestionRepository;

    private final NumberCheckboxQuestionMapper numberCheckboxQuestionMapper;

    public NumberCheckboxQuestionServiceImpl(NumberCheckboxQuestionRepository numberCheckboxQuestionRepository, NumberCheckboxQuestionMapper numberCheckboxQuestionMapper) {
        this.numberCheckboxQuestionRepository = numberCheckboxQuestionRepository;
        this.numberCheckboxQuestionMapper = numberCheckboxQuestionMapper;
    }

    /**
     * Save a numberCheckboxQuestion.
     *
     * @param numberCheckboxQuestionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public NumberCheckboxQuestionDTO save(NumberCheckboxQuestionDTO numberCheckboxQuestionDTO) {
        log.debug("Request to save NumberCheckboxQuestion : {}", numberCheckboxQuestionDTO);
        NumberCheckboxQuestion numberCheckboxQuestion = numberCheckboxQuestionMapper.toEntity(numberCheckboxQuestionDTO);
        numberCheckboxQuestion = numberCheckboxQuestionRepository.save(numberCheckboxQuestion);
        return numberCheckboxQuestionMapper.toDto(numberCheckboxQuestion);
    }

    /**
     * Get all the numberCheckboxQuestions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NumberCheckboxQuestionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NumberCheckboxQuestions");
        return numberCheckboxQuestionRepository.findAll(pageable)
            .map(numberCheckboxQuestionMapper::toDto);
    }


    /**
     * Get one numberCheckboxQuestion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<NumberCheckboxQuestionDTO> findOne(Long id) {
        log.debug("Request to get NumberCheckboxQuestion : {}", id);
        return numberCheckboxQuestionRepository.findById(id)
            .map(numberCheckboxQuestionMapper::toDto);
    }

    /**
     * Delete the numberCheckboxQuestion by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete NumberCheckboxQuestion : {}", id);
        numberCheckboxQuestionRepository.deleteById(id);
    }
}
