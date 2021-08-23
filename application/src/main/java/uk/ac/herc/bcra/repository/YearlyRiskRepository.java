package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.YearlyRisk;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RiskFactor entity.
 */
@Repository
public interface YearlyRiskRepository extends JpaRepository<YearlyRisk, Long> {

}
