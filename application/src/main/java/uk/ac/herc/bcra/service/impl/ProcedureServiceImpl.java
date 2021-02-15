package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.ProcedureService;
import uk.ac.herc.bcra.domain.Procedure;
import uk.ac.herc.bcra.repository.ProcedureRepository;
import uk.ac.herc.bcra.service.dto.ProcedureDTO;
import uk.ac.herc.bcra.service.mapper.ProcedureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Procedure}.
 */
@Service
@Transactional
public class ProcedureServiceImpl implements ProcedureService {

    private final Logger log = LoggerFactory.getLogger(ProcedureServiceImpl.class);

    private final ProcedureRepository procedureRepository;

    private final ProcedureMapper procedureMapper;

    public ProcedureServiceImpl(ProcedureRepository procedureRepository, ProcedureMapper procedureMapper) {
        this.procedureRepository = procedureRepository;
        this.procedureMapper = procedureMapper;
    }

    /**
     * Save a procedure.
     *
     * @param procedureDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProcedureDTO save(ProcedureDTO procedureDTO) {
        log.debug("Request to save Procedure : {}", procedureDTO);
        Procedure procedure = procedureMapper.toEntity(procedureDTO);
        procedure = procedureRepository.save(procedure);
        return procedureMapper.toDto(procedure);
    }

    @Override
    public Procedure save(Procedure procedure) {
        return procedureRepository.save(procedure);
    }

    /**
     * Get all the procedures.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProcedureDTO> findAll() {
        log.debug("Request to get all Procedures");
        return procedureRepository.findAll().stream()
            .map(procedureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
    *  Get all the procedures where Participant is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<ProcedureDTO> findAllWhereParticipantIsNull() {
        log.debug("Request to get all procedures where Participant is null");
        return StreamSupport
            .stream(procedureRepository.findAll().spliterator(), false)
            .filter(procedure -> procedure.getParticipant() == null)
            .map(procedureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one procedure by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProcedureDTO> findOne(Long id) {
        log.debug("Request to get Procedure : {}", id);
        return procedureRepository.findById(id)
            .map(procedureMapper::toDto);
    }

    /**
     * Delete the procedure by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Procedure : {}", id);
        procedureRepository.deleteById(id);
    }
}
