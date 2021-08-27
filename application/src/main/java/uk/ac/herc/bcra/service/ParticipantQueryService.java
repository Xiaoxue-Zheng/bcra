package uk.ac.herc.bcra.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.JoinType;

import org.apache.commons.lang3.StringUtils;
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
import uk.ac.herc.bcra.service.dto.CanRiskReportDTO;
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

    private final CanRiskReportService canRiskReportService;

    public ParticipantQueryService(ParticipantRepository participantRepository
        , ParticipantMapper participantMapper, CanRiskReportService canRiskReportService
    ) {
        this.participantRepository = participantRepository;
        this.participantMapper = participantMapper;
        this.canRiskReportService = canRiskReportService;
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
            .map((participant)->{
                ParticipantDTO participantDTO = participantMapper.toDto(participant);
                Optional<CanRiskReportDTO> canRiskReportOptional = canRiskReportService.findOneByAssociatedStudyId(participant.getStudyId());
                canRiskReportOptional.ifPresent(canRiskReportDTO -> participantDTO.setCanRiskReportId(canRiskReportDTO.getId()));
                return participantDTO;
            });
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
            if (criteria.getStudyId() != null && StringUtils.isNotEmpty(criteria.getStudyId().getEquals())) {
                specification = specification.and(buildSpecification(criteria.getStudyId(),
                    root -> root.join(Participant_.studyId, JoinType.LEFT).get(StudyId_.code)));
            }
            if (criteria.getDateOfBirth() != null) {
                specification = specification.and(buildSpecification(criteria.getDateOfBirth(),
                    root -> root.get(Participant_.dateOfBirth)));
            }
            if(criteria.getStatus() != null && StringUtils.isNotEmpty(criteria.getStatus().getEquals())) {
                specification = specification.and(buildSpecification(criteria.getStatus(),
                    root -> root.get(Participant_.status)));
            }

        }
        return specification;
    }
}
