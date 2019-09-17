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

import uk.ac.herc.bcra.domain.CheckboxQuestion;
import uk.ac.herc.bcra.domain.*; // for static metamodels
import uk.ac.herc.bcra.repository.CheckboxQuestionRepository;
import uk.ac.herc.bcra.service.dto.CheckboxQuestionCriteria;
import uk.ac.herc.bcra.service.dto.CheckboxQuestionDTO;
import uk.ac.herc.bcra.service.mapper.CheckboxQuestionMapper;

/**
 * Service for executing complex queries for {@link CheckboxQuestion} entities in the database.
 * The main input is a {@link CheckboxQuestionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CheckboxQuestionDTO} or a {@link Page} of {@link CheckboxQuestionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CheckboxQuestionQueryService extends QueryService<CheckboxQuestion> {

    private final Logger log = LoggerFactory.getLogger(CheckboxQuestionQueryService.class);

    private final CheckboxQuestionRepository checkboxQuestionRepository;

    private final CheckboxQuestionMapper checkboxQuestionMapper;

    public CheckboxQuestionQueryService(CheckboxQuestionRepository checkboxQuestionRepository, CheckboxQuestionMapper checkboxQuestionMapper) {
        this.checkboxQuestionRepository = checkboxQuestionRepository;
        this.checkboxQuestionMapper = checkboxQuestionMapper;
    }

    /**
     * Return a {@link List} of {@link CheckboxQuestionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CheckboxQuestionDTO> findByCriteria(CheckboxQuestionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CheckboxQuestion> specification = createSpecification(criteria);
        return checkboxQuestionMapper.toDto(checkboxQuestionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CheckboxQuestionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckboxQuestionDTO> findByCriteria(CheckboxQuestionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CheckboxQuestion> specification = createSpecification(criteria);
        return checkboxQuestionRepository.findAll(specification, page)
            .map(checkboxQuestionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CheckboxQuestionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CheckboxQuestion> specification = createSpecification(criteria);
        return checkboxQuestionRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<CheckboxQuestion> createSpecification(CheckboxQuestionCriteria criteria) {
        Specification<CheckboxQuestion> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CheckboxQuestion_.id));
            }
            if (criteria.getCheckboxQuestionItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getCheckboxQuestionItemId(),
                    root -> root.join(CheckboxQuestion_.checkboxQuestionItems, JoinType.LEFT).get(CheckboxQuestionItem_.id)));
            }
        }
        return specification;
    }
}
