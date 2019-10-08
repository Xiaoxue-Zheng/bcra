package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.QuestionSection;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the QuestionSection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionSectionRepository extends JpaRepository<QuestionSection, Long> {

}
