package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.CheckboxQuestion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CheckboxQuestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckboxQuestionRepository extends JpaRepository<CheckboxQuestion, Long>, JpaSpecificationExecutor<CheckboxQuestion> {

}
