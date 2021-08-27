package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.domain.StudyId;
import uk.ac.herc.bcra.domain.Participant;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StudyId entity.
 */
@Repository
public interface StudyIdRepository extends JpaRepository<StudyId, Long> {
    public Optional<StudyId> findOneByCode(String code);
    public Optional<StudyId> findOneByParticipant(Participant participant);

    Optional<StudyId> findByRiskAssessmentResponse(AnswerResponse answerResponse);
}
