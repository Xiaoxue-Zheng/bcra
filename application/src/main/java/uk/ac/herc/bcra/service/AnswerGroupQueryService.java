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

import uk.ac.herc.bcra.domain.AnswerGroup;
import uk.ac.herc.bcra.domain.*; // for static metamodels
import uk.ac.herc.bcra.repository.AnswerGroupRepository;
import uk.ac.herc.bcra.service.dto.AnswerGroupCriteria;
import uk.ac.herc.bcra.service.dto.AnswerGroupDTO;
import uk.ac.herc.bcra.service.mapper.AnswerGroupMapper;

/**
 * Service for executing complex queries for {@link AnswerGroup} entities in the database.
 * The main input is a {@link AnswerGroupCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AnswerGroupDTO} or a {@link Page} of {@link AnswerGroupDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AnswerGroupQueryService extends QueryService<AnswerGroup> {

    private final Logger log = LoggerFactory.getLogger(AnswerGroupQueryService.class);

    private final AnswerGroupRepository answerGroupRepository;

    private final AnswerGroupMapper answerGroupMapper;

    public AnswerGroupQueryService(AnswerGroupRepository answerGroupRepository, AnswerGroupMapper answerGroupMapper) {
        this.answerGroupRepository = answerGroupRepository;
        this.answerGroupMapper = answerGroupMapper;
    }

    /**
     * Return a {@link List} of {@link AnswerGroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AnswerGroupDTO> findByCriteria(AnswerGroupCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AnswerGroup> specification = createSpecification(criteria);
        return answerGroupMapper.toDto(answerGroupRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AnswerGroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AnswerGroupDTO> findByCriteria(AnswerGroupCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AnswerGroup> specification = createSpecification(criteria);
        return answerGroupRepository.findAll(specification, page)
            .map(answerGroupMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AnswerGroupCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AnswerGroup> specification = createSpecification(criteria);
        return answerGroupRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<AnswerGroup> createSpecification(AnswerGroupCriteria criteria) {
        Specification<AnswerGroup> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AnswerGroup_.id));
            }
            if (criteria.getAnswerResponseId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnswerResponseId(),
                    root -> root.join(AnswerGroup_.answerResponse, JoinType.LEFT).get(AnswerResponse_.id)));
            }
            if (criteria.getQuestionGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuestionGroupId(),
                    root -> root.join(AnswerGroup_.questionGroup, JoinType.LEFT).get(QuestionGroup_.id)));
            }
            if (criteria.getAnswerId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnswerId(),
                    root -> root.join(AnswerGroup_.answers, JoinType.LEFT).get(Answer_.id)));
            }
        }
        return specification;
    }
}
