package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.QuestionGroupQuestionService;
import uk.ac.herc.bcra.domain.QuestionGroupQuestion;
import uk.ac.herc.bcra.repository.QuestionGroupQuestionRepository;
import uk.ac.herc.bcra.service.dto.QuestionGroupQuestionDTO;
import uk.ac.herc.bcra.service.mapper.QuestionGroupQuestionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link QuestionGroupQuestion}.
 */
@Service
@Transactional
public class QuestionGroupQuestionServiceImpl implements QuestionGroupQuestionService {

    private final Logger log = LoggerFactory.getLogger(QuestionGroupQuestionServiceImpl.class);

    private final QuestionGroupQuestionRepository questionGroupQuestionRepository;

    private final QuestionGroupQuestionMapper questionGroupQuestionMapper;

    public QuestionGroupQuestionServiceImpl(QuestionGroupQuestionRepository questionGroupQuestionRepository, QuestionGroupQuestionMapper questionGroupQuestionMapper) {
        this.questionGroupQuestionRepository = questionGroupQuestionRepository;
        this.questionGroupQuestionMapper = questionGroupQuestionMapper;
    }

    /**
     * Save a questionGroupQuestion.
     *
     * @param questionGroupQuestionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public QuestionGroupQuestionDTO save(QuestionGroupQuestionDTO questionGroupQuestionDTO) {
        log.debug("Request to save QuestionGroupQuestion : {}", questionGroupQuestionDTO);
        QuestionGroupQuestion questionGroupQuestion = questionGroupQuestionMapper.toEntity(questionGroupQuestionDTO);
        questionGroupQuestion = questionGroupQuestionRepository.save(questionGroupQuestion);
        return questionGroupQuestionMapper.toDto(questionGroupQuestion);
    }

    /**
     * Get all the questionGroupQuestions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<QuestionGroupQuestionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all QuestionGroupQuestions");
        return questionGroupQuestionRepository.findAll(pageable)
            .map(questionGroupQuestionMapper::toDto);
    }


    /**
     * Get one questionGroupQuestion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionGroupQuestionDTO> findOne(Long id) {
        log.debug("Request to get QuestionGroupQuestion : {}", id);
        return questionGroupQuestionRepository.findById(id)
            .map(questionGroupQuestionMapper::toDto);
    }

    /**
     * Delete the questionGroupQuestion by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuestionGroupQuestion : {}", id);
        questionGroupQuestionRepository.deleteById(id);
    }
}
