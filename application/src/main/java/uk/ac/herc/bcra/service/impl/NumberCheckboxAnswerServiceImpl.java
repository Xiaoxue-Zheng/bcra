package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.NumberCheckboxAnswerService;
import uk.ac.herc.bcra.domain.NumberCheckboxAnswer;
import uk.ac.herc.bcra.repository.NumberCheckboxAnswerRepository;
import uk.ac.herc.bcra.service.dto.NumberCheckboxAnswerDTO;
import uk.ac.herc.bcra.service.mapper.NumberCheckboxAnswerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link NumberCheckboxAnswer}.
 */
@Service
@Transactional
public class NumberCheckboxAnswerServiceImpl implements NumberCheckboxAnswerService {

    private final Logger log = LoggerFactory.getLogger(NumberCheckboxAnswerServiceImpl.class);

    private final NumberCheckboxAnswerRepository numberCheckboxAnswerRepository;

    private final NumberCheckboxAnswerMapper numberCheckboxAnswerMapper;

    public NumberCheckboxAnswerServiceImpl(NumberCheckboxAnswerRepository numberCheckboxAnswerRepository, NumberCheckboxAnswerMapper numberCheckboxAnswerMapper) {
        this.numberCheckboxAnswerRepository = numberCheckboxAnswerRepository;
        this.numberCheckboxAnswerMapper = numberCheckboxAnswerMapper;
    }

    /**
     * Save a numberCheckboxAnswer.
     *
     * @param numberCheckboxAnswerDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public NumberCheckboxAnswerDTO save(NumberCheckboxAnswerDTO numberCheckboxAnswerDTO) {
        log.debug("Request to save NumberCheckboxAnswer : {}", numberCheckboxAnswerDTO);
        NumberCheckboxAnswer numberCheckboxAnswer = numberCheckboxAnswerMapper.toEntity(numberCheckboxAnswerDTO);
        numberCheckboxAnswer = numberCheckboxAnswerRepository.save(numberCheckboxAnswer);
        return numberCheckboxAnswerMapper.toDto(numberCheckboxAnswer);
    }

    /**
     * Get all the numberCheckboxAnswers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NumberCheckboxAnswerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NumberCheckboxAnswers");
        return numberCheckboxAnswerRepository.findAll(pageable)
            .map(numberCheckboxAnswerMapper::toDto);
    }


    /**
     * Get one numberCheckboxAnswer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<NumberCheckboxAnswerDTO> findOne(Long id) {
        log.debug("Request to get NumberCheckboxAnswer : {}", id);
        return numberCheckboxAnswerRepository.findById(id)
            .map(numberCheckboxAnswerMapper::toDto);
    }

    /**
     * Delete the numberCheckboxAnswer by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete NumberCheckboxAnswer : {}", id);
        numberCheckboxAnswerRepository.deleteById(id);
    }
}
