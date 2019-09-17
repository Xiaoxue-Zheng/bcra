package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.CheckboxDisplayCondition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CheckboxDisplayCondition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckboxDisplayConditionRepository extends JpaRepository<CheckboxDisplayCondition, Long>, JpaSpecificationExecutor<CheckboxDisplayCondition> {

}
