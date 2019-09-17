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

import uk.ac.herc.bcra.domain.CheckboxAnswerItem;
import uk.ac.herc.bcra.domain.*; // for static metamodels
import uk.ac.herc.bcra.repository.CheckboxAnswerItemRepository;
import uk.ac.herc.bcra.service.dto.CheckboxAnswerItemCriteria;
import uk.ac.herc.bcra.service.dto.CheckboxAnswerItemDTO;
import uk.ac.herc.bcra.service.mapper.CheckboxAnswerItemMapper;

/**
 * Service for executing complex queries for {@link CheckboxAnswerItem} entities in the database.
 * The main input is a {@link CheckboxAnswerItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CheckboxAnswerItemDTO} or a {@link Page} of {@link CheckboxAnswerItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CheckboxAnswerItemQueryService extends QueryService<CheckboxAnswerItem> {

    private final Logger log = LoggerFactory.getLogger(CheckboxAnswerItemQueryService.class);

    private final CheckboxAnswerItemRepository checkboxAnswerItemRepository;

    private final CheckboxAnswerItemMapper checkboxAnswerItemMapper;

    public CheckboxAnswerItemQueryService(CheckboxAnswerItemRepository checkboxAnswerItemRepository, CheckboxAnswerItemMapper checkboxAnswerItemMapper) {
        this.checkboxAnswerItemRepository = checkboxAnswerItemRepository;
        this.checkboxAnswerItemMapper = checkboxAnswerItemMapper;
    }

    /**
     * Return a {@link List} of {@link CheckboxAnswerItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CheckboxAnswerItemDTO> findByCriteria(CheckboxAnswerItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CheckboxAnswerItem> specification = createSpecification(criteria);
        return checkboxAnswerItemMapper.toDto(checkboxAnswerItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CheckboxAnswerItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckboxAnswerItemDTO> findByCriteria(CheckboxAnswerItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CheckboxAnswerItem> specification = createSpecification(criteria);
        return checkboxAnswerItemRepository.findAll(specification, page)
            .map(checkboxAnswerItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CheckboxAnswerItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CheckboxAnswerItem> specification = createSpecification(criteria);
        return checkboxAnswerItemRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<CheckboxAnswerItem> createSpecification(CheckboxAnswerItemCriteria criteria) {
        Specification<CheckboxAnswerItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CheckboxAnswerItem_.id));
            }
            if (criteria.getCheckboxAnswerId() != null) {
                specification = specification.and(buildSpecification(criteria.getCheckboxAnswerId(),
                    root -> root.join(CheckboxAnswerItem_.checkboxAnswer, JoinType.LEFT).get(CheckboxAnswer_.id)));
            }
            if (criteria.getCheckboxQuestionItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getCheckboxQuestionItemId(),
                    root -> root.join(CheckboxAnswerItem_.checkboxQuestionItem, JoinType.LEFT).get(CheckboxQuestionItem_.id)));
            }
        }
        return specification;
    }
}
