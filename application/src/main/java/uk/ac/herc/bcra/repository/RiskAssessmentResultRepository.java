package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.RiskAssessmentResult;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RiskAssessmentResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiskAssessmentResultRepository extends JpaRepository<RiskAssessmentResult, Long> {

}
