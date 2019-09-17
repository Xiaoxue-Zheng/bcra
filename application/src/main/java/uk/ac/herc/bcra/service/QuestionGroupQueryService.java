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

import uk.ac.herc.bcra.domain.QuestionGroup;
import uk.ac.herc.bcra.domain.*; // for static metamodels
import uk.ac.herc.bcra.repository.QuestionGroupRepository;
import uk.ac.herc.bcra.service.dto.QuestionGroupCriteria;
import uk.ac.herc.bcra.service.dto.QuestionGroupDTO;
import uk.ac.herc.bcra.service.mapper.QuestionGroupMapper;

/**
 * Service for executing complex queries for {@link QuestionGroup} entities in the database.
 * The main input is a {@link QuestionGroupCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QuestionGroupDTO} or a {@link Page} of {@link QuestionGroupDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QuestionGroupQueryService extends QueryService<QuestionGroup> {

    private final Logger log = LoggerFactory.getLogger(QuestionGroupQueryService.class);

    private final QuestionGroupRepository questionGroupRepository;

    private final QuestionGroupMapper questionGroupMapper;

    public QuestionGroupQueryService(QuestionGroupRepository questionGroupRepository, QuestionGroupMapper questionGroupMapper) {
        this.questionGroupRepository = questionGroupRepository;
        this.questionGroupMapper = questionGroupMapper;
    }

    /**
     * Return a {@link List} of {@link QuestionGroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QuestionGroupDTO> findByCriteria(QuestionGroupCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<QuestionGroup> specification = createSpecification(criteria);
        return questionGroupMapper.toDto(questionGroupRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link QuestionGroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QuestionGroupDTO> findByCriteria(QuestionGroupCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<QuestionGroup> specification = createSpecification(criteria);
        return questionGroupRepository.findAll(specification, page)
            .map(questionGroupMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QuestionGroupCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<QuestionGroup> specification = createSpecification(criteria);
        return questionGroupRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<QuestionGroup> createSpecification(QuestionGroupCriteria criteria) {
        Specification<QuestionGroup> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), QuestionGroup_.id));
            }
            if (criteria.getUuid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUuid(), QuestionGroup_.uuid));
            }
            if (criteria.getDisplayConditionId() != null) {
                specification = specification.and(buildSpecification(criteria.getDisplayConditionId(),
                    root -> root.join(QuestionGroup_.displayConditions, JoinType.LEFT).get(DisplayCondition_.id)));
            }
            if (criteria.getQuestionnaireQuestionGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuestionnaireQuestionGroupId(),
                    root -> root.join(QuestionGroup_.questionnaireQuestionGroups, JoinType.LEFT).get(QuestionnaireQuestionGroup_.id)));
            }
            if (criteria.getQuestionGroupQuestionId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuestionGroupQuestionId(),
                    root -> root.join(QuestionGroup_.questionGroupQuestions, JoinType.LEFT).get(QuestionGroupQuestion_.id)));
            }
            if (criteria.getAnswerGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnswerGroupId(),
                    root -> root.join(QuestionGroup_.answerGroups, JoinType.LEFT).get(AnswerGroup_.id)));
            }
        }
        return specification;
    }
}
