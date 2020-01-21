package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.ReferralCondition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ReferralCondition entity.
 */
@Repository
public interface ReferralConditionRepository extends JpaRepository<ReferralCondition, Long> {

}
