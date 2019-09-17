package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.CheckboxQuestionItemService;
import uk.ac.herc.bcra.domain.CheckboxQuestionItem;
import uk.ac.herc.bcra.repository.CheckboxQuestionItemRepository;
import uk.ac.herc.bcra.service.dto.CheckboxQuestionItemDTO;
import uk.ac.herc.bcra.service.mapper.CheckboxQuestionItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CheckboxQuestionItem}.
 */
@Service
@Transactional
public class CheckboxQuestionItemServiceImpl implements CheckboxQuestionItemService {

    private final Logger log = LoggerFactory.getLogger(CheckboxQuestionItemServiceImpl.class);

    private final CheckboxQuestionItemRepository checkboxQuestionItemRepository;

    private final CheckboxQuestionItemMapper checkboxQuestionItemMapper;

    public CheckboxQuestionItemServiceImpl(CheckboxQuestionItemRepository checkboxQuestionItemRepository, CheckboxQuestionItemMapper checkboxQuestionItemMapper) {
        this.checkboxQuestionItemRepository = checkboxQuestionItemRepository;
        this.checkboxQuestionItemMapper = checkboxQuestionItemMapper;
    }

    /**
     * Save a checkboxQuestionItem.
     *
     * @param checkboxQuestionItemDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CheckboxQuestionItemDTO save(CheckboxQuestionItemDTO checkboxQuestionItemDTO) {
        log.debug("Request to save CheckboxQuestionItem : {}", checkboxQuestionItemDTO);
        CheckboxQuestionItem checkboxQuestionItem = checkboxQuestionItemMapper.toEntity(checkboxQuestionItemDTO);
        checkboxQuestionItem = checkboxQuestionItemRepository.save(checkboxQuestionItem);
        return checkboxQuestionItemMapper.toDto(checkboxQuestionItem);
    }

    /**
     * Get all the checkboxQuestionItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CheckboxQuestionItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CheckboxQuestionItems");
        return checkboxQuestionItemRepository.findAll(pageable)
            .map(checkboxQuestionItemMapper::toDto);
    }


    /**
     * Get one checkboxQuestionItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CheckboxQuestionItemDTO> findOne(Long id) {
        log.debug("Request to get CheckboxQuestionItem : {}", id);
        return checkboxQuestionItemRepository.findById(id)
            .map(checkboxQuestionItemMapper::toDto);
    }

    /**
     * Delete the checkboxQuestionItem by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CheckboxQuestionItem : {}", id);
        checkboxQuestionItemRepository.deleteById(id);
    }
}
