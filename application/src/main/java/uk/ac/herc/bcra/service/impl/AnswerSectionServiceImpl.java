package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.AnswerSectionService;
import uk.ac.herc.bcra.domain.AnswerSection;
import uk.ac.herc.bcra.repository.AnswerSectionRepository;
import uk.ac.herc.bcra.service.dto.AnswerSectionDTO;
import uk.ac.herc.bcra.service.mapper.AnswerSectionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AnswerSection}.
 */
@Service
@Transactional
public class AnswerSectionServiceImpl implements AnswerSectionService {

    private final Logger log = LoggerFactory.getLogger(AnswerSectionServiceImpl.class);

    private final AnswerSectionRepository answerSectionRepository;

    private final AnswerSectionMapper answerSectionMapper;

    public AnswerSectionServiceImpl(AnswerSectionRepository answerSectionRepository, AnswerSectionMapper answerSectionMapper) {
        this.answerSectionRepository = answerSectionRepository;
        this.answerSectionMapper = answerSectionMapper;
    }

    /**
     * Save a answerSection.
     *
     * @param answerSectionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AnswerSectionDTO save(AnswerSectionDTO answerSectionDTO) {
        log.debug("Request to save AnswerSection : {}", answerSectionDTO);
        AnswerSection answerSection = answerSectionMapper.toEntity(answerSectionDTO);
        answerSection = answerSectionRepository.save(answerSection);
        return answerSectionMapper.toDto(answerSection);
    }

    /**
     * Get all the answerSections.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AnswerSectionDTO> findAll() {
        log.debug("Request to get all AnswerSections");
        return answerSectionRepository.findAll().stream()
            .map(answerSectionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one answerSection by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AnswerSectionDTO> findOne(Long id) {
        log.debug("Request to get AnswerSection : {}", id);
        return answerSectionRepository.findById(id)
            .map(answerSectionMapper::toDto);
    }

    /**
     * Delete the answerSection by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnswerSection : {}", id);
        answerSectionRepository.deleteById(id);
    }
}
