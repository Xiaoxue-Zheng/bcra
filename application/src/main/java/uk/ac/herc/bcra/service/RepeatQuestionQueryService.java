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

import uk.ac.herc.bcra.domain.RepeatQuestion;
import uk.ac.herc.bcra.domain.*; // for static metamodels
import uk.ac.herc.bcra.repository.RepeatQuestionRepository;
import uk.ac.herc.bcra.service.dto.RepeatQuestionCriteria;
import uk.ac.herc.bcra.service.dto.RepeatQuestionDTO;
import uk.ac.herc.bcra.service.mapper.RepeatQuestionMapper;

/**
 * Service for executing complex queries for {@link RepeatQuestion} entities in the database.
 * The main input is a {@link RepeatQuestionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RepeatQuestionDTO} or a {@link Page} of {@link RepeatQuestionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RepeatQuestionQueryService extends QueryService<RepeatQuestion> {

    private final Logger log = LoggerFactory.getLogger(RepeatQuestionQueryService.class);

    private final RepeatQuestionRepository repeatQuestionRepository;

    private final RepeatQuestionMapper repeatQuestionMapper;

    public RepeatQuestionQueryService(RepeatQuestionRepository repeatQuestionRepository, RepeatQuestionMapper repeatQuestionMapper) {
        this.repeatQuestionRepository = repeatQuestionRepository;
        this.repeatQuestionMapper = repeatQuestionMapper;
    }

    /**
     * Return a {@link List} of {@link RepeatQuestionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RepeatQuestionDTO> findByCriteria(RepeatQuestionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RepeatQuestion> specification = createSpecification(criteria);
        return repeatQuestionMapper.toDto(repeatQuestionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RepeatQuestionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RepeatQuestionDTO> findByCriteria(RepeatQuestionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RepeatQuestion> specification = createSpecification(criteria);
        return repeatQuestionRepository.findAll(specification, page)
            .map(repeatQuestionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RepeatQuestionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RepeatQuestion> specification = createSpecification(criteria);
        return repeatQuestionRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<RepeatQuestion> createSpecification(RepeatQuestionCriteria criteria) {
        Specification<RepeatQuestion> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), RepeatQuestion_.id));
            }
            if (criteria.getMaximum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaximum(), RepeatQuestion_.maximum));
            }
            if (criteria.getRepeatDisplayConditionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRepeatDisplayConditionId(),
                    root -> root.join(RepeatQuestion_.repeatDisplayConditions, JoinType.LEFT).get(RepeatDisplayCondition_.id)));
            }
        }
        return specification;
    }
}
