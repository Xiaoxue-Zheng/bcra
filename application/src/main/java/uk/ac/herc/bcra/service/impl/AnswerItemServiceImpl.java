package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.AnswerItemService;
import uk.ac.herc.bcra.domain.AnswerItem;
import uk.ac.herc.bcra.repository.AnswerItemRepository;
import uk.ac.herc.bcra.service.dto.AnswerItemDTO;
import uk.ac.herc.bcra.service.mapper.AnswerItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AnswerItem}.
 */
@Service
@Transactional
public class AnswerItemServiceImpl implements AnswerItemService {

    private final Logger log = LoggerFactory.getLogger(AnswerItemServiceImpl.class);

    private final AnswerItemRepository answerItemRepository;

    private final AnswerItemMapper answerItemMapper;

    public AnswerItemServiceImpl(AnswerItemRepository answerItemRepository, AnswerItemMapper answerItemMapper) {
        this.answerItemRepository = answerItemRepository;
        this.answerItemMapper = answerItemMapper;
    }

    /**
     * Save a answerItem.
     *
     * @param answerItemDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AnswerItemDTO save(AnswerItemDTO answerItemDTO) {
        log.debug("Request to save AnswerItem : {}", answerItemDTO);
        AnswerItem answerItem = answerItemMapper.toEntity(answerItemDTO);
        answerItem = answerItemRepository.save(answerItem);
        return answerItemMapper.toDto(answerItem);
    }

    /**
     * Get all the answerItems.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AnswerItemDTO> findAll() {
        log.debug("Request to get all AnswerItems");
        return answerItemRepository.findAll().stream()
            .map(answerItemMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one answerItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AnswerItemDTO> findOne(Long id) {
        log.debug("Request to get AnswerItem : {}", id);
        return answerItemRepository.findById(id)
            .map(answerItemMapper::toDto);
    }

    /**
     * Delete the answerItem by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnswerItem : {}", id);
        answerItemRepository.deleteById(id);
    }
}
