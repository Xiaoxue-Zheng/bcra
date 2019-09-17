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

import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.domain.*; // for static metamodels
import uk.ac.herc.bcra.repository.AnswerResponseRepository;
import uk.ac.herc.bcra.service.dto.AnswerResponseCriteria;
import uk.ac.herc.bcra.service.dto.AnswerResponseDTO;
import uk.ac.herc.bcra.service.mapper.AnswerResponseMapper;

/**
 * Service for executing complex queries for {@link AnswerResponse} entities in the database.
 * The main input is a {@link AnswerResponseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AnswerResponseDTO} or a {@link Page} of {@link AnswerResponseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AnswerResponseQueryService extends QueryService<AnswerResponse> {

    private final Logger log = LoggerFactory.getLogger(AnswerResponseQueryService.class);

    private final AnswerResponseRepository answerResponseRepository;

    private final AnswerResponseMapper answerResponseMapper;

    public AnswerResponseQueryService(AnswerResponseRepository answerResponseRepository, AnswerResponseMapper answerResponseMapper) {
        this.answerResponseRepository = answerResponseRepository;
        this.answerResponseMapper = answerResponseMapper;
    }

    /**
     * Return a {@link List} of {@link AnswerResponseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AnswerResponseDTO> findByCriteria(AnswerResponseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AnswerResponse> specification = createSpecification(criteria);
        return answerResponseMapper.toDto(answerResponseRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AnswerResponseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AnswerResponseDTO> findByCriteria(AnswerResponseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AnswerResponse> specification = createSpecification(criteria);
        return answerResponseRepository.findAll(specification, page)
            .map(answerResponseMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AnswerResponseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AnswerResponse> specification = createSpecification(criteria);
        return answerResponseRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<AnswerResponse> createSpecification(AnswerResponseCriteria criteria) {
        Specification<AnswerResponse> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AnswerResponse_.id));
            }
            if (criteria.getQuestionnaireId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuestionnaireId(),
                    root -> root.join(AnswerResponse_.questionnaire, JoinType.LEFT).get(Questionnaire_.id)));
            }
            if (criteria.getAnswerGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnswerGroupId(),
                    root -> root.join(AnswerResponse_.answerGroups, JoinType.LEFT).get(AnswerGroup_.id)));
            }
        }
        return specification;
    }
}
