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

import uk.ac.herc.bcra.domain.NumberCheckboxAnswer;
import uk.ac.herc.bcra.domain.*; // for static metamodels
import uk.ac.herc.bcra.repository.NumberCheckboxAnswerRepository;
import uk.ac.herc.bcra.service.dto.NumberCheckboxAnswerCriteria;
import uk.ac.herc.bcra.service.dto.NumberCheckboxAnswerDTO;
import uk.ac.herc.bcra.service.mapper.NumberCheckboxAnswerMapper;

/**
 * Service for executing complex queries for {@link NumberCheckboxAnswer} entities in the database.
 * The main input is a {@link NumberCheckboxAnswerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NumberCheckboxAnswerDTO} or a {@link Page} of {@link NumberCheckboxAnswerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NumberCheckboxAnswerQueryService extends QueryService<NumberCheckboxAnswer> {

    private final Logger log = LoggerFactory.getLogger(NumberCheckboxAnswerQueryService.class);

    private final NumberCheckboxAnswerRepository numberCheckboxAnswerRepository;

    private final NumberCheckboxAnswerMapper numberCheckboxAnswerMapper;

    public NumberCheckboxAnswerQueryService(NumberCheckboxAnswerRepository numberCheckboxAnswerRepository, NumberCheckboxAnswerMapper numberCheckboxAnswerMapper) {
        this.numberCheckboxAnswerRepository = numberCheckboxAnswerRepository;
        this.numberCheckboxAnswerMapper = numberCheckboxAnswerMapper;
    }

    /**
     * Return a {@link List} of {@link NumberCheckboxAnswerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NumberCheckboxAnswerDTO> findByCriteria(NumberCheckboxAnswerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NumberCheckboxAnswer> specification = createSpecification(criteria);
        return numberCheckboxAnswerMapper.toDto(numberCheckboxAnswerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NumberCheckboxAnswerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NumberCheckboxAnswerDTO> findByCriteria(NumberCheckboxAnswerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NumberCheckboxAnswer> specification = createSpecification(criteria);
        return numberCheckboxAnswerRepository.findAll(specification, page)
            .map(numberCheckboxAnswerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NumberCheckboxAnswerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NumberCheckboxAnswer> specification = createSpecification(criteria);
        return numberCheckboxAnswerRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<NumberCheckboxAnswer> createSpecification(NumberCheckboxAnswerCriteria criteria) {
        Specification<NumberCheckboxAnswer> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), NumberCheckboxAnswer_.id));
            }
            if (criteria.getNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumber(), NumberCheckboxAnswer_.number));
            }
            if (criteria.getCheck() != null) {
                specification = specification.and(buildSpecification(criteria.getCheck(), NumberCheckboxAnswer_.check));
            }
        }
        return specification;
    }
}
