package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.QuestionnaireQuestionGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the QuestionnaireQuestionGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionnaireQuestionGroupRepository extends JpaRepository<QuestionnaireQuestionGroup, Long>, JpaSpecificationExecutor<QuestionnaireQuestionGroup> {

}
