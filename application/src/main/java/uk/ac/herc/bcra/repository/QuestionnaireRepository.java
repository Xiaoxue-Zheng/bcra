package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.Questionnaire;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;

import java.util.Optional;

/**
 * Spring Data  repository for the Questionnaire entity.
 */
@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {
    @Query(value = "SELECT * FROM Questionnaire WHERE type = ?1 ORDER BY version DESC LIMIT 1", nativeQuery = true)
    Questionnaire getLatestQuestionnaire(String questionnaireTypeString);

    Optional<Questionnaire> findFirstByType(QuestionnaireType type);
}
