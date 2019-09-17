package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.RepeatAnswer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RepeatAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RepeatAnswerRepository extends JpaRepository<RepeatAnswer, Long>, JpaSpecificationExecutor<RepeatAnswer> {

}
