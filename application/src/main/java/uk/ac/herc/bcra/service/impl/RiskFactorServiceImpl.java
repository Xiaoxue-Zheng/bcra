package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.RiskFactorService;
import uk.ac.herc.bcra.domain.RiskFactor;
import uk.ac.herc.bcra.repository.RiskFactorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link RiskFactor}.
 */
@Service
@Transactional
public class RiskFactorServiceImpl implements RiskFactorService {

    private final Logger log = LoggerFactory.getLogger(RiskFactorServiceImpl.class);

    private final RiskFactorRepository riskFactorRepository;

    public RiskFactorServiceImpl(RiskFactorRepository riskFactorRepository) {
        this.riskFactorRepository = riskFactorRepository;
    }

    /**
     * Save a riskFactor.
     *
     * @param riskFactor the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RiskFactor save(RiskFactor riskFactor) {
        log.debug("Request to save RiskFactor : {}", riskFactor);
        return riskFactorRepository.save(riskFactor);
    }

    /**
     * Get all the riskFactors.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RiskFactor> findAll() {
        log.debug("Request to get all RiskFactors");
        return riskFactorRepository.findAll();
    }

    /**
     * Get one riskFactor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RiskFactor> findOne(Long id) {
        log.debug("Request to get RiskFactor : {}", id);
        return riskFactorRepository.findById(id);
    }

    /**
     * Delete the riskFactor by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RiskFactor : {}", id);
        riskFactorRepository.deleteById(id);
    }
}
