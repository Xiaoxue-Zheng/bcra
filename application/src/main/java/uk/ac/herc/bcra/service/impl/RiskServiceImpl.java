package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.RiskService;
import uk.ac.herc.bcra.domain.Risk;
import uk.ac.herc.bcra.repository.RiskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Risk}.
 */
@Service
@Transactional
public class RiskServiceImpl implements RiskService {

    private final Logger log = LoggerFactory.getLogger(RiskServiceImpl.class);

    private final RiskRepository riskRepository;

    public RiskServiceImpl(RiskRepository riskRepository) {
        this.riskRepository = riskRepository;
    }

    /**
     * Save a risk.
     *
     * @param risk the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Risk save(Risk risk) {
        log.debug("Request to save Risk : {}", risk);
        return riskRepository.save(risk);
    }

    /**
     * Get all the risks.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Risk> findAll() {
        log.debug("Request to get all Risks");
        return riskRepository.findAll();
    }

    /**
     * Get one risk by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Risk> findOne(Long id) {
        log.debug("Request to get Risk : {}", id);
        return riskRepository.findById(id);
    }

    /**
     * Delete the risk by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Risk : {}", id);
        riskRepository.deleteById(id);
    }
}
