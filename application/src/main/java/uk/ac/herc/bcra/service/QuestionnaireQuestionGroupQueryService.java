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

import uk.ac.herc.bcra.domain.QuestionnaireQuestionGroup;
import uk.ac.herc.bcra.domain.*; // for static metamodels
import uk.ac.herc.bcra.repository.QuestionnaireQuestionGroupRepository;
import uk.ac.herc.bcra.service.dto.QuestionnaireQuestionGroupCriteria;
import uk.ac.herc.bcra.service.dto.QuestionnaireQuestionGroupDTO;
import uk.ac.herc.bcra.service.mapper.QuestionnaireQuestionGroupMapper;

/**
 * Service for executing complex queries for {@link QuestionnaireQuestionGroup} entities in the database.
 * The main input is a {@link QuestionnaireQuestionGroupCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QuestionnaireQuestionGroupDTO} or a {@link Page} of {@link QuestionnaireQuestionGroupDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QuestionnaireQuestionGroupQueryService extends QueryService<QuestionnaireQuestionGroup> {

    private final Logger log = LoggerFactory.getLogger(QuestionnaireQuestionGroupQueryService.class);

    private final QuestionnaireQuestionGroupRepository questionnaireQuestionGroupRepository;

    private final QuestionnaireQuestionGroupMapper questionnaireQuestionGroupMapper;

    public QuestionnaireQuestionGroupQueryService(QuestionnaireQuestionGroupRepository questionnaireQuestionGroupRepository, QuestionnaireQuestionGroupMapper questionnaireQuestionGroupMapper) {
        this.questionnaireQuestionGroupRepository = questionnaireQuestionGroupRepository;
        this.questionnaireQuestionGroupMapper = questionnaireQuestionGroupMapper;
    }

    /**
     * Return a {@link List} of {@link QuestionnaireQuestionGroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QuestionnaireQuestionGroupDTO> findByCriteria(QuestionnaireQuestionGroupCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<QuestionnaireQuestionGroup> specification = createSpecification(criteria);
        return questionnaireQuestionGroupMapper.toDto(questionnaireQuestionGroupRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link QuestionnaireQuestionGroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QuestionnaireQuestionGroupDTO> findByCriteria(QuestionnaireQuestionGroupCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<QuestionnaireQuestionGroup> specification = createSpecification(criteria);
        return questionnaireQuestionGroupRepository.findAll(specification, page)
            .map(questionnaireQuestionGroupMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QuestionnaireQuestionGroupCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<QuestionnaireQuestionGroup> specification = createSpecification(criteria);
        return questionnaireQuestionGroupRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<QuestionnaireQuestionGroup> createSpecification(QuestionnaireQuestionGroupCriteria criteria) {
        Specification<QuestionnaireQuestionGroup> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), QuestionnaireQuestionGroup_.id));
            }
            if (criteria.getUuid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUuid(), QuestionnaireQuestionGroup_.uuid));
            }
            if (criteria.getOrder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrder(), QuestionnaireQuestionGroup_.order));
            }
            if (criteria.getQuestionnaireId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuestionnaireId(),
                    root -> root.join(QuestionnaireQuestionGroup_.questionnaire, JoinType.LEFT).get(Questionnaire_.id)));
            }
            if (criteria.getQuestionGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuestionGroupId(),
                    root -> root.join(QuestionnaireQuestionGroup_.questionGroup, JoinType.LEFT).get(QuestionGroup_.id)));
            }
        }
        return specification;
    }
}
