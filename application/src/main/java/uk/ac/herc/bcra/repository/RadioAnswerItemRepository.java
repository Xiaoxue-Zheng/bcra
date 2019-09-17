package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.RadioAnswerItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RadioAnswerItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RadioAnswerItemRepository extends JpaRepository<RadioAnswerItem, Long>, JpaSpecificationExecutor<RadioAnswerItem> {

}
