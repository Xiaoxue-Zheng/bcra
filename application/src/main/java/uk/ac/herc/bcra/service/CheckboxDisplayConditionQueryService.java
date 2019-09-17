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

import uk.ac.herc.bcra.domain.CheckboxDisplayCondition;
import uk.ac.herc.bcra.domain.*; // for static metamodels
import uk.ac.herc.bcra.repository.CheckboxDisplayConditionRepository;
import uk.ac.herc.bcra.service.dto.CheckboxDisplayConditionCriteria;
import uk.ac.herc.bcra.service.dto.CheckboxDisplayConditionDTO;
import uk.ac.herc.bcra.service.mapper.CheckboxDisplayConditionMapper;

/**
 * Service for executing complex queries for {@link CheckboxDisplayCondition} entities in the database.
 * The main input is a {@link CheckboxDisplayConditionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CheckboxDisplayConditionDTO} or a {@link Page} of {@link CheckboxDisplayConditionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CheckboxDisplayConditionQueryService extends QueryService<CheckboxDisplayCondition> {

    private final Logger log = LoggerFactory.getLogger(CheckboxDisplayConditionQueryService.class);

    private final CheckboxDisplayConditionRepository checkboxDisplayConditionRepository;

    private final CheckboxDisplayConditionMapper checkboxDisplayConditionMapper;

    public CheckboxDisplayConditionQueryService(CheckboxDisplayConditionRepository checkboxDisplayConditionRepository, CheckboxDisplayConditionMapper checkboxDisplayConditionMapper) {
        this.checkboxDisplayConditionRepository = checkboxDisplayConditionRepository;
        this.checkboxDisplayConditionMapper = checkboxDisplayConditionMapper;
    }

    /**
     * Return a {@link List} of {@link CheckboxDisplayConditionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CheckboxDisplayConditionDTO> findByCriteria(CheckboxDisplayConditionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CheckboxDisplayCondition> specification = createSpecification(criteria);
        return checkboxDisplayConditionMapper.toDto(checkboxDisplayConditionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CheckboxDisplayConditionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckboxDisplayConditionDTO> findByCriteria(CheckboxDisplayConditionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CheckboxDisplayCondition> specification = createSpecification(criteria);
        return checkboxDisplayConditionRepository.findAll(specification, page)
            .map(checkboxDisplayConditionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CheckboxDisplayConditionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CheckboxDisplayCondition> specification = createSpecification(criteria);
        return checkboxDisplayConditionRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<CheckboxDisplayCondition> createSpecification(CheckboxDisplayConditionCriteria criteria) {
        Specification<CheckboxDisplayCondition> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CheckboxDisplayCondition_.id));
            }
            if (criteria.getCheckboxQuestionItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getCheckboxQuestionItemId(),
                    root -> root.join(CheckboxDisplayCondition_.checkboxQuestionItem, JoinType.LEFT).get(CheckboxQuestionItem_.id)));
            }
        }
        return specification;
    }
}
