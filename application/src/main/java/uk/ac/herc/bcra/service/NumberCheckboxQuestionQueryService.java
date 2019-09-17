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

import uk.ac.herc.bcra.domain.NumberCheckboxQuestion;
import uk.ac.herc.bcra.domain.*; // for static metamodels
import uk.ac.herc.bcra.repository.NumberCheckboxQuestionRepository;
import uk.ac.herc.bcra.service.dto.NumberCheckboxQuestionCriteria;
import uk.ac.herc.bcra.service.dto.NumberCheckboxQuestionDTO;
import uk.ac.herc.bcra.service.mapper.NumberCheckboxQuestionMapper;

/**
 * Service for executing complex queries for {@link NumberCheckboxQuestion} entities in the database.
 * The main input is a {@link NumberCheckboxQuestionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NumberCheckboxQuestionDTO} or a {@link Page} of {@link NumberCheckboxQuestionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NumberCheckboxQuestionQueryService extends QueryService<NumberCheckboxQuestion> {

    private final Logger log = LoggerFactory.getLogger(NumberCheckboxQuestionQueryService.class);

    private final NumberCheckboxQuestionRepository numberCheckboxQuestionRepository;

    private final NumberCheckboxQuestionMapper numberCheckboxQuestionMapper;

    public NumberCheckboxQuestionQueryService(NumberCheckboxQuestionRepository numberCheckboxQuestionRepository, NumberCheckboxQuestionMapper numberCheckboxQuestionMapper) {
        this.numberCheckboxQuestionRepository = numberCheckboxQuestionRepository;
        this.numberCheckboxQuestionMapper = numberCheckboxQuestionMapper;
    }

    /**
     * Return a {@link List} of {@link NumberCheckboxQuestionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NumberCheckboxQuestionDTO> findByCriteria(NumberCheckboxQuestionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NumberCheckboxQuestion> specification = createSpecification(criteria);
        return numberCheckboxQuestionMapper.toDto(numberCheckboxQuestionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NumberCheckboxQuestionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NumberCheckboxQuestionDTO> findByCriteria(NumberCheckboxQuestionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NumberCheckboxQuestion> specification = createSpecification(criteria);
        return numberCheckboxQuestionRepository.findAll(specification, page)
            .map(numberCheckboxQuestionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NumberCheckboxQuestionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NumberCheckboxQuestion> specification = createSpecification(criteria);
        return numberCheckboxQuestionRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<NumberCheckboxQuestion> createSpecification(NumberCheckboxQuestionCriteria criteria) {
        Specification<NumberCheckboxQuestion> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), NumberCheckboxQuestion_.id));
            }
            if (criteria.getMinimum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinimum(), NumberCheckboxQuestion_.minimum));
            }
            if (criteria.getMaximum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaximum(), NumberCheckboxQuestion_.maximum));
            }
        }
        return specification;
    }
}
