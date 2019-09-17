package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.RadioQuestionItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RadioQuestionItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RadioQuestionItemRepository extends JpaRepository<RadioQuestionItem, Long>, JpaSpecificationExecutor<RadioQuestionItem> {

}
