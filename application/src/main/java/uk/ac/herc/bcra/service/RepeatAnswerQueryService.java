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

import uk.ac.herc.bcra.domain.RepeatAnswer;
import uk.ac.herc.bcra.domain.*; // for static metamodels
import uk.ac.herc.bcra.repository.RepeatAnswerRepository;
import uk.ac.herc.bcra.service.dto.RepeatAnswerCriteria;
import uk.ac.herc.bcra.service.dto.RepeatAnswerDTO;
import uk.ac.herc.bcra.service.mapper.RepeatAnswerMapper;

/**
 * Service for executing complex queries for {@link RepeatAnswer} entities in the database.
 * The main input is a {@link RepeatAnswerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RepeatAnswerDTO} or a {@link Page} of {@link RepeatAnswerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RepeatAnswerQueryService extends QueryService<RepeatAnswer> {

    private final Logger log = LoggerFactory.getLogger(RepeatAnswerQueryService.class);

    private final RepeatAnswerRepository repeatAnswerRepository;

    private final RepeatAnswerMapper repeatAnswerMapper;

    public RepeatAnswerQueryService(RepeatAnswerRepository repeatAnswerRepository, RepeatAnswerMapper repeatAnswerMapper) {
        this.repeatAnswerRepository = repeatAnswerRepository;
        this.repeatAnswerMapper = repeatAnswerMapper;
    }

    /**
     * Return a {@link List} of {@link RepeatAnswerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RepeatAnswerDTO> findByCriteria(RepeatAnswerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RepeatAnswer> specification = createSpecification(criteria);
        return repeatAnswerMapper.toDto(repeatAnswerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RepeatAnswerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RepeatAnswerDTO> findByCriteria(RepeatAnswerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RepeatAnswer> specification = createSpecification(criteria);
        return repeatAnswerRepository.findAll(specification, page)
            .map(repeatAnswerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RepeatAnswerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RepeatAnswer> specification = createSpecification(criteria);
        return repeatAnswerRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<RepeatAnswer> createSpecification(RepeatAnswerCriteria criteria) {
        Specification<RepeatAnswer> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), RepeatAnswer_.id));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), RepeatAnswer_.quantity));
            }
        }
        return specification;
    }
}
