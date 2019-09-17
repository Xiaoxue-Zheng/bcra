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

import uk.ac.herc.bcra.domain.RadioQuestion;
import uk.ac.herc.bcra.domain.*; // for static metamodels
import uk.ac.herc.bcra.repository.RadioQuestionRepository;
import uk.ac.herc.bcra.service.dto.RadioQuestionCriteria;
import uk.ac.herc.bcra.service.dto.RadioQuestionDTO;
import uk.ac.herc.bcra.service.mapper.RadioQuestionMapper;

/**
 * Service for executing complex queries for {@link RadioQuestion} entities in the database.
 * The main input is a {@link RadioQuestionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RadioQuestionDTO} or a {@link Page} of {@link RadioQuestionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RadioQuestionQueryService extends QueryService<RadioQuestion> {

    private final Logger log = LoggerFactory.getLogger(RadioQuestionQueryService.class);

    private final RadioQuestionRepository radioQuestionRepository;

    private final RadioQuestionMapper radioQuestionMapper;

    public RadioQuestionQueryService(RadioQuestionRepository radioQuestionRepository, RadioQuestionMapper radioQuestionMapper) {
        this.radioQuestionRepository = radioQuestionRepository;
        this.radioQuestionMapper = radioQuestionMapper;
    }

    /**
     * Return a {@link List} of {@link RadioQuestionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RadioQuestionDTO> findByCriteria(RadioQuestionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RadioQuestion> specification = createSpecification(criteria);
        return radioQuestionMapper.toDto(radioQuestionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RadioQuestionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RadioQuestionDTO> findByCriteria(RadioQuestionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RadioQuestion> specification = createSpecification(criteria);
        return radioQuestionRepository.findAll(specification, page)
            .map(radioQuestionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RadioQuestionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RadioQuestion> specification = createSpecification(criteria);
        return radioQuestionRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<RadioQuestion> createSpecification(RadioQuestionCriteria criteria) {
        Specification<RadioQuestion> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), RadioQuestion_.id));
            }
            if (criteria.getRadioQuestionItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getRadioQuestionItemId(),
                    root -> root.join(RadioQuestion_.radioQuestionItems, JoinType.LEFT).get(RadioQuestionItem_.id)));
            }
        }
        return specification;
    }
}
