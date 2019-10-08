package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.AnswerResponse;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AnswerResponse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnswerResponseRepository extends JpaRepository<AnswerResponse, Long> {

}
