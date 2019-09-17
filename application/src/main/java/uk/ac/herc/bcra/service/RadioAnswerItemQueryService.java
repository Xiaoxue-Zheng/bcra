package uk.ac.herc.bcra.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import uk.ac.herc.bcra.domain.RadioAnswerItem;
import uk.ac.herc.bcra.domain.*; // for static metamodels
import uk.ac.herc.bcra.repository.RadioAnswerItemRepository;
import uk.ac.herc.bcra.service.dto.RadioAnswerItemCriteria;
import uk.ac.herc.bcra.service.dto.RadioAnswerItemDTO;
import uk.ac.herc.bcra.service.mapper.RadioAnswerItemMapper;

/**
 * Service for executing complex queries for {@link RadioAnswerItem} entities in the database.
 * The main input is a {@link RadioAnswerItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RadioAnswerItemDTO} or a {@link Page} of {@link RadioAnswerItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RadioAnswerItemQueryService extends QueryService<RadioAnswerItem> {

    private final Logger log = LoggerFactory.getLogger(RadioAnswerItemQueryService.class);

    private final RadioAnswerItemRepository radioAnswerItemRepository;

    private final RadioAnswerItemMapper radioAnswerItemMapper;

    public RadioAnswerItemQueryService(RadioAnswerItemRepository radioAnswerItemRepository, RadioAnswerItemMapper radioAnswerItemMapper) {
        this.radioAnswerItemRepository = radioAnswerItemRepository;
        this.radioAnswerItemMapper = radioAnswerItemMapper;
    }

    /**
     * Return a {@link List} of {@link RadioAnswerItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RadioAnswerItemDTO> findByCriteria(RadioAnswerItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RadioAnswerItem> specification = createSpecification(criteria);
        return radioAnswerItemMapper.toDto(radioAnswerItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RadioAnswerItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RadioAnswerItemDTO> findByCriteria(RadioAnswerItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RadioAnswerItem> specification = createSpecification(criteria);
        return radioAnswerItemRepository.findAll(specification, page)
            .map(radioAnswerItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RadioAnswerItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RadioAnswerItem> specification = createSpecification(criteria);
        return radioAnswerItemRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<RadioAnswerItem> createSpecification(RadioAnswerItemCriteria criteria) {
        Specification<RadioAnswerItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), RadioAnswerItem_.id));
            }
            if (criteria.getRadioAnswerId() != null) {
                specification = specification.and(buildSpecification(criteria.getRadioAnswerId(),
                    root -> root.join(RadioAnswerItem_.radioAnswer, JoinType.LEFT).get(RadioAnswer_.id)));
            }
        }
        return specification;
    }
}
