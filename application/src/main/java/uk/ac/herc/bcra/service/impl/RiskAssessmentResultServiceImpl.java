package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.RiskAssessmentResultService;
import uk.ac.herc.bcra.domain.RiskAssessmentResult;
import uk.ac.herc.bcra.repository.RiskAssessmentResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link RiskAssessmentResult}.
 */
@Service
@Transactional
public class RiskAssessmentResultServiceImpl implements RiskAssessmentResultService {

    private final Logger log = LoggerFactory.getLogger(RiskAssessmentResultServiceImpl.class);

    private final RiskAssessmentResultRepository riskAssessmentResultRepository;

    public RiskAssessmentResultServiceImpl(RiskAssessmentResultRepository riskAssessmentResultRepository) {
        this.riskAssessmentResultRepository = riskAssessmentResultRepository;
    }

    /**
     * Save a riskAssessmentResult.
     *
     * @param riskAssessmentResult the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RiskAssessmentResult save(RiskAssessmentResult riskAssessmentResult) {
        log.debug("Request to save RiskAssessmentResult : {}", riskAssessmentResult);
        return riskAssessmentResultRepository.save(riskAssessmentResult);
    }

    /**
     * Get all the riskAssessmentResults.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RiskAssessmentResult> findAll() {
        log.debug("Request to get all RiskAssessmentResults");
        return riskAssessmentResultRepository.findAll();
    }

    /**
     * Get one riskAssessmentResult by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RiskAssessmentResult> findOne(Long id) {
        log.debug("Request to get RiskAssessmentResult : {}", id);
        return riskAssessmentResultRepository.findById(id);
    }

    /**
     * Delete the riskAssessmentResult by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RiskAssessmentResult : {}", id);
        riskAssessmentResultRepository.deleteById(id);
    }
}
