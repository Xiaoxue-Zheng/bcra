package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.RadioQuestionItemService;
import uk.ac.herc.bcra.domain.RadioQuestionItem;
import uk.ac.herc.bcra.repository.RadioQuestionItemRepository;
import uk.ac.herc.bcra.service.dto.RadioQuestionItemDTO;
import uk.ac.herc.bcra.service.mapper.RadioQuestionItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RadioQuestionItem}.
 */
@Service
@Transactional
public class RadioQuestionItemServiceImpl implements RadioQuestionItemService {

    private final Logger log = LoggerFactory.getLogger(RadioQuestionItemServiceImpl.class);

    private final RadioQuestionItemRepository radioQuestionItemRepository;

    private final RadioQuestionItemMapper radioQuestionItemMapper;

    public RadioQuestionItemServiceImpl(RadioQuestionItemRepository radioQuestionItemRepository, RadioQuestionItemMapper radioQuestionItemMapper) {
        this.radioQuestionItemRepository = radioQuestionItemRepository;
        this.radioQuestionItemMapper = radioQuestionItemMapper;
    }

    /**
     * Save a radioQuestionItem.
     *
     * @param radioQuestionItemDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RadioQuestionItemDTO save(RadioQuestionItemDTO radioQuestionItemDTO) {
        log.debug("Request to save RadioQuestionItem : {}", radioQuestionItemDTO);
        RadioQuestionItem radioQuestionItem = radioQuestionItemMapper.toEntity(radioQuestionItemDTO);
        radioQuestionItem = radioQuestionItemRepository.save(radioQuestionItem);
        return radioQuestionItemMapper.toDto(radioQuestionItem);
    }

    /**
     * Get all the radioQuestionItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RadioQuestionItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RadioQuestionItems");
        return radioQuestionItemRepository.findAll(pageable)
            .map(radioQuestionItemMapper::toDto);
    }


    /**
     * Get one radioQuestionItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RadioQuestionItemDTO> findOne(Long id) {
        log.debug("Request to get RadioQuestionItem : {}", id);
        return radioQuestionItemRepository.findById(id)
            .map(radioQuestionItemMapper::toDto);
    }

    /**
     * Delete the radioQuestionItem by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RadioQuestionItem : {}", id);
        radioQuestionItemRepository.deleteById(id);
    }
}
