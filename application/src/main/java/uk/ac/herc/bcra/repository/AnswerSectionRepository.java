package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.AnswerSection;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AnswerSection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnswerSectionRepository extends JpaRepository<AnswerSection, Long> {

}
