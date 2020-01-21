package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.QuestionItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the QuestionItem entity.
 */
@Repository
public interface QuestionItemRepository extends JpaRepository<QuestionItem, Long> {

}
