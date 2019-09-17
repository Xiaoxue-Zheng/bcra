package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.CheckboxAnswerItemService;
import uk.ac.herc.bcra.domain.CheckboxAnswerItem;
import uk.ac.herc.bcra.repository.CheckboxAnswerItemRepository;
import uk.ac.herc.bcra.service.dto.CheckboxAnswerItemDTO;
import uk.ac.herc.bcra.service.mapper.CheckboxAnswerItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CheckboxAnswerItem}.
 */
@Service
@Transactional
public class CheckboxAnswerItemServiceImpl implements CheckboxAnswerItemService {

    private final Logger log = LoggerFactory.getLogger(CheckboxAnswerItemServiceImpl.class);

    private final CheckboxAnswerItemRepository checkboxAnswerItemRepository;

    private final CheckboxAnswerItemMapper checkboxAnswerItemMapper;

    public CheckboxAnswerItemServiceImpl(CheckboxAnswerItemRepository checkboxAnswerItemRepository, CheckboxAnswerItemMapper checkboxAnswerItemMapper) {
        this.checkboxAnswerItemRepository = checkboxAnswerItemRepository;
        this.checkboxAnswerItemMapper = checkboxAnswerItemMapper;
    }

    /**
     * Save a checkboxAnswerItem.
     *
     * @param checkboxAnswerItemDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CheckboxAnswerItemDTO save(CheckboxAnswerItemDTO checkboxAnswerItemDTO) {
        log.debug("Request to save CheckboxAnswerItem : {}", checkboxAnswerItemDTO);
        CheckboxAnswerItem checkboxAnswerItem = checkboxAnswerItemMapper.toEntity(checkboxAnswerItemDTO);
        checkboxAnswerItem = checkboxAnswerItemRepository.save(checkboxAnswerItem);
        return checkboxAnswerItemMapper.toDto(checkboxAnswerItem);
    }

    /**
     * Get all the checkboxAnswerItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CheckboxAnswerItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CheckboxAnswerItems");
        return checkboxAnswerItemRepository.findAll(pageable)
            .map(checkboxAnswerItemMapper::toDto);
    }


    /**
     * Get one checkboxAnswerItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CheckboxAnswerItemDTO> findOne(Long id) {
        log.debug("Request to get CheckboxAnswerItem : {}", id);
        return checkboxAnswerItemRepository.findById(id)
            .map(checkboxAnswerItemMapper::toDto);
    }

    /**
     * Delete the checkboxAnswerItem by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CheckboxAnswerItem : {}", id);
        checkboxAnswerItemRepository.deleteById(id);
    }
}
