package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.CheckboxAnswerItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CheckboxAnswerItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckboxAnswerItemRepository extends JpaRepository<CheckboxAnswerItem, Long>, JpaSpecificationExecutor<CheckboxAnswerItem> {

}
