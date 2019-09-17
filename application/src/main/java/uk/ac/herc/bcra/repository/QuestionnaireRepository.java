package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.Questionnaire;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Questionnaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long>, JpaSpecificationExecutor<Questionnaire> {

}
