package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.CheckboxQuestionService;
import uk.ac.herc.bcra.domain.CheckboxQuestion;
import uk.ac.herc.bcra.repository.CheckboxQuestionRepository;
import uk.ac.herc.bcra.service.dto.CheckboxQuestionDTO;
import uk.ac.herc.bcra.service.mapper.CheckboxQuestionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CheckboxQuestion}.
 */
@Service
@Transactional
public class CheckboxQuestionServiceImpl implements CheckboxQuestionService {

    private final Logger log = LoggerFactory.getLogger(CheckboxQuestionServiceImpl.class);

    private final CheckboxQuestionRepository checkboxQuestionRepository;

    private final CheckboxQuestionMapper checkboxQuestionMapper;

    public CheckboxQuestionServiceImpl(CheckboxQuestionRepository checkboxQuestionRepository, CheckboxQuestionMapper checkboxQuestionMapper) {
        this.checkboxQuestionRepository = checkboxQuestionRepository;
        this.checkboxQuestionMapper = checkboxQuestionMapper;
    }

    /**
     * Save a checkboxQuestion.
     *
     * @param checkboxQuestionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CheckboxQuestionDTO save(CheckboxQuestionDTO checkboxQuestionDTO) {
        log.debug("Request to save CheckboxQuestion : {}", checkboxQuestionDTO);
        CheckboxQuestion checkboxQuestion = checkboxQuestionMapper.toEntity(checkboxQuestionDTO);
        checkboxQuestion = checkboxQuestionRepository.save(checkboxQuestion);
        return checkboxQuestionMapper.toDto(checkboxQuestion);
    }

    /**
     * Get all the checkboxQuestions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CheckboxQuestionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CheckboxQuestions");
        return checkboxQuestionRepository.findAll(pageable)
            .map(checkboxQuestionMapper::toDto);
    }


    /**
     * Get one checkboxQuestion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CheckboxQuestionDTO> findOne(Long id) {
        log.debug("Request to get CheckboxQuestion : {}", id);
        return checkboxQuestionRepository.findById(id)
            .map(checkboxQuestionMapper::toDto);
    }

    /**
     * Delete the checkboxQuestion by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CheckboxQuestion : {}", id);
        checkboxQuestionRepository.deleteById(id);
    }
}
