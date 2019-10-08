package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.QuestionItemService;
import uk.ac.herc.bcra.domain.QuestionItem;
import uk.ac.herc.bcra.repository.QuestionItemRepository;
import uk.ac.herc.bcra.service.dto.QuestionItemDTO;
import uk.ac.herc.bcra.service.mapper.QuestionItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link QuestionItem}.
 */
@Service
@Transactional
public class QuestionItemServiceImpl implements QuestionItemService {

    private final Logger log = LoggerFactory.getLogger(QuestionItemServiceImpl.class);

    private final QuestionItemRepository questionItemRepository;

    private final QuestionItemMapper questionItemMapper;

    public QuestionItemServiceImpl(QuestionItemRepository questionItemRepository, QuestionItemMapper questionItemMapper) {
        this.questionItemRepository = questionItemRepository;
        this.questionItemMapper = questionItemMapper;
    }

    /**
     * Save a questionItem.
     *
     * @param questionItemDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public QuestionItemDTO save(QuestionItemDTO questionItemDTO) {
        log.debug("Request to save QuestionItem : {}", questionItemDTO);
        QuestionItem questionItem = questionItemMapper.toEntity(questionItemDTO);
        questionItem = questionItemRepository.save(questionItem);
        return questionItemMapper.toDto(questionItem);
    }

    /**
     * Get all the questionItems.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<QuestionItemDTO> findAll() {
        log.debug("Request to get all QuestionItems");
        return questionItemRepository.findAll().stream()
            .map(questionItemMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one questionItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionItemDTO> findOne(Long id) {
        log.debug("Request to get QuestionItem : {}", id);
        return questionItemRepository.findById(id)
            .map(questionItemMapper::toDto);
    }

    /**
     * Delete the questionItem by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuestionItem : {}", id);
        questionItemRepository.deleteById(id);
    }
}
