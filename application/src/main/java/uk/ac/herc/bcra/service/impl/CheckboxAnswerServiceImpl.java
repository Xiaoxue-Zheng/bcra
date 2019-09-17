package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.CheckboxAnswerService;
import uk.ac.herc.bcra.domain.CheckboxAnswer;
import uk.ac.herc.bcra.repository.CheckboxAnswerRepository;
import uk.ac.herc.bcra.service.dto.CheckboxAnswerDTO;
import uk.ac.herc.bcra.service.mapper.CheckboxAnswerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CheckboxAnswer}.
 */
@Service
@Transactional
public class CheckboxAnswerServiceImpl implements CheckboxAnswerService {

    private final Logger log = LoggerFactory.getLogger(CheckboxAnswerServiceImpl.class);

    private final CheckboxAnswerRepository checkboxAnswerRepository;

    private final CheckboxAnswerMapper checkboxAnswerMapper;

    public CheckboxAnswerServiceImpl(CheckboxAnswerRepository checkboxAnswerRepository, CheckboxAnswerMapper checkboxAnswerMapper) {
        this.checkboxAnswerRepository = checkboxAnswerRepository;
        this.checkboxAnswerMapper = checkboxAnswerMapper;
    }

    /**
     * Save a checkboxAnswer.
     *
     * @param checkboxAnswerDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CheckboxAnswerDTO save(CheckboxAnswerDTO checkboxAnswerDTO) {
        log.debug("Request to save CheckboxAnswer : {}", checkboxAnswerDTO);
        CheckboxAnswer checkboxAnswer = checkboxAnswerMapper.toEntity(checkboxAnswerDTO);
        checkboxAnswer = checkboxAnswerRepository.save(checkboxAnswer);
        return checkboxAnswerMapper.toDto(checkboxAnswer);
    }

    /**
     * Get all the checkboxAnswers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CheckboxAnswerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CheckboxAnswers");
        return checkboxAnswerRepository.findAll(pageable)
            .map(checkboxAnswerMapper::toDto);
    }


    /**
     * Get one checkboxAnswer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CheckboxAnswerDTO> findOne(Long id) {
        log.debug("Request to get CheckboxAnswer : {}", id);
        return checkboxAnswerRepository.findById(id)
            .map(checkboxAnswerMapper::toDto);
    }

    /**
     * Delete the checkboxAnswer by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CheckboxAnswer : {}", id);
        checkboxAnswerRepository.deleteById(id);
    }
}
