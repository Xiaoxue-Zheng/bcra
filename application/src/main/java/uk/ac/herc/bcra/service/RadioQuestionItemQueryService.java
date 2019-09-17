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

import uk.ac.herc.bcra.domain.RadioQuestionItem;
import uk.ac.herc.bcra.domain.*; // for static metamodels
import uk.ac.herc.bcra.repository.RadioQuestionItemRepository;
import uk.ac.herc.bcra.service.dto.RadioQuestionItemCriteria;
import uk.ac.herc.bcra.service.dto.RadioQuestionItemDTO;
import uk.ac.herc.bcra.service.mapper.RadioQuestionItemMapper;

/**
 * Service for executing complex queries for {@link RadioQuestionItem} entities in the database.
 * The main input is a {@link RadioQuestionItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RadioQuestionItemDTO} or a {@link Page} of {@link RadioQuestionItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RadioQuestionItemQueryService extends QueryService<RadioQuestionItem> {

    private final Logger log = LoggerFactory.getLogger(RadioQuestionItemQueryService.class);

    private final RadioQuestionItemRepository radioQuestionItemRepository;

    private final RadioQuestionItemMapper radioQuestionItemMapper;

    public RadioQuestionItemQueryService(RadioQuestionItemRepository radioQuestionItemRepository, RadioQuestionItemMapper radioQuestionItemMapper) {
        this.radioQuestionItemRepository = radioQuestionItemRepository;
        this.radioQuestionItemMapper = radioQuestionItemMapper;
    }

    /**
     * Return a {@link List} of {@link RadioQuestionItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RadioQuestionItemDTO> findByCriteria(RadioQuestionItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RadioQuestionItem> specification = createSpecification(criteria);
        return radioQuestionItemMapper.toDto(radioQuestionItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RadioQuestionItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RadioQuestionItemDTO> findByCriteria(RadioQuestionItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RadioQuestionItem> specification = createSpecification(criteria);
        return radioQuestionItemRepository.findAll(specification, page)
            .map(radioQuestionItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RadioQuestionItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RadioQuestionItem> specification = createSpecification(criteria);
        return radioQuestionItemRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<RadioQuestionItem> createSpecification(RadioQuestionItemCriteria criteria) {
        Specification<RadioQuestionItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), RadioQuestionItem_.id));
            }
            if (criteria.getUuid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUuid(), RadioQuestionItem_.uuid));
            }
            if (criteria.getQuestionUuid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestionUuid(), RadioQuestionItem_.questionUuid));
            }
            if (criteria.getLabel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLabel(), RadioQuestionItem_.label));
            }
            if (criteria.getDescriptor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescriptor(), RadioQuestionItem_.descriptor));
            }
            if (criteria.getRadioQuestionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRadioQuestionId(),
                    root -> root.join(RadioQuestionItem_.radioQuestion, JoinType.LEFT).get(RadioQuestion_.id)));
            }
        }
        return specification;
    }
}
