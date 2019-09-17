package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.RadioAnswerItemService;
import uk.ac.herc.bcra.domain.RadioAnswerItem;
import uk.ac.herc.bcra.repository.RadioAnswerItemRepository;
import uk.ac.herc.bcra.service.dto.RadioAnswerItemDTO;
import uk.ac.herc.bcra.service.mapper.RadioAnswerItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RadioAnswerItem}.
 */
@Service
@Transactional
public class RadioAnswerItemServiceImpl implements RadioAnswerItemService {

    private final Logger log = LoggerFactory.getLogger(RadioAnswerItemServiceImpl.class);

    private final RadioAnswerItemRepository radioAnswerItemRepository;

    private final RadioAnswerItemMapper radioAnswerItemMapper;

    public RadioAnswerItemServiceImpl(RadioAnswerItemRepository radioAnswerItemRepository, RadioAnswerItemMapper radioAnswerItemMapper) {
        this.radioAnswerItemRepository = radioAnswerItemRepository;
        this.radioAnswerItemMapper = radioAnswerItemMapper;
    }

    /**
     * Save a radioAnswerItem.
     *
     * @param radioAnswerItemDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RadioAnswerItemDTO save(RadioAnswerItemDTO radioAnswerItemDTO) {
        log.debug("Request to save RadioAnswerItem : {}", radioAnswerItemDTO);
        RadioAnswerItem radioAnswerItem = radioAnswerItemMapper.toEntity(radioAnswerItemDTO);
        radioAnswerItem = radioAnswerItemRepository.save(radioAnswerItem);
        return radioAnswerItemMapper.toDto(radioAnswerItem);
    }

    /**
     * Get all the radioAnswerItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RadioAnswerItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RadioAnswerItems");
        return radioAnswerItemRepository.findAll(pageable)
            .map(radioAnswerItemMapper::toDto);
    }


    /**
     * Get one radioAnswerItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RadioAnswerItemDTO> findOne(Long id) {
        log.debug("Request to get RadioAnswerItem : {}", id);
        return radioAnswerItemRepository.findById(id)
            .map(radioAnswerItemMapper::toDto);
    }

    /**
     * Delete the radioAnswerItem by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RadioAnswerItem : {}", id);
        radioAnswerItemRepository.deleteById(id);
    }
}
