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

import uk.ac.herc.bcra.domain.CheckboxQuestionItem;
import uk.ac.herc.bcra.domain.*; // for static metamodels
import uk.ac.herc.bcra.repository.CheckboxQuestionItemRepository;
import uk.ac.herc.bcra.service.dto.CheckboxQuestionItemCriteria;
import uk.ac.herc.bcra.service.dto.CheckboxQuestionItemDTO;
import uk.ac.herc.bcra.service.mapper.CheckboxQuestionItemMapper;

/**
 * Service for executing complex queries for {@link CheckboxQuestionItem} entities in the database.
 * The main input is a {@link CheckboxQuestionItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CheckboxQuestionItemDTO} or a {@link Page} of {@link CheckboxQuestionItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CheckboxQuestionItemQueryService extends QueryService<CheckboxQuestionItem> {

    private final Logger log = LoggerFactory.getLogger(CheckboxQuestionItemQueryService.class);

    private final CheckboxQuestionItemRepository checkboxQuestionItemRepository;

    private final CheckboxQuestionItemMapper checkboxQuestionItemMapper;

    public CheckboxQuestionItemQueryService(CheckboxQuestionItemRepository checkboxQuestionItemRepository, CheckboxQuestionItemMapper checkboxQuestionItemMapper) {
        this.checkboxQuestionItemRepository = checkboxQuestionItemRepository;
        this.checkboxQuestionItemMapper = checkboxQuestionItemMapper;
    }

    /**
     * Return a {@link List} of {@link CheckboxQuestionItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CheckboxQuestionItemDTO> findByCriteria(CheckboxQuestionItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CheckboxQuestionItem> specification = createSpecification(criteria);
        return checkboxQuestionItemMapper.toDto(checkboxQuestionItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CheckboxQuestionItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckboxQuestionItemDTO> findByCriteria(CheckboxQuestionItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CheckboxQuestionItem> specification = createSpecification(criteria);
        return checkboxQuestionItemRepository.findAll(specification, page)
            .map(checkboxQuestionItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CheckboxQuestionItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CheckboxQuestionItem> specification = createSpecification(criteria);
        return checkboxQuestionItemRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<CheckboxQuestionItem> createSpecification(CheckboxQuestionItemCriteria criteria) {
        Specification<CheckboxQuestionItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CheckboxQuestionItem_.id));
            }
            if (criteria.getUuid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUuid(), CheckboxQuestionItem_.uuid));
            }
            if (criteria.getLabel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLabel(), CheckboxQuestionItem_.label));
            }
            if (criteria.getDescriptor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescriptor(), CheckboxQuestionItem_.descriptor));
            }
            if (criteria.getCheckboxQuestionId() != null) {
                specification = specification.and(buildSpecification(criteria.getCheckboxQuestionId(),
                    root -> root.join(CheckboxQuestionItem_.checkboxQuestion, JoinType.LEFT).get(CheckboxQuestion_.id)));
            }
            if (criteria.getCheckboxAnswerItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getCheckboxAnswerItemId(),
                    root -> root.join(CheckboxQuestionItem_.checkboxAnswerItems, JoinType.LEFT).get(CheckboxAnswerItem_.id)));
            }
            if (criteria.getCheckboxDisplayConditionId() != null) {
                specification = specification.and(buildSpecification(criteria.getCheckboxDisplayConditionId(),
                    root -> root.join(CheckboxQuestionItem_.checkboxDisplayConditions, JoinType.LEFT).get(CheckboxDisplayCondition_.id)));
            }
        }
        return specification;
    }
}
