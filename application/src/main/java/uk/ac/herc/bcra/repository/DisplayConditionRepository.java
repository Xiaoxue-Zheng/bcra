package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.DisplayCondition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DisplayCondition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DisplayConditionRepository extends JpaRepository<DisplayCondition, Long> {

}
