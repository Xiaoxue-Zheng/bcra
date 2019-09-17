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

import uk.ac.herc.bcra.domain.DisplayCondition;
import uk.ac.herc.bcra.domain.*; // for static metamodels
import uk.ac.herc.bcra.repository.DisplayConditionRepository;
import uk.ac.herc.bcra.service.dto.DisplayConditionCriteria;
import uk.ac.herc.bcra.service.dto.DisplayConditionDTO;
import uk.ac.herc.bcra.service.mapper.DisplayConditionMapper;

/**
 * Service for executing complex queries for {@link DisplayCondition} entities in the database.
 * The main input is a {@link DisplayConditionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DisplayConditionDTO} or a {@link Page} of {@link DisplayConditionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DisplayConditionQueryService extends QueryService<DisplayCondition> {

    private final Logger log = LoggerFactory.getLogger(DisplayConditionQueryService.class);

    private final DisplayConditionRepository displayConditionRepository;

    private final DisplayConditionMapper displayConditionMapper;

    public DisplayConditionQueryService(DisplayConditionRepository displayConditionRepository, DisplayConditionMapper displayConditionMapper) {
        this.displayConditionRepository = displayConditionRepository;
        this.displayConditionMapper = displayConditionMapper;
    }

    /**
     * Return a {@link List} of {@link DisplayConditionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DisplayConditionDTO> findByCriteria(DisplayConditionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DisplayCondition> specification = createSpecification(criteria);
        return displayConditionMapper.toDto(displayConditionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DisplayConditionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DisplayConditionDTO> findByCriteria(DisplayConditionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DisplayCondition> specification = createSpecification(criteria);
        return displayConditionRepository.findAll(specification, page)
            .map(displayConditionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DisplayConditionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DisplayCondition> specification = createSpecification(criteria);
        return displayConditionRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<DisplayCondition> createSpecification(DisplayConditionCriteria criteria) {
        Specification<DisplayCondition> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DisplayCondition_.id));
            }
            if (criteria.getQuestionGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuestionGroupId(),
                    root -> root.join(DisplayCondition_.questionGroup, JoinType.LEFT).get(QuestionGroup_.id)));
            }
        }
        return specification;
    }
}
