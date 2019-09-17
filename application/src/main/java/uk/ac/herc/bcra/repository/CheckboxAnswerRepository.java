package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.CheckboxAnswer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CheckboxAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckboxAnswerRepository extends JpaRepository<CheckboxAnswer, Long>, JpaSpecificationExecutor<CheckboxAnswer> {

}
