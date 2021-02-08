package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.Procedure;
import uk.ac.herc.bcra.domain.AnswerResponse;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Procedure entity.
 */
@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, Long> {
	Optional<Procedure> findByRiskAssessmentResponse(AnswerResponse riskAssessmentResponse);
}
