package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.QuestionSectionService;
import uk.ac.herc.bcra.domain.QuestionSection;
import uk.ac.herc.bcra.repository.QuestionSectionRepository;
import uk.ac.herc.bcra.service.dto.QuestionSectionDTO;
import uk.ac.herc.bcra.service.mapper.QuestionSectionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link QuestionSection}.
 */
@Service
@Transactional
public class QuestionSectionServiceImpl implements QuestionSectionService {

    private final Logger log = LoggerFactory.getLogger(QuestionSectionServiceImpl.class);

    private final QuestionSectionRepository questionSectionRepository;

    private final QuestionSectionMapper questionSectionMapper;

    public QuestionSectionServiceImpl(QuestionSectionRepository questionSectionRepository, QuestionSectionMapper questionSectionMapper) {
        this.questionSectionRepository = questionSectionRepository;
        this.questionSectionMapper = questionSectionMapper;
    }

    /**
     * Save a questionSection.
     *
     * @param questionSectionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public QuestionSectionDTO save(QuestionSectionDTO questionSectionDTO) {
        log.debug("Request to save QuestionSection : {}", questionSectionDTO);
        QuestionSection questionSection = questionSectionMapper.toEntity(questionSectionDTO);
        questionSection = questionSectionRepository.save(questionSection);
        return questionSectionMapper.toDto(questionSection);
    }

    /**
     * Get all the questionSections.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<QuestionSectionDTO> findAll() {
        log.debug("Request to get all QuestionSections");
        return questionSectionRepository.findAll().stream()
            .map(questionSectionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one questionSection by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionSectionDTO> findOne(Long id) {
        log.debug("Request to get QuestionSection : {}", id);
        return questionSectionRepository.findById(id)
            .map(questionSectionMapper::toDto);
    }

    /**
     * Delete the questionSection by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuestionSection : {}", id);
        questionSectionRepository.deleteById(id);
    }
}
