package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.AnswerGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AnswerGroup entity.
 */
@Repository
public interface AnswerGroupRepository extends JpaRepository<AnswerGroup, Long> {

}
