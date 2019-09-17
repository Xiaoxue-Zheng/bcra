package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.NumberCheckboxQuestion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NumberCheckboxQuestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NumberCheckboxQuestionRepository extends JpaRepository<NumberCheckboxQuestion, Long>, JpaSpecificationExecutor<NumberCheckboxQuestion> {

}
