package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.Risk;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Risk entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiskRepository extends JpaRepository<Risk, Long> {

}
