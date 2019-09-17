package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.NumberCheckboxAnswer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NumberCheckboxAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NumberCheckboxAnswerRepository extends JpaRepository<NumberCheckboxAnswer, Long>, JpaSpecificationExecutor<NumberCheckboxAnswer> {

}
