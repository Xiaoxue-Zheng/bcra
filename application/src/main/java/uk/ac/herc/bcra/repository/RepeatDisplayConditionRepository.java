package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.RepeatDisplayCondition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RepeatDisplayCondition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RepeatDisplayConditionRepository extends JpaRepository<RepeatDisplayCondition, Long>, JpaSpecificationExecutor<RepeatDisplayCondition> {

}
