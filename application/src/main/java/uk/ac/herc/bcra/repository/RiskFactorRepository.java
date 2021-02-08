package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.RiskFactor;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RiskFactor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiskFactorRepository extends JpaRepository<RiskFactor, Long> {

}
