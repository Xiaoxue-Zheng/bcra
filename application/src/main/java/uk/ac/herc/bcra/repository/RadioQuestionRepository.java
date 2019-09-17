package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.RadioQuestion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RadioQuestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RadioQuestionRepository extends JpaRepository<RadioQuestion, Long>, JpaSpecificationExecutor<RadioQuestion> {

}
