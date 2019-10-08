package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.AnswerItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AnswerItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnswerItemRepository extends JpaRepository<AnswerItem, Long> {

}
