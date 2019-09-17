package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.RadioAnswerService;
import uk.ac.herc.bcra.domain.RadioAnswer;
import uk.ac.herc.bcra.repository.RadioAnswerRepository;
import uk.ac.herc.bcra.service.dto.RadioAnswerDTO;
import uk.ac.herc.bcra.service.mapper.RadioAnswerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link RadioAnswer}.
 */
@Service
@Transactional
public class RadioAnswerServiceImpl implements RadioAnswerService {

    private final Logger log = LoggerFactory.getLogger(RadioAnswerServiceImpl.class);

    private final RadioAnswerRepository radioAnswerRepository;

    private final RadioAnswerMapper radioAnswerMapper;

    public RadioAnswerServiceImpl(RadioAnswerRepository radioAnswerRepository, RadioAnswerMapper radioAnswerMapper) {
        this.radioAnswerRepository = radioAnswerRepository;
        this.radioAnswerMapper = radioAnswerMapper;
    }

    /**
     * Save a radioAnswer.
     *
     * @param radioAnswerDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RadioAnswerDTO save(RadioAnswerDTO radioAnswerDTO) {
        log.debug("Request to save RadioAnswer : {}", radioAnswerDTO);
        RadioAnswer radioAnswer = radioAnswerMapper.toEntity(radioAnswerDTO);
        radioAnswer = radioAnswerRepository.save(radioAnswer);
        return radioAnswerMapper.toDto(radioAnswer);
    }

    /**
     * Get all the radioAnswers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RadioAnswerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RadioAnswers");
        return radioAnswerRepository.findAll(pageable)
            .map(radioAnswerMapper::toDto);
    }



    /**
    *  Get all the radioAnswers where RadioAnswerItem is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<RadioAnswerDTO> findAllWhereRadioAnswerItemIsNull() {
        log.debug("Request to get all radioAnswers where RadioAnswerItem is null");
        return StreamSupport
            .stream(radioAnswerRepository.findAll().spliterator(), false)
            .filter(radioAnswer -> radioAnswer.getRadioAnswerItem() == null)
            .map(radioAnswerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one radioAnswer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RadioAnswerDTO> findOne(Long id) {
        log.debug("Request to get RadioAnswer : {}", id);
        return radioAnswerRepository.findById(id)
            .map(radioAnswerMapper::toDto);
    }

    /**
     * Delete the radioAnswer by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RadioAnswer : {}", id);
        radioAnswerRepository.deleteById(id);
    }
}
