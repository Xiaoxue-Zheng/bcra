package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.RepeatQuestion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RepeatQuestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RepeatQuestionRepository extends JpaRepository<RepeatQuestion, Long>, JpaSpecificationExecutor<RepeatQuestion> {

}
