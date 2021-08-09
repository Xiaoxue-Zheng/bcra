package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.CanRiskReport;
import uk.ac.herc.bcra.domain.StudyId;
import uk.ac.herc.bcra.domain.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CanRiskReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CanRiskReportRepository extends JpaRepository<CanRiskReport, Long> {
    public List<CanRiskReport> findByUploadedBy(User uploadedByUser);
    public Optional<CanRiskReport> findOneByAssociatedStudyId(StudyId studyId);
}
