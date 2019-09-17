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

import uk.ac.herc.bcra.domain.QuestionGroupQuestion;
import uk.ac.herc.bcra.domain.*; // for static metamodels
import uk.ac.herc.bcra.repository.QuestionGroupQuestionRepository;
import uk.ac.herc.bcra.service.dto.QuestionGroupQuestionCriteria;
import uk.ac.herc.bcra.service.dto.QuestionGroupQuestionDTO;
import uk.ac.herc.bcra.service.mapper.QuestionGroupQuestionMapper;

/**
 * Service for executing complex queries for {@link QuestionGroupQuestion} entities in the database.
 * The main input is a {@link QuestionGroupQuestionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QuestionGroupQuestionDTO} or a {@link Page} of {@link QuestionGroupQuestionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QuestionGroupQuestionQueryService extends QueryService<QuestionGroupQuestion> {

    private final Logger log = LoggerFactory.getLogger(QuestionGroupQuestionQueryService.class);

    private final QuestionGroupQuestionRepository questionGroupQuestionRepository;

    private final QuestionGroupQuestionMapper questionGroupQuestionMapper;

    public QuestionGroupQuestionQueryService(QuestionGroupQuestionRepository questionGroupQuestionRepository, QuestionGroupQuestionMapper questionGroupQuestionMapper) {
        this.questionGroupQuestionRepository = questionGroupQuestionRepository;
        this.questionGroupQuestionMapper = questionGroupQuestionMapper;
    }

    /**
     * Return a {@link List} of {@link QuestionGroupQuestionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QuestionGroupQuestionDTO> findByCriteria(QuestionGroupQuestionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<QuestionGroupQuestion> specification = createSpecification(criteria);
        return questionGroupQuestionMapper.toDto(questionGroupQuestionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link QuestionGroupQuestionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QuestionGroupQuestionDTO> findByCriteria(QuestionGroupQuestionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<QuestionGroupQuestion> specification = createSpecification(criteria);
        return questionGroupQuestionRepository.findAll(specification, page)
            .map(questionGroupQuestionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QuestionGroupQuestionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<QuestionGroupQuestion> specification = createSpecification(criteria);
        return questionGroupQuestionRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<QuestionGroupQuestion> createSpecification(QuestionGroupQuestionCriteria criteria) {
        Specification<QuestionGroupQuestion> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), QuestionGroupQuestion_.id));
            }
            if (criteria.getUuid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUuid(), QuestionGroupQuestion_.uuid));
            }
            if (criteria.getQuestionGroupUuid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestionGroupUuid(), QuestionGroupQuestion_.questionGroupUuid));
            }
            if (criteria.getQuestionUuid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestionUuid(), QuestionGroupQuestion_.questionUuid));
            }
            if (criteria.getOrder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrder(), QuestionGroupQuestion_.order));
            }
            if (criteria.getQuestionGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuestionGroupId(),
                    root -> root.join(QuestionGroupQuestion_.questionGroup, JoinType.LEFT).get(QuestionGroup_.id)));
            }
            if (criteria.getQuestionId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuestionId(),
                    root -> root.join(QuestionGroupQuestion_.question, JoinType.LEFT).get(Question_.id)));
            }
        }
        return specification;
    }
}
