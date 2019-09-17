package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.RadioAnswer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RadioAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RadioAnswerRepository extends JpaRepository<RadioAnswer, Long>, JpaSpecificationExecutor<RadioAnswer> {

}
