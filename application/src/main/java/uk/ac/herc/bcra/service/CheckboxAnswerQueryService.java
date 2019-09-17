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

import uk.ac.herc.bcra.domain.CheckboxAnswer;
import uk.ac.herc.bcra.domain.*; // for static metamodels
import uk.ac.herc.bcra.repository.CheckboxAnswerRepository;
import uk.ac.herc.bcra.service.dto.CheckboxAnswerCriteria;
import uk.ac.herc.bcra.service.dto.CheckboxAnswerDTO;
import uk.ac.herc.bcra.service.mapper.CheckboxAnswerMapper;

/**
 * Service for executing complex queries for {@link CheckboxAnswer} entities in the database.
 * The main input is a {@link CheckboxAnswerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CheckboxAnswerDTO} or a {@link Page} of {@link CheckboxAnswerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CheckboxAnswerQueryService extends QueryService<CheckboxAnswer> {

    private final Logger log = LoggerFactory.getLogger(CheckboxAnswerQueryService.class);

    private final CheckboxAnswerRepository checkboxAnswerRepository;

    private final CheckboxAnswerMapper checkboxAnswerMapper;

    public CheckboxAnswerQueryService(CheckboxAnswerRepository checkboxAnswerRepository, CheckboxAnswerMapper checkboxAnswerMapper) {
        this.checkboxAnswerRepository = checkboxAnswerRepository;
        this.checkboxAnswerMapper = checkboxAnswerMapper;
    }

    /**
     * Return a {@link List} of {@link CheckboxAnswerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CheckboxAnswerDTO> findByCriteria(CheckboxAnswerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CheckboxAnswer> specification = createSpecification(criteria);
        return checkboxAnswerMapper.toDto(checkboxAnswerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CheckboxAnswerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckboxAnswerDTO> findByCriteria(CheckboxAnswerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CheckboxAnswer> specification = createSpecification(criteria);
        return checkboxAnswerRepository.findAll(specification, page)
            .map(checkboxAnswerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CheckboxAnswerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CheckboxAnswer> specification = createSpecification(criteria);
        return checkboxAnswerRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<CheckboxAnswer> createSpecification(CheckboxAnswerCriteria criteria) {
        Specification<CheckboxAnswer> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CheckboxAnswer_.id));
            }
            if (criteria.getCheckboxAnswerItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getCheckboxAnswerItemId(),
                    root -> root.join(CheckboxAnswer_.checkboxAnswerItems, JoinType.LEFT).get(CheckboxAnswerItem_.id)));
            }
        }
        return specification;
    }
}
