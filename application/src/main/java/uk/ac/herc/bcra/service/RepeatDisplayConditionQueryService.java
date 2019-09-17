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

import uk.ac.herc.bcra.domain.RepeatDisplayCondition;
import uk.ac.herc.bcra.domain.*; // for static metamodels
import uk.ac.herc.bcra.repository.RepeatDisplayConditionRepository;
import uk.ac.herc.bcra.service.dto.RepeatDisplayConditionCriteria;
import uk.ac.herc.bcra.service.dto.RepeatDisplayConditionDTO;
import uk.ac.herc.bcra.service.mapper.RepeatDisplayConditionMapper;

/**
 * Service for executing complex queries for {@link RepeatDisplayCondition} entities in the database.
 * The main input is a {@link RepeatDisplayConditionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RepeatDisplayConditionDTO} or a {@link Page} of {@link RepeatDisplayConditionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RepeatDisplayConditionQueryService extends QueryService<RepeatDisplayCondition> {

    private final Logger log = LoggerFactory.getLogger(RepeatDisplayConditionQueryService.class);

    private final RepeatDisplayConditionRepository repeatDisplayConditionRepository;

    private final RepeatDisplayConditionMapper repeatDisplayConditionMapper;

    public RepeatDisplayConditionQueryService(RepeatDisplayConditionRepository repeatDisplayConditionRepository, RepeatDisplayConditionMapper repeatDisplayConditionMapper) {
        this.repeatDisplayConditionRepository = repeatDisplayConditionRepository;
        this.repeatDisplayConditionMapper = repeatDisplayConditionMapper;
    }

    /**
     * Return a {@link List} of {@link RepeatDisplayConditionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RepeatDisplayConditionDTO> findByCriteria(RepeatDisplayConditionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RepeatDisplayCondition> specification = createSpecification(criteria);
        return repeatDisplayConditionMapper.toDto(repeatDisplayConditionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RepeatDisplayConditionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RepeatDisplayConditionDTO> findByCriteria(RepeatDisplayConditionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RepeatDisplayCondition> specification = createSpecification(criteria);
        return repeatDisplayConditionRepository.findAll(specification, page)
            .map(repeatDisplayConditionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RepeatDisplayConditionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RepeatDisplayCondition> specification = createSpecification(criteria);
        return repeatDisplayConditionRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<RepeatDisplayCondition> createSpecification(RepeatDisplayConditionCriteria criteria) {
        Specification<RepeatDisplayCondition> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), RepeatDisplayCondition_.id));
            }
            if (criteria.getRepeatQuestionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRepeatQuestionId(),
                    root -> root.join(RepeatDisplayCondition_.repeatQuestion, JoinType.LEFT).get(RepeatQuestion_.id)));
            }
        }
        return specification;
    }
}
