package uk.ac.herc.bcra.repository;

import uk.ac.herc.bcra.domain.QuestionGroupQuestion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the QuestionGroupQuestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionGroupQuestionRepository extends JpaRepository<QuestionGroupQuestion, Long>, JpaSpecificationExecutor<QuestionGroupQuestion> {

}
