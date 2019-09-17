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

import uk.ac.herc.bcra.domain.RadioAnswer;
import uk.ac.herc.bcra.domain.*; // for static metamodels
import uk.ac.herc.bcra.repository.RadioAnswerRepository;
import uk.ac.herc.bcra.service.dto.RadioAnswerCriteria;
import uk.ac.herc.bcra.service.dto.RadioAnswerDTO;
import uk.ac.herc.bcra.service.mapper.RadioAnswerMapper;

/**
 * Service for executing complex queries for {@link RadioAnswer} entities in the database.
 * The main input is a {@link RadioAnswerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RadioAnswerDTO} or a {@link Page} of {@link RadioAnswerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RadioAnswerQueryService extends QueryService<RadioAnswer> {

    private final Logger log = LoggerFactory.getLogger(RadioAnswerQueryService.class);

    private final RadioAnswerRepository radioAnswerRepository;

    private final RadioAnswerMapper radioAnswerMapper;

    public RadioAnswerQueryService(RadioAnswerRepository radioAnswerRepository, RadioAnswerMapper radioAnswerMapper) {
        this.radioAnswerRepository = radioAnswerRepository;
        this.radioAnswerMapper = radioAnswerMapper;
    }

    /**
     * Return a {@link List} of {@link RadioAnswerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RadioAnswerDTO> findByCriteria(RadioAnswerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RadioAnswer> specification = createSpecification(criteria);
        return radioAnswerMapper.toDto(radioAnswerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RadioAnswerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RadioAnswerDTO> findByCriteria(RadioAnswerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RadioAnswer> specification = createSpecification(criteria);
        return radioAnswerRepository.findAll(specification, page)
            .map(radioAnswerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RadioAnswerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RadioAnswer> specification = createSpecification(criteria);
        return radioAnswerRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<RadioAnswer> createSpecification(RadioAnswerCriteria criteria) {
        Specification<RadioAnswer> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), RadioAnswer_.id));
            }
            if (criteria.getRadioAnswerItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getRadioAnswerItemId(),
                    root -> root.join(RadioAnswer_.radioAnswerItem, JoinType.LEFT).get(RadioAnswerItem_.id)));
            }
        }
        return specification;
    }
}
