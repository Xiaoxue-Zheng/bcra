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

import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.domain.*; // for static metamodels
import uk.ac.herc.bcra.repository.ParticipantRepository;
import uk.ac.herc.bcra.service.dto.ParticipantCriteria;
import uk.ac.herc.bcra.service.dto.ParticipantDTO;
import uk.ac.herc.bcra.service.mapper.ParticipantMapper;

/**
 * Service for executing complex queries for {@link Participant} entities in the database.
 * The main input is a {@link ParticipantCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ParticipantDTO} or a {@link Page} of {@link ParticipantDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ParticipantQueryService extends QueryService<Participant> {

    private final Logger log = LoggerFactory.getLogger(ParticipantQueryService.class);

    private final ParticipantRepository participantRepository;

    private final ParticipantMapper participantMapper;

    public ParticipantQueryService(ParticipantRepository participantRepository, ParticipantMapper participantMapper) {
        this.participantRepository = participantRepository;
        this.participantMapper = participantMapper;
    }

    /**
     * Return a {@link List} of {@link ParticipantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ParticipantDTO> findByCriteria(ParticipantCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Participant> specification = createSpecification(criteria);
        return participantMapper.toDto(participantRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ParticipantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ParticipantDTO> findByCriteria(ParticipantCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Participant> specification = createSpecification(criteria);
        return participantRepository.findAll(specification, page)
            .map(participantMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ParticipantCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Participant> specification = createSpecification(criteria);
        return participantRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<Participant> createSpecification(ParticipantCriteria criteria) {
        Specification<Participant> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Participant_.id));
            }
            if (criteria.getRegisterDatetime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegisterDatetime(), Participant_.registerDatetime));
            }
            if (criteria.getLastLoginDatetime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastLoginDatetime(), Participant_.lastLoginDatetime));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Participant_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getIdentifiableDataId() != null) {
                specification = specification.and(buildSpecification(criteria.getIdentifiableDataId(),
                    root -> root.join(Participant_.identifiableData, JoinType.LEFT).get(IdentifiableData_.id)));
            }
            if (criteria.getProcedureId() != null) {
                specification = specification.and(buildSpecification(criteria.getProcedureId(),
                    root -> root.join(Participant_.procedure, JoinType.LEFT).get(Procedure_.id)));
            }
            if (criteria.getCsvFileId() != null) {
                specification = specification.and(buildSpecification(criteria.getCsvFileId(),
                    root -> root.join(Participant_.csvFile, JoinType.LEFT).get(CsvFile_.id)));
            }
            if (criteria.getNhsNumber() != null) {
                specification = specification.and(buildSpecification(criteria.getNhsNumber(),
                    root -> root.join(Participant_.identifiableData, JoinType.LEFT).get(IdentifiableData_.nhsNumber)));
            }
        }
        return specification;
    }
}
