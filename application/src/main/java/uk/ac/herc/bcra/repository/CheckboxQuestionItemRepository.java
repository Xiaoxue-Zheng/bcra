package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.CheckboxQuestionItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CheckboxQuestionItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckboxQuestionItemRepository extends JpaRepository<CheckboxQuestionItem, Long>, JpaSpecificationExecutor<CheckboxQuestionItem> {

}
