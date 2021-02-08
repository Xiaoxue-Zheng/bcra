package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.domain.enumeration.ResponseState;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

/**
 * Spring Data  repository for the AnswerResponse entity.
 */
@Repository
public interface AnswerResponseRepository extends JpaRepository<AnswerResponse, Long> {
    @Query(value = "SELECT ar FROM AnswerResponse ar LEFT JOIN ar.questionnaire AS q WHERE q.type = :type")
    Optional<AnswerResponse> findOneByQuestionnaireType(@Param("type") QuestionnaireType type);

	public List<AnswerResponse> findByState(ResponseState responseState);
}
