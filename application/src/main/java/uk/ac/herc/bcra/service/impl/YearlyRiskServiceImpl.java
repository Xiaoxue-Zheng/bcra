package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.YearlyRiskService;
import uk.ac.herc.bcra.domain.YearlyRisk;
import uk.ac.herc.bcra.repository.YearlyRiskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link YearlyRiskFactor}.
 */
@Service
@Transactional
public class YearlyRiskServiceImpl implements YearlyRiskService {

    private final Logger log = LoggerFactory.getLogger(YearlyRiskServiceImpl.class);

    private final YearlyRiskRepository yearlyRiskRepository;

    public YearlyRiskServiceImpl(YearlyRiskRepository yearlyRiskRepository) {
        this.yearlyRiskRepository = yearlyRiskRepository;
    }

    /**
     * Save a yearlyRisk.
     *
     * @param yearlyRisk the entity to save.
     * @return the persisted entity.
     */
    @Override
    public YearlyRisk save(YearlyRisk yearlyRisk) {
        log.debug("Request to save YearlyRisk : {}", yearlyRisk);
        return yearlyRiskRepository.save(yearlyRisk);
    }

    /**
     * Get all the yearlyRisks.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<YearlyRisk> findAll() {
        log.debug("Request to get all YearlyRisks");
        return yearlyRiskRepository.findAll();
    }

    /**
     * Get one yearlyRisk by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<YearlyRisk> findOne(Long id) {
        log.debug("Request to get YearlyRisk : {}", id);
        return yearlyRiskRepository.findById(id);
    }

    /**
     * Delete the yearlyRisk by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete YearlyRisk : {}", id);
        yearlyRiskRepository.deleteById(id);
    }
}
