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

import uk.ac.herc.bcra.domain.Questionnaire;
import uk.ac.herc.bcra.domain.*; // for static metamodels
import uk.ac.herc.bcra.repository.QuestionnaireRepository;
import uk.ac.herc.bcra.service.dto.QuestionnaireCriteria;
import uk.ac.herc.bcra.service.dto.QuestionnaireDTO;
import uk.ac.herc.bcra.service.mapper.QuestionnaireMapper;

/**
 * Service for executing complex queries for {@link Questionnaire} entities in the database.
 * The main input is a {@link QuestionnaireCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QuestionnaireDTO} or a {@link Page} of {@link QuestionnaireDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QuestionnaireQueryService extends QueryService<Questionnaire> {

    private final Logger log = LoggerFactory.getLogger(QuestionnaireQueryService.class);

    private final QuestionnaireRepository questionnaireRepository;

    private final QuestionnaireMapper questionnaireMapper;

    public QuestionnaireQueryService(QuestionnaireRepository questionnaireRepository, QuestionnaireMapper questionnaireMapper) {
        this.questionnaireRepository = questionnaireRepository;
        this.questionnaireMapper = questionnaireMapper;
    }

    /**
     * Return a {@link List} of {@link QuestionnaireDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QuestionnaireDTO> findByCriteria(QuestionnaireCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Questionnaire> specification = createSpecification(criteria);
        return questionnaireMapper.toDto(questionnaireRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link QuestionnaireDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QuestionnaireDTO> findByCriteria(QuestionnaireCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Questionnaire> specification = createSpecification(criteria);
        return questionnaireRepository.findAll(specification, page)
            .map(questionnaireMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QuestionnaireCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Questionnaire> specification = createSpecification(criteria);
        return questionnaireRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<Questionnaire> createSpecification(QuestionnaireCriteria criteria) {
        Specification<Questionnaire> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Questionnaire_.id));
            }
            if (criteria.getUuid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUuid(), Questionnaire_.uuid));
            }
            if (criteria.getQuestionnaireQuestionGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuestionnaireQuestionGroupId(),
                    root -> root.join(Questionnaire_.questionnaireQuestionGroups, JoinType.LEFT).get(QuestionnaireQuestionGroup_.id)));
            }
            if (criteria.getAnswerResponseId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnswerResponseId(),
                    root -> root.join(Questionnaire_.answerResponses, JoinType.LEFT).get(AnswerResponse_.id)));
            }
        }
        return specification;
    }
}
